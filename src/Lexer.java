import Tokens.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Lexer {
    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    private static TokenScanner tokenScanner;
    private Lexer() {}
    
    private static TokenScanner getTokenScanner(PushBackLineNumberReader code){
        if (tokenScanner == null) tokenScanner = new TokenScanner(code);
        return tokenScanner;
        
    }


    public static void main(String[] args) {
        System.out.println("Please enter your code below Enter '~' to end input:");
        PushBackLineNumberReader codeInput = new PushBackLineNumberReader(new BufferedReader(new InputStreamReader(System.in)));
        TokenScanner tokenscanner = Lexer.getTokenScanner(codeInput);
        try
        {
            System.out.println(tokenscanner.getAllTokens());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
