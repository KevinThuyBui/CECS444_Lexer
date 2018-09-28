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
 * @author Kevin Bui Kevinthuybui@gmail.com
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */

public class PushBackLineNumberReader extends LineNumberReader
{
    /**
     * This is the buffer to hold unread data.
     */
    private int[] buffer;
    
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
        buffer = new int[position];
    }
    
    /**
     *  This method reads the next character using the underlying stream and
     *  adds it to the internal buffer.
     * @return The next character in the stream.
     */
    public char peek() throws EndOfFileException {
        int nextChar = ' ';
        try
        {
            nextChar = this.readCharAsInt();
            if (nextChar == -1) {
                throw new EndOfFileException();
            }
            unread(nextChar);
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return (char)nextChar;
    }

    /**
     * Casts the next character from <code>readChar()</code> to a char instead of an int
     * @return next character
     * @throws IOException Unhandled exception from <code>readChar()</code>
     */
    public char readChar() throws IOException
    {
        return (char)readCharAsInt();
    }

    /**
     * Checks if the buffer contains any characters before using underlying stream to read.
     * If the position is less than the length of buffer, <code>buffer[position]</code> is
     * returned and position is incremented.
     * Otherwise, the underlying stream's read method is called.
     * @return Next character as an int in buffer or stream.
     * @throws IOException Unhandled exception from <code>super.read()</code>
     */
    private int readCharAsInt() throws IOException
    {
        if (position < buffer.length){
            return buffer[position++];
        }
        return super.read();
    }
    
    /**
     * Adds the characters in given String to buffer in reverse order and updates
     * position.
     * @param pushBackInput The String to be unread.
     */
    public void unread(String pushBackInput){
        //TODO Length checking for buffer
        int length = pushBackInput.length();
        int[] charactersAsInts = toIntArray(pushBackInput);
        System.arraycopy(charactersAsInts, 0, buffer, position - length, length);
        position = position - length;
    }

    /**
     * Convert a string to an array of ints
     * @param input The string
     * @return The input as an array of ints
     */
    private int[] toIntArray(String input) {
        char[] character = input.toCharArray();
        int [] charactersAsInts = new int[character.length];

        int i = 0;
        for (char c : character) {
            charactersAsInts[i++] = c;
        }
        return charactersAsInts;
    }

    /**
     * Decrements position then adds the given character to buffer.
     * @param character The character to be unread.
     */
    private void unread(int character){
        buffer[--position] = character;
    }
    
    /**
     * Default line number starts at 0. It is changed to 1 here.
     * @return the line number of the input stream
     */
    @Override
    public int getLineNumber() {
        return super.getLineNumber() + 1;
    }
}