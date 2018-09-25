import Tokens.Token;
import java.util.ArrayList;


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
