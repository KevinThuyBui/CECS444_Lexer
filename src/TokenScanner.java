import Tokens.Token;

import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.HashSet;

public class TokenScanner
{
    private static final char NEWLINE = '\n';
    private PushbackInputStream code;
    private State currentState = State.START;
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    private int lineNumber;
    HashMap<String,State> keywords;


    private void createKeywordMap() {
        keywords = new HashMap<>();

        keywords.put("prog",State.KPROG);
        keywords.put("main",State.KMAIN);
        keywords.put("fcn",State.KFCN);
    }
    
    public TokenScanner()
    {
        code = new PushbackInputStream(System.in);
        lineNumber = 1;
        createKeywordMap();
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
            if (currentState == State.COMMENT) {
                advance();
            } else {
                nextChar = advance();
                if (transitionMap.containsKey(new CurrentSituation(currentState, nextChar))) {
                    currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
                    if (currentState == State.COMMENT) {
                        newTokenValueBuilder = new StringBuilder();
                    }else {
                        newTokenValueBuilder.append(nextChar);
                    }
                } else {
                    throw new InvalidTokenException("Transition not found");
                }
            }
        }

        return newTokenValueBuilder.toString();
//        if (currentState.isAccepting()){
//            tokenArrayList.add(tokenFactory.createToken(currentState, 1, newTokenValueBuilder.toString()));
//        }
//        System.out.println(tokenArrayList);
    }

    public Token getNextToken(){
        String tokenValue = "";
        Token token;
        try {
            tokenValue = scan();
            if (currentState == State.ID && keywords.containsKey(tokenValue)) {
                currentState = keywords.get(tokenValue);
            }
            token = TokenFactory.createToken(currentState, lineNumber, tokenValue);
        }
        catch (InvalidTokenException e) {
            token = TokenFactory.createToken(State.ERROR,lineNumber,tokenValue);
        }
        currentState = State.START;

        if (peek() == NEWLINE) {
            advance();
            lineNumber++;
        }

        return token;
    }
}
