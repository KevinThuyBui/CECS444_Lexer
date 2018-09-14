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
        }
        return null;
    }
}
