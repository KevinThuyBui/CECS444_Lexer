public class Token {
    private String codeString;
    private int id;
    private int lineNumber;

    Token(int id, int lineNumber, String codeString) {
        this.id = id;
        this.codeString = codeString;
        this.lineNumber = lineNumber;
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getLineNumber()
    {
        return lineNumber;
    }
    
    public String getCodeString()
    {
    
        return codeString;
    }
    
    @Override
    public String toString()
    {
        return "(Tok: " + id + " line= " + lineNumber + " str= \"" + codeString + "\")";
    }
}
