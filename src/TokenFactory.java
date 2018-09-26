import Tokens.*;

public class TokenFactory
{
    public static Token createToken(State state, int lineNumber, String tokenValue)
    {
        switch (state){
            case INT:
                return new IntToken(3, lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(4, lineNumber, tokenValue, Float.parseFloat(tokenValue));
            case KPROG:
                return new Token(10,lineNumber,tokenValue);
            case ID:
                return new Token(2,lineNumber,tokenValue);
            case COMMENT:
                return null;
            default:
                return new Token(99,lineNumber,tokenValue);
        }
    }
}
