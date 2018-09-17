import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class LexerLineNumberReader extends LineNumberReader
{
    public LexerLineNumberReader(Reader in)
    {
        super(in);
    }
    
    public char peek(){
        char nextChar = ' ';
        try
        {
            this.mark(1);
            nextChar = (char) this.read();
            this.reset();
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return nextChar;
    }
}
