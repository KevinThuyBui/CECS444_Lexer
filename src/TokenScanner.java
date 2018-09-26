import Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  This class handles the parsing of code input and the creation of the token array.
 *
 *  @author Stefan Brand
 *  @author Kevin Bui Kevinthuybui@gmail.com
 */

public class TokenScanner {
    /**
     * The input stream for the input code.
     */
    private PushBackLineNumberReader code;
    
    /**
     * The <code>State</code> which is used to create the correct token. By default it should be at
     * <code>State.START</code>.
     */
    private State currentState = State.START;
    
    /**
     * The <code>HashMap</code> that contains the transition table for the states.
     */
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    
    /**
     * The default constructor. Requires a <code>PushBackLineNumberReader</code>
     * @param code The input stream that contains the code to be parsed.
     */
    public TokenScanner(PushBackLineNumberReader code) {
        this.code = code;
    }
    
    /**
     * Consumes one character from the input stream to be parsed.
     * @return The character to be parsed.
     */
    private char advance() {
        char character = ' ';
        try {
            character =  code.readChar();
        }
        catch (Exception e) {
            //TODO Return EOF or something like it
        }
        
        return character;
    }
    
    /**
     * Calls <code>parseNextToken()</code> to get a token then checks if characters need to be unread by comparing
     * the value of the lastValidToken with the 
     * @return The parsed Token
     * @throws IOException Passes the exception from <code>code.peek()</code>
     */
    
    private Token getNextToken() throws IOException {
        StringBuilder newTokenValueBuilder = new StringBuilder();   // This will hold the value of the new token
        Token lastValidToken = parseNextToken(newTokenValueBuilder);
        
        if (lastValidToken.getId() == 1){
            currentState = State.START;
            return lastValidToken;
        }
    
        /**
         * Checks if the last valid token parsed consists of the entirety of the StringBuilder in order to check if
         * the
         */
        if (lastValidToken.getCodeString().length() == newTokenValueBuilder.length()) {
            currentState = State.START;
            return lastValidToken;
        } else {
            newTokenValueBuilder.delete(0, lastValidToken.getCodeString().length());
            code.unread(newTokenValueBuilder.toString());
    
            currentState = State.START;
            return lastValidToken;
    
        }
    }
    
    private Token parseNextToken(StringBuilder newTokenValueBuilder) throws IOException {
        char nextChar = code.peek();
        Token lastValidToken = null;
        
        while(nextChar != '\n' && nextChar != ' '){ // Continue while the next character is not whitespace
            nextChar = advance();
            /**
             * The token is built a character at a time by passing the <code>CurrentSituation</code> object
             * to the transitionMap. This serves as the key for the <code>HashMap</code> which returns the next
             * state or null if invalid next state.
             */
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
        
        
            if (currentState == null){
                break;    // Ends parsing if invalid state entered
            } else if (currentState == State.COMMENT) {
                lastValidToken = handleComment();
                break;
            } else if (currentState == State.STRING) {
                lastValidToken =  handleString();
                break;
            }
        
            if (currentState.isAccepting()) {
            
                lastValidToken = TokenFactory.createToken(currentState, code.getLineNumber(),
                        newTokenValueBuilder.toString());
            }
        
            nextChar = code.peek();
        }
        
        return lastValidToken;
    }
    
    private Token handleString() throws IOException {
        currentState = State.START;
        String stringTokenValue = processString();
        return TokenFactory.createToken(State.STRING, code.getLineNumber(), stringTokenValue);
    }
    
    private Token handleComment() throws IOException {
        while (code.peek() != '\n') {
            code.readChar();
        }
        
        return TokenFactory.createToken(State.COMMENT, 0, " ");
    }
    
    public ArrayList<Token> getAllTokens() throws IOException {
        clearWhitespace();
        ArrayList<Token> allTokens = new ArrayList<>();
        while (code.peek() != '~') {
            Token nextToken = getNextToken();
            if (nextToken.getId() != 1) allTokens.add(nextToken);  // Discards comment tokens
            clearWhitespace();
        }
        code.close();
        allTokens.add(new Token(State.EOF.getStateID(), code.getLineNumber(),""));
        return allTokens;
    }
    
    private void clearWhitespace() throws IOException {
        while (code.peek() == ' ' ||  code.peek() == '\n' )
            advance();
    }
    
    private String processString() throws IOException {
        StringBuilder newString = new StringBuilder();
        while (code.peek() != '"'){
            
            newString.append(code.readChar());
            
        }
        code.readChar();
        return newString.toString();
        
    }
}
