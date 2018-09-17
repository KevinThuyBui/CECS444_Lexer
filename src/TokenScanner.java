import Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TokenScanner
{
    private PushBackLineNumberReader code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    
    public TokenScanner(PushBackLineNumberReader code) {
        this.code = code;
    }
    
    private char advance() {
        char character = ' ';
        try {
            character = (char)  code.read();
        }
        catch (Exception e) {
            //TODO Return EOF or something like it
        }
        
        return character;
    }
    
    private Token getNextToken(){
        //TODO Refactor this monster and add comment handling
        char nextChar = code.peek();
        StringBuilder newTokenValueBuilder = new StringBuilder();
        Token lastValidToken = null;
        while(nextChar != '\n' && code.peek() != ' '){
            
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
    
            if (currentState == null) break;
            
            if (currentState.isAccepting()) {
    
                lastValidToken = TokenFactory.createToken(currentState, code.getLineNumber(),
                        newTokenValueBuilder.toString());
            }
            nextChar = code.peek();
        }
        if (lastValidToken.getCodeString().length() == newTokenValueBuilder.length()) {
            currentState = State.START;
            return lastValidToken;
        }
        else {
            if (lastValidToken != null)
            {
                newTokenValueBuilder.delete(0, lastValidToken.getCodeString().length());
                code.unread(newTokenValueBuilder.toString());
                
            }
            currentState = State.START;
            return lastValidToken;
    
        }
        
        
        
    }
    
    public ArrayList<Token> getAllTokens() throws IOException
    {
        clearWhitespace();
        ArrayList<Token> allTokens = new ArrayList<>();
        while (code.peek() != '~') {
            allTokens.add(getNextToken());
    
            clearWhitespace();
        }
        code.close();
        allTokens.add(new Token(0, code.getLineNumber(),""));
        return allTokens;
    }
    
    private void clearWhitespace() throws IOException
    {
        while (code.peek() == ' ' ||  code.peek() == '\n' )
            advance();
    }
}
