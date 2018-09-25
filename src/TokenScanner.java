import Tokens.Token;

import java.io.PushbackInputStream;
import java.util.HashMap;

public class TokenScanner
{
    private static final char NEWLINE = '\n';
    private PushbackInputStream code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    private int lineNumber;
    
    public TokenScanner()
    {
        code = new PushbackInputStream(System.in);
        lineNumber = 0;
    }
    
    private char advance() {
        char character = ' ';
        try {
            character = (char)  code.read();
        }
        catch (Exception e) {
            return (char) -1;
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
    
    private String scan() throws InvalidTokenException {
        char nextChar;
        StringBuilder newTokenValueBuilder = new StringBuilder();
        while( peek() != NEWLINE && peek() != ' ') {
            nextChar = advance();
            if (transitionMap.containsKey(new CurrentSituation(currentState, nextChar))) {
                currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
                newTokenValueBuilder.append(nextChar);
            } else {
                throw new InvalidTokenException("Transition not found");
            }
        }

        if (peek() == NEWLINE) {
            advance();
            lineNumber++;
        }

        return newTokenValueBuilder.toString();
//        if (currentState.isAccepting()){
//            tokenArrayList.add(tokenFactory.createToken(currentState, 1, newTokenValueBuilder.toString()));
//        }
//        System.out.println(tokenArrayList);
    }
    
    public Token getNextToken(){
        String tokenValue = "";
        try {
            tokenValue = scan();
        }
        catch (InvalidTokenException e) {
            return TokenFactory.createToken(State.ERROR,lineNumber,tokenValue);
        }
        return TokenFactory.createToken(currentState, lineNumber, tokenValue);
    }
}
