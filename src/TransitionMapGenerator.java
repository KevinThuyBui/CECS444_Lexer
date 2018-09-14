import java.util.HashMap;

public class TransitionMapGenerator
{
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};
    
    private static HashMap<CurrentSituation, State> transitionMap = new HashMap<>();
    private TransitionMapGenerator(){
    }
    
    private static void addIntFloatTransitions(){
        transitionMap.put(new CurrentSituation(State.START,'+'),State.PLUS);
        transitionMap.put(new CurrentSituation(State.START, '-'),State.MINUS);
        fillMap(State.START,State.INT,DIGITS);
        fillMap(State.PLUS,State.INT,DIGITS);
        fillMap(State.MINUS,State.INT,DIGITS);
        fillMap(State.INT,State.INT,DIGITS);
        transitionMap.put(new CurrentSituation(State.INT,'.'),State.MAYBEFLOAT);
        fillMap(State.MAYBEFLOAT,State.FLOAT,DIGITS);
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
    }
    
    private static void fillMap(State oldState, State newState, char[] chars) {
        for (char c : chars) {
            transitionMap.put(new CurrentSituation(oldState, c), newState);
        }
    }
}
