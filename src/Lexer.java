import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Lexer {
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    private State currentState = State.START;
    private PushbackInputStream code;
    private ArrayList<Token> tokenArrayList = new ArrayList<>();

    private Lexer() {
    }


    public static void main(String[] args) {
        new Lexer().scan();
    }

    private void readCode() {
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

    private void scan() {
        readCode();
        char nextChar;
        StringBuilder newTokenValueBuilder = new StringBuilder();
        TokenFactory tokenFactory = new TokenFactory();
        while( peek() != '\n' && peek() != ' ') {
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
            newTokenValueBuilder.append(nextChar);
        }
        if (currentState.isAccepting()){
            tokenArrayList.add(tokenFactory.createToken(currentState, 1, newTokenValueBuilder.toString()));
        }
        System.out.println(tokenArrayList);
    }


}
