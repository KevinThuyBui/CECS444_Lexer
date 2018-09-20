import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * This subclass of <code>LineNumberReader</code> provides the ability to
 * unread data from a stream. It maintains an internal buffer of previously
 * read data to supply to next read operation.
 *
 * The default buffer is 20 characters long. This should suffice for our Lexer.
 *
 * @author Kevin Bui
 */

public class PushBackLineNumberReader extends LineNumberReader
{
    /**
     * This is the buffer to hold unread data.
     */
    private char[] buffer;
    
    /**
     * This is the position in the buffer where the next character is to be read.
     * The characters are stored in reverse order starting at the end of buffer to
     * position. If position is zero, the buffer is full.
     */
    private int position;
    
    /**
     * This is a constructor that sets the buffer size to 20.
     * @param in The stream to be wrapped.
     */
    public PushBackLineNumberReader(Reader in)
    {
        super(in);
    
        position = 20;
        buffer = new char[position];
    }
    
    /**
     *  This method reads the next character using the underlying stream and
     *  adds it to the internal buffer.
     * @return The next character in the stream.
     */
    
    public char peek(){
        char nextChar = ' ';
        try
        {
            nextChar = this.readChar();
            unread(nextChar);
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return nextChar;
    }
    
    /**
     * Checks if the buffer contains any characters before using underlying stream to read.
     * If the position is less than the length of buffer, <code>buffer[position]</code> is
     * returned and position is incremented.
     * Otherwise, the underlying stream's read method is called.
     * @return Next character in buffer or stream.
     * @throws IOException
     */
    
    public char readChar() throws IOException
    {
        if (position < buffer.length){
            return buffer[position++];
        }
        return (char) super.read();
    }
    
    /**
     * Adds the characters in given String to buffer in reverse order and updates
     * position.
     * @param pushBackInput The String to be unread.
     */
    
    public void unread(String pushBackInput){
        //TODO Length checking for buffer
        int length = pushBackInput.length();
        System.arraycopy(pushBackInput.toCharArray(), 0, buffer, position - length, length);
        position = position - length;
    }
    
    /**
     * Adds the given character to buffer and updates position.
     * @param character The character to be unread.
     */
    public void unread(char character){
        buffer[--position] = character;
    }
}
