public class IntToken extends Token {
    private int value;

    public IntToken(int id, int lineNumber, String name, int value) {
        super(id, lineNumber, name);
        this.value = value;
    }
    @Override
    public String toString()
    {
        return "(Tok: " + getId() + " line= " + getLineNumber() + " str= \"" + getCodeString() + "\"" +
                " int= " + value +")";
    }
}
