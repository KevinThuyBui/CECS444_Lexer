import java.io.PushbackInputStream;
import java.util.HashMap;


public class Lexer {
    private static HashMap<CurrentSituation,State> transitionMap = TransitionMapGenerator.getTransitionMap();
    private State currentState = State.START;
    private PushbackInputStream code;

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
            character = (char) code.read();
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
        while((nextChar = peek()) != '$') {
            nextChar = advance();
            currentState = transitionMap.get(new CurrentSituation(currentState, nextChar));
        }
    }


}
