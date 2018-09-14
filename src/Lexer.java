import Tokens.Token;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static jdk.nashorn.internal.objects.Global.print;


public class Lexer {
    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    private static TokenScanner tokenScanner;
    private Lexer() {
    }
    
    private static TokenScanner getTokenScanner(){
        if (tokenScanner == null) tokenScanner = new TokenScanner();
        return tokenScanner;
        
    }


    public static void main(String[] args) {
        TokenScanner tokenscanner = Lexer.getTokenScanner();
        System.out.println(tokenscanner.getNextToken());
    }
}
