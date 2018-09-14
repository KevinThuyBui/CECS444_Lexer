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
        return "(Tok: " + getId() + " line= " + getLineNumber() + " str= \"" + getCodeString() + "\"" +
                " float= " + value +")";
    }
}
