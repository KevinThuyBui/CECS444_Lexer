import Tokens.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  This class represents a scanner/lexer for the fictional A4 language.
 *  It runs on the command line and either accepts your A4 code manually (terminated by a '~') or from a piped file
 *
 *  @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 *  @author Kevin Bui <Kevinthuybui@gmail.com>
 *  @author Gabe Flores <rgabeflores@gmail.com>
 */

public class Lexer {
    private static TokenScanner tokenScanner;
    private Lexer() {}

    /**
     * Creates a new TokenScanner if none is present
     * @param code The code to be scanned
     * @return The newly created TokenScanner object
     */
    private static TokenScanner getTokenScanner(PushBackLineNumberReader code){
        if (tokenScanner == null) tokenScanner = new TokenScanner(code);
        return tokenScanner;
        
    }

    /**
     * Main method for the lexer.
     * Reads the code from stdin and prints all the tokens to stdout
     * @param args all arguments are ignored
     */
    public static void main(String[] args) {
        System.out.println("\nEither pipe your code to this program or enter it manually below, terminated by '~':\n");
        PushBackLineNumberReader codeInput = new PushBackLineNumberReader(new BufferedReader(new InputStreamReader(System.in)));
        TokenScanner tokenscanner = Lexer.getTokenScanner(codeInput);
        try
        {
            for (Token t : tokenscanner.getAllTokens())
                if (t.getId() == 99)
                    System.out.println(String.format("Invalid Input Error: Malformed Token \"%s\" on line %d.",
                            t.getCodeString(), t.getLineNumber()));
                else
                    System.out.println(t);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}