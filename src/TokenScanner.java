import Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TokenScanner {
    private PushBackLineNumberReader code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    
    public TokenScanner(PushBackLineNumberReader code) {
        this.code = code;
    }
    
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
    
    private Token getNextToken() throws IOException {
        //TODO Refactor this monster
        char nextChar = code.peek();
        StringBuilder newTokenValueBuilder = new StringBuilder();
        Token lastValidToken = null;
        
        while(nextChar != '\n' && nextChar != ' '){
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
    
    
            if (currentState == null) break;
    
    
            if (currentState == State.COMMENT) {
                return handleComment();
            }
        
        
            if (currentState == State.STRING) {
                return handleString();
            }
            
            if (currentState.isAccepting()) {
    
                lastValidToken = TokenFactory.createToken(currentState, code.getLineNumber(),
                        newTokenValueBuilder.toString());
            }
            
            nextChar = code.peek();
        }
        
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
    
    private Token handleString() throws IOException {
        currentState = State.START;
        String stringTokenValue = processString();
        return TokenFactory.createToken(State.STRING, code.getLineNumber(), stringTokenValue);
    }
    
    private Token handleComment() throws IOException {
        while (code.peek() != '\n') {
            code.readChar();
        }
        
        currentState = State.START;
        clearWhitespace();
        
        return getNextToken();
    }
    
    public ArrayList<Token> getAllTokens() throws IOException {
        clearWhitespace();
        ArrayList<Token> allTokens = new ArrayList<>();
        while (code.peek() != '~') {
            allTokens.add(getNextToken());
    
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
