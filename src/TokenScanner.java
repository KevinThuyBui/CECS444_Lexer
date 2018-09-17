import Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TokenScanner
{
    private LexerLineNumberReader code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    
    public TokenScanner(LexerLineNumberReader code) {
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
    
    private String scan() {
        char nextChar;
        StringBuilder newTokenValueBuilder = new StringBuilder();
        while( code.peek() != '\n' && code.peek() != ' ') {
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
        }
        
        return newTokenValueBuilder.toString();

    }
    
    private Token getNextToken(){
        String tokenValue = scan();
        //TODO Check accepting state and handle reverting to previous accepting state.
        Token newToken = TokenFactory.createToken(currentState, code.getLineNumber(), tokenValue);
        currentState = State.START;
        return newToken;
    }
    
    public ArrayList<Token> getAllTokens() throws IOException
    {
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
