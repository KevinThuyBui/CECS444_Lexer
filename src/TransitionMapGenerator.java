import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a transition map used by the Finite State Machine
 */

public class TransitionMapGenerator
{
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};
    private static HashMap<Transition, State> transitionMap = new HashMap<>();

    /**
     * Should never be called, hides the public default constructor
     */
    private TransitionMapGenerator(){}

    /**
     * Creates (if necessary) and returns the transition map
     * @return the transition map
     */
    public static Map<Transition, State> getTransitionMap()
    {
        if (transitionMap.isEmpty()){
            generateTransitionMap();
        }
        return transitionMap;
    }

    /**
     * Fills the map with all transitions
     */
    private static void generateTransitionMap()
    {
        addIntFloatTransitions();
        addPairedDelimiters();
        addOtherPunctuationTokens();
        addCharacters();
        addMulticharacterOperators();
        addStringComment();
    }

    //TODO add the rest of the transition states

    /**
     * Adds all transitions necessary for numbers (int and float)
     */
    private static void addIntFloatTransitions(){
        transitionMap.put(new Transition(State.START,'+'),State.PLUS);
        transitionMap.put(new Transition(State.START, '-'),State.MINUS);
        fillMap(State.START,State.INT,DIGITS);
        fillMap(State.PLUS,State.INT,DIGITS);
        fillMap(State.MINUS,State.INT,DIGITS);
        fillMap(State.INT,State.INT,DIGITS);
        transitionMap.put(new Transition(State.INT,'.'),State.MAYBEFLOAT);
        fillMap(State.MAYBEFLOAT,State.FLOAT,DIGITS);
        fillMap(State.FLOAT, State.FLOAT, DIGITS);
    }

    /**
     * Adds all transitions necessary for paired delimiters
     */
    private static void addPairedDelimiters(){
        transitionMap.put(new Transition(State.START, '<'), State.ANGLE1);
        transitionMap.put(new Transition(State.START, '>'), State.ANGLE2);
        transitionMap.put(new Transition(State.START, '{'), State.BRACE1);
        transitionMap.put(new Transition(State.START, '}'), State.BRACE2);
        transitionMap.put(new Transition(State.START, '['), State.BRACKET1);
        transitionMap.put(new Transition(State.START, ']'), State.BRACKET2);
        transitionMap.put(new Transition(State.START, '('), State.PARENS1);
        transitionMap.put(new Transition(State.START, ')'), State.PARENS2);
    }

    /**
     * Adds all transitions necessary for miscellaneous punctuation
     */
    private static void addOtherPunctuationTokens(){
        transitionMap.put(new Transition(State.START, ';'), State.SEMI);
        transitionMap.put(new Transition(State.START, ','), State.COMMA);
        transitionMap.put(new Transition(State.START, '*'), State.ASTER);
        transitionMap.put(new Transition(State.START, '^'), State.CARET);
        transitionMap.put(new Transition(State.START, ':'), State.COLON);
        transitionMap.put(new Transition(State.START, '.'), State.DOT);
        transitionMap.put(new Transition(State.START, '='), State.EQUAL);
        transitionMap.put(new Transition(State.START, '-'), State.MINUS);
        transitionMap.put(new Transition(State.START, '+'), State.PLUS);
        transitionMap.put(new Transition(State.START, '/'), State.SLASH);
    }

    /**
     * Adds all transitions necessary for multicharacter operators
     */
    private static void addMulticharacterOperators(){
        transitionMap.put(new Transition(State.MINUS, '>'), State.OPARROW);
        transitionMap.put(new Transition(State.EQUAL, '='), State.OPEQ);
        transitionMap.put(new Transition(State.START, '!'), State.MAYBEOPNE);
        transitionMap.put(new Transition(State.MAYBEOPNE, '='), State.OPNE);
        transitionMap.put(new Transition(State.ANGLE1, '='), State.OPLE);
        transitionMap.put(new Transition(State.ANGLE2, '='), State.OPGE);
        transitionMap.put(new Transition(State.ANGLE1, '<'), State.OPSHL);
        transitionMap.put(new Transition(State.ANGLE2, '>'), State.OPSHR);
    }

    /**
     * Adds all transitions necessary for identifiers
     */
    private static void addCharacters(){
        fillMapChar(State.START, State.ID);
        transitionMap.put(new Transition(State.START, '_'), State.ID);
        
        fillMapChar(State.ID, State.ID);
        fillMap(State.ID, State.ID, DIGITS);
        transitionMap.put(new Transition(State.ID, '_'), State.ID);
   
    }

    /**
     * Adds all transitions necessary for strings and comments
     */
    private static void addStringComment() {
        transitionMap.put(new Transition(State.START, '"'), State.STRING);
        transitionMap.put(new Transition(State.SLASH, '/'), State.COMMENT);
    }


    private static void fillMap(State oldState, State newState, char[] chars) {
        for (char c : chars) {
            transitionMap.put(new Transition(oldState, c), newState);
        }
    }
    
    private static void fillMapChar(State oldState, State newState){
        //Uppercase ASCII
        for (int i = 65; i < 91; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }

        //Lowercase ASCII
        for (int i = 97; i < 123; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }
    }
    
    private static void fillMapSymbols(State oldState, State newState){
        for (int i =33 ; i < 48; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }
        
        for (int i = 58; i < 65; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }
        
        for (int i = 91; i < 97; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }
        
        for (int i = 123; i < 128; i++){
            transitionMap.put(new Transition(oldState, (char) i), newState);
        }
    }
}
