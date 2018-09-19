import java.util.HashMap;

public class TransitionMapGenerator
{
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};
    
    private static HashMap<CurrentSituation, State> transitionMap = new HashMap<>();
    private TransitionMapGenerator(){}
    
    //TODO add the rest of the transition states
    
    private static void addIntFloatTransitions(){
        transitionMap.put(new CurrentSituation(State.START,'+'),State.PLUS);
        transitionMap.put(new CurrentSituation(State.START, '-'),State.MINUS);
        fillMap(State.START,State.INT,DIGITS);
        fillMap(State.PLUS,State.INT,DIGITS);
        fillMap(State.MINUS,State.INT,DIGITS);
        fillMap(State.INT,State.INT,DIGITS);
        transitionMap.put(new CurrentSituation(State.INT,'.'),State.MAYBEFLOAT);
        fillMap(State.MAYBEFLOAT,State.FLOAT,DIGITS);
        fillMap(State.FLOAT, State.FLOAT, DIGITS);
    }
    
    private static void addPairedDelimiters(){
        transitionMap.put(new CurrentSituation(State.START, '<'), State.ANGLE1);
        transitionMap.put(new CurrentSituation(State.START, '>'), State.ANGLE2);
        transitionMap.put(new CurrentSituation(State.START, '{'), State.BRACE1);
        transitionMap.put(new CurrentSituation(State.START, '}'), State.BRACE2);
        transitionMap.put(new CurrentSituation(State.START, '['), State.BRACKET1);
        transitionMap.put(new CurrentSituation(State.START, ']'), State.BRACKET2);
        transitionMap.put(new CurrentSituation(State.START, '('), State.PARENS1);
        transitionMap.put(new CurrentSituation(State.START, ')'), State.PARENS2);
    }
    
    private static void addOtherPunctuationTokens(){
        transitionMap.put(new CurrentSituation(State.START, ';'), State.SEMI);
        transitionMap.put(new CurrentSituation(State.START, ','), State.COMMA);
        
        
        transitionMap.put(new CurrentSituation(State.START, '*'), State.ASTER);
        transitionMap.put(new CurrentSituation(State.START, '^'), State.CARET);
        transitionMap.put(new CurrentSituation(State.START, ':'), State.COLON);
        transitionMap.put(new CurrentSituation(State.START, '.'), State.DOT);
        transitionMap.put(new CurrentSituation(State.START, '='), State.EQUAL);
        transitionMap.put(new CurrentSituation(State.START, '-'), State.MINUS);
        transitionMap.put(new CurrentSituation(State.START, '+'), State.PLUS);
        transitionMap.put(new CurrentSituation(State.START, '/'), State.SLASH);
    }
    
    private static void addCharacters(){
        fillMapChar(State.START, State.ID);
        transitionMap.put(new CurrentSituation(State.START, '_'), State.ID);
        
        fillMapChar(State.ID, State.ID);
        fillMap(State.ID, State.ID, DIGITS);
        transitionMap.put(new CurrentSituation(State.ID, '_'), State.ID);
    }
    
    public static HashMap<CurrentSituation, State> getTransitionMap()
    {
        if (transitionMap.isEmpty()){
            generateTransitionMap();
        }
        return transitionMap;
    }
    
    private static void generateTransitionMap()
    {
        addIntFloatTransitions();
        addPairedDelimiters();
        addOtherPunctuationTokens();
        addCharacters();
        
        transitionMap.put(new CurrentSituation(State.START, '"'), State.STRING);
        transitionMap.put(new CurrentSituation(State.SLASH, '/'), State.COMMENT);
    }
    
    private static void fillMap(State oldState, State newState, char[] chars) {
        for (char c : chars) {
            transitionMap.put(new CurrentSituation(oldState, c), newState);
        }
    }
    
    private static void fillMapChar(State oldState, State newState){
        for (int i = 65; i < 91; i++){
            transitionMap.put(new CurrentSituation(oldState, (char) i), newState);
        }
        
        for (int i = 97; i < 123; i++){
            transitionMap.put(new CurrentSituation(oldState, (char) i), newState);
        }
    }
}
