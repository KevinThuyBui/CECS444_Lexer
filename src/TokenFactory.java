public class TokenFactory
{
    private Token token;
    
    public Token createToken(State state, int lineNumber, String tokenValue)
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
