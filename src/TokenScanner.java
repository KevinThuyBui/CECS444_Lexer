import Tokens.Token;

import java.io.PushbackInputStream;
import java.util.HashMap;

public class TokenScanner
{
    private PushbackInputStream code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    
    public TokenScanner()
    {
        code = new PushbackInputStream(System.in);
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
    
    private char peek() {
        char character = advance();
        try {
            code.unread(character);
        }
        catch (Exception e) {
            //TODO handle exception
        }
        return character;
    }
    
    private String scan() {
        char nextChar;
        StringBuilder newTokenValueBuilder = new StringBuilder();
        while( peek() != '\n' && peek() != ' ') {
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
        }
        
        return newTokenValueBuilder.toString();
//        if (currentState.isAccepting()){
//            tokenArrayList.add(tokenFactory.createToken(currentState, 1, newTokenValueBuilder.toString()));
//        }
//        System.out.println(tokenArrayList);
    }
    
    public Token getNextToken(){
        String tokenValue = scan();
        return TokenFactory.createToken(currentState, 1, tokenValue);
    }
}
