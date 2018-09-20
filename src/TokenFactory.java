import Tokens.FloatToken;
import Tokens.IntToken;
import Tokens.Token;

/**
 * This class handles the creation of Tokens.
 */
public class TokenFactory
{
    /**
     *
     * @param state
     * @param lineNumber
     * @param tokenValue
     * @return
     */
    public static Token createToken(State state, int lineNumber, String tokenValue)
    {
        switch (state){
            case INT:
                return new IntToken(state.getStateID(), lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(state.getStateID(), lineNumber, tokenValue, Float.parseFloat(tokenValue));
    
            case ID:
                //TODO Add function to check/create tokens for keywords
                
            case DOT:
            case MINUS:
            case ASTER:
            case CARET:
            case COLON:
            case PLUS:
            case SLASH:
            case EQUAL:
            case ANGLE1:
            case ANGLE2:
            case BRACE1:
            case BRACE2:
            case PARENS1:
            case PARENS2:
            case BRACKET1:
            case BRACKET2:
            case COMMA:
            case SEMI:
            case STRING:
                return new Token(state.getStateID(), lineNumber, tokenValue);
            default:
                return new Token(State.ERROR.getStateID(), lineNumber, tokenValue);
        }
    }
}

