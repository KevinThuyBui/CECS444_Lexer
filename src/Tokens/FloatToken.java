package Tokens;

public class FloatToken extends Token {
    private double value;

    public FloatToken(int id, int lineNumber, String name, double value) {
        super(id,lineNumber, name);
        this.value = value;
    }
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" float= %.2f)", getId(),
                getLineNumber(), getCodeString(), value);
    }
}
