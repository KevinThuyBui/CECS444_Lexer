import Tokens.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Lexer {
    private static TokenScanner tokenScanner;
    private Lexer() {}
    
    private static TokenScanner getTokenScanner(PushBackLineNumberReader code){
        if (tokenScanner == null) tokenScanner = new TokenScanner(code);
        return tokenScanner;
        
    }
    
    
    public static void main(String[] args) {
        System.out.println("\nEither pipe your code to this program or enter it manually below, terminated by '~':\n");
        PushBackLineNumberReader codeInput = new PushBackLineNumberReader(new BufferedReader(new InputStreamReader(System.in)));
        TokenScanner tokenscanner = Lexer.getTokenScanner(codeInput);
        try
        {
            for (Token t : tokenscanner.getAllTokens())
                System.out.println(t);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}