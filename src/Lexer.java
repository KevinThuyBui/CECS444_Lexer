import java.io.PushbackInputStream;
import java.util.HashMap;


public class Lexer {
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};

    private HashMap<CurrentSituation,State> transitionMap = new HashMap<>();
    private State currentState = State.START;
    private PushbackInputStream code;

    private Lexer() {
        createFsm();
    }

    private void createFsm() {
        transitionMap.put(new CurrentSituation(State.START,'+'),State.PLUS);
        transitionMap.put(new CurrentSituation(State.START, '-'),State.MINUS);
        fillMap(State.START,State.INT,DIGITS);
        fillMap(State.PLUS,State.INT,DIGITS);
        fillMap(State.MINUS,State.INT,DIGITS);
        fillMap(State.INT,State.INT,DIGITS);
        transitionMap.put(new CurrentSituation(State.INT,'.'),State.MAYBEFLOAT);
        fillMap(State.MAYBEFLOAT,State.FLOAT,DIGITS);
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

    private void fillMap(State oldState, State newState, char[] chars) {
        for (char c : chars) {
            transitionMap.put(new CurrentSituation(oldState, c), newState);
        }
    }
}
