package Tokens;

public class IntToken extends Token {
    private int value;

    public IntToken(int id, int lineNumber, String name, int value) {
        super(id, lineNumber, name);
        this.value = value;
    }
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" int= %d)", getId(),
            getLineNumber(), getCodeString(), value);
    }
}
