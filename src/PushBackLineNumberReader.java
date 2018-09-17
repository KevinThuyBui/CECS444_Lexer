import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class PushBackLineNumberReader extends LineNumberReader
{
    private char[] buffer;
    private int position;
    public PushBackLineNumberReader(Reader in)
    {
        super(in);
    
        position = 20;
        buffer = new char[position];
    }
    
    public char peek(){
        char nextChar = ' ';
        try
        {
            nextChar = (char) this.read();
            unread(nextChar);
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return nextChar;
    }
    
    @Override
    public int read() throws IOException
    {
        if (position < buffer.length){
            return buffer[position++];
        }
        return super.read();
    }
    
    public void unread(String pushbackInput){
        int length = pushbackInput.length();
        System.arraycopy(pushbackInput.toCharArray(), 0, buffer, position - length, length);
        position = position - length;
    }
    public void unread(char character){
        buffer[--position] = character;
    }
}
