import java.util.HashMap;
import java.util.HashSet;

public class TransitionMapGenerator
{
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};
    private static final char[] LU = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
            'P','Q','R','S','T','U','V','W','X','Y','Z','_'};

    private static HashMap<CurrentSituation, State> transitionMap = new HashMap<>();
    private TransitionMapGenerator(){}



    private static void addIntFloatTransitions(){
        fillMap(State.START,State.PLUS,'+');
        fillMap(State.START,State.MINUS,'-');
        fillMap(State.START,State.INT,DIGITS);
        fillMap(State.PLUS,State.INT,DIGITS);
        fillMap(State.MINUS,State.INT,DIGITS);
        fillMap(State.INT,State.INT,DIGITS);
        fillMap(State.INT,State.MAYBEFLOAT,'.');
        fillMap(State.MAYBEFLOAT,State.FLOAT,DIGITS);
    }

    private static void addIDTransitions() {
        fillMap(State.START,State.LU,LU);
        fillMap(State.LU,State.ID,LU);
        fillMap(State.LU,State.ID,DIGITS);
        fillMap(State.ID,State.ID,LU);
        fillMap(State.ID,State.ID,DIGITS);
    }

    private static void addDelimiterTransitions() {
        fillMap(State.START,State.COMMA,',');
        fillMap(State.START,State.SEMI,';');
        fillMap(State.START,State.ANGLE1,'<');
        fillMap(State.START,State.ANGLE2,'>');
        fillMap(State.START,State.BRACE1,'{');
        fillMap(State.START,State.BRACE2,'}');
        fillMap(State.START,State.BRACKET1,'[');
        fillMap(State.START,State.BRACKET2,']');
        fillMap(State.START,State.PARENS1,'(');
        fillMap(State.START,State.PARENS2,')');
    }

    private static void addPunctuationTransitions() {
        fillMap(State.START,State.ASTER,'*');
        fillMap(State.START,State.CARET,'^');
        fillMap(State.START,State.COLON,':');
        fillMap(State.START,State.DOT,'.');
        fillMap(State.START,State.EQUAL,'=');
        fillMap(State.START,State.MINUS,'-');
        fillMap(State.START,State.PLUS,'+');
        fillMap(State.START,State.SLASH,'/');
    }

    private static void addMultiCharOperatorTransitions() {
        fillMap(State.MINUS,State.OPARROW,'>');
        fillMap(State.EQUAL,State.OPEQ,'=');
        fillMap(State.START,State.EXCLA,'!');
        fillMap(State.EXCLA,State.OPNE,'=');
        fillMap(State.ANGLE1,State.OPLE,'=');
        fillMap(State.ANGLE2,State.OPGE,'=');
        fillMap(State.ANGLE1,State.OPSHL,'<');
        fillMap(State.ANGLE2,State.OPSHR,'>');
        fillMap(State.SLASH,State.COMMENT,'/');
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
        addIDTransitions();
        addDelimiterTransitions();
        addPunctuationTransitions();
        addMultiCharOperatorTransitions();
    }
    
    private static void fillMap(State oldState, State newState, char[] chars) {
        for (char c : chars) {
            fillMap(oldState,newState,c);
        }
    }

    private static void fillMap(State oldState, State newState, char c) {
        transitionMap.put(new CurrentSituation(oldState, c), newState);
    }
}
