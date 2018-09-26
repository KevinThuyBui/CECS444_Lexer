import Tokens.Token;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  This class handles the parsing of code input and the creation of the token output.
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
    TokenScanner(PushBackLineNumberReader code) {
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
     * Creates a new token using <code>parseNextToken</code> and pushes unused characters back.
     * @return The parsed Token
     * @throws IOException Passes the exception from <code>parseNextToken</code>
     */
    
    private Token getNextToken() throws IOException {
        StringBuilder newTokenValueBuilder = new StringBuilder();
        Token lastValidToken = parseNextToken(newTokenValueBuilder);
        handleUnusedCharacters(newTokenValueBuilder, lastValidToken);
        currentState = State.START;
        return lastValidToken;
    }
    
    /**
     * Checks the length of <code>codeString</code> of the given token against the value of the StringBuilder to
     * determine if too many characters have been read and <code>unread</code> the unneeded characters.
     * @param newTokenValueBuilder The characters that have been read from input stream
     * @param lastValidToken The last token that was in an accepting state
     */
    private void handleUnusedCharacters(StringBuilder newTokenValueBuilder, Token lastValidToken) {
        if (lastValidToken.getCodeString().length() != newTokenValueBuilder.length()) {
            newTokenValueBuilder.delete(0, lastValidToken.getCodeString().length());
            code.unread(newTokenValueBuilder.toString());
        }
    }
    
    /**
     * The token is built a character at a time by passing the <code>CurrentSituation</code> object
     * to the transitionMap. This serves as the key for the <code>HashMap</code> which returns the next
     * state or null if invalid next state.
     */
    private Token parseNextToken(StringBuilder newTokenValueBuilder) throws IOException {
        char nextChar = code.peek();
        Token lastValidToken = null;
        
        while(nextChar != '\n' && nextChar != ' '){ // Continue while the next character is not whitespace
            nextChar = advance();
            
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
        
        
            if (currentState == null){
                break;    // Ends parsing if invalid state entered
            } else if (currentState == State.COMMENT) {
                lastValidToken = handleComment();
                break;
            } else if (currentState == State.STRING) {
                lastValidToken =  createStringToken();
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
    
    /**
     * Creates a String token
     * @return String Token
     * @throws IOException unhandled from processString()
     */
    private Token createStringToken() throws IOException {
        currentState = State.START;
        String stringTokenValue = processString();
        return TokenFactory.createToken(State.STRING, code.getLineNumber(), stringTokenValue);
    }
    
    /**
     * Discards the remaining line from stdin then creates a comment token.
     * @return Token A comment token
     * @throws IOException from code.readChar()
     */
    
    private Token handleComment() throws IOException {
        while (code.peek() != '\n') {
            code.readChar();
        }
        
        return TokenFactory.createToken(State.COMMENT, 0, "//");
    }
    
    /**
     * Creates an <code>ArrayList</code> of <code>Token</code>. Adds an end of file token to the end of array.
     * @return ArrayList of Tokens
     * @throws IOException unhandled exception from getNextToken()
     */
    
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
    
    /**
     * Advances PushBackLineNumberReader to next non-whitespace character.
     */
    
    private void clearWhitespace() {
        while (code.peek() == ' ' ||  code.peek() == '\n' )
            advance();
    }
    
    /**
     * Creates a String from all characters until the next quote.
     * @return the code string of the String Token
     * @throws IOException Unhandled from readChar()
     */
    
    private String processString() throws IOException {
        StringBuilder newString = new StringBuilder();
        while (code.peek() != '"'){
            
            newString.append(code.readChar());
            
        }
        code.readChar(); // Necessary to consume the second quote character.
        return newString.toString();
        
    }
}
