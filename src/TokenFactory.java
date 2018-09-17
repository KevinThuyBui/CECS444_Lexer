import Tokens.FloatToken;
import Tokens.IntToken;
import Tokens.Token;

public class TokenFactory
{
    public static Token createToken(State state, int lineNumber, String tokenValue)
    {
        switch (state){
            case INT:
                return new IntToken(3, lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(4, lineNumber, tokenValue, Float.parseFloat(tokenValue));
                //TODO Add cases for each unique token and a general one for the rest
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
            case ID:
                return new Token(state.ordinal(), lineNumber, tokenValue);
        }
        return null;
    }
}
