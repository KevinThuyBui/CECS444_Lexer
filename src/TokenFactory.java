import Tokens.FloatToken;
import Tokens.IntToken;
import Tokens.Token;

/**
 * This class handles the creation of Tokens.
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Richard Salmeron <richard.salmeron@student.csulb.edu>
 */
public class TokenFactory
{
    /**
     * Creates a token given a state, line number, and the token's value.
     * @param state The state as given by <code>State</code>
     * @param lineNumber The line number of the source code
     * @param tokenValue The String value of the token
     * @return A token as described by the state
     */
    public static Token createToken(State state, int lineNumber, String tokenValue)
    {
        switch (state){
            case INT:
                return new IntToken(state.getStateID(), lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(state.getStateID(), lineNumber, tokenValue, Float.parseFloat(tokenValue));
            case ID:
                return handleID(lineNumber, tokenValue);
            case COMMENT:
                //fallthrough
            case OPARROW:
                //fallthrough
            case OPEQ:
                //fallthrough
            case OPGE:
                //fallthrough
            case OPLE:
                //fallthrough
            case OPNE:
                //fallthrough
            case OPSHL:
                //fallthrough
            case OPSHR:
                //fallthrough
            case DOT:
                //fallthrough
            case MINUS:
                //fallthrough
            case ASTER:
                //fallthrough
            case CARET:
                //fallthrough
            case COLON:
                //fallthrough
            case PLUS:
                //fallthrough
            case SLASH:
                //fallthrough
            case EQUAL:
                //fallthrough
            case ANGLE1:
                //fallthrough
            case ANGLE2:
                //fallthrough
            case BRACE1:
                //fallthrough
            case BRACE2:
                //fallthrough
            case PARENS1:
                //fallthrough
            case PARENS2:
                //fallthrough
            case BRACKET1:
                //fallthrough
            case BRACKET2:
                //fallthrough
            case COMMA:
                //fallthrough
            case SEMI:
                //fallthrough
            case STRING:
                return new Token(state.getStateID(), lineNumber, tokenValue);
            default:
                return new Token(State.ERROR.getStateID(), lineNumber, tokenValue);
        }
    }

    /**
     * Checks a token of type ID if it matches one of the reserved keywords.
     * If so, it changes the type of the token to the respective keyword and creates the new token.
     * Otherwise it creates a regular token of type ID
     * @param lineNumber the line number of the token
     * @param tokenValue the string which represents the token
     * @return the newly created token object
     */
    private static Token handleID(int lineNumber, String tokenValue){
        switch (tokenValue){
            case "prog":
                return new Token(State.KPROG.getStateID(), lineNumber, tokenValue);
            case "main":
                return new Token(State.KMAIN.getStateID(), lineNumber, tokenValue);
            case "fcn":
                return new Token(State.KFCN.getStateID(), lineNumber, tokenValue);
            case "class":
                return new Token(State.KCLASS.getStateID(), lineNumber, tokenValue);
            case "float":
                return new Token(State.KFLOAT.getStateID(), lineNumber, tokenValue);
            case "int":
                return new Token(State.KINT.getStateID(), lineNumber, tokenValue);
            case "string":
                return new Token(State.KSTRING.getStateID(), lineNumber, tokenValue);
            case "if":
                return new Token(State.KIF.getStateID(), lineNumber, tokenValue);
            case "elseif":
                return new Token(State.KELSEIF.getStateID(), lineNumber, tokenValue);
            case "else":
                return new Token(State.KELSE.getStateID(), lineNumber, tokenValue);
            case "while":
                return new Token(State.KWHILE.getStateID(), lineNumber, tokenValue);
            case "input":
                return new Token(State.KINPUT.getStateID(), lineNumber, tokenValue);
            case "print":
                return new Token(State.KPRINT.getStateID(), lineNumber, tokenValue);
            case "new":
                return new Token(State.KNEW.getStateID(), lineNumber, tokenValue);
            case "return":
                return new Token(State.KRETURN.getStateID(), lineNumber, tokenValue);
            case "var":
                return new Token(State.KVAR.getStateID(), lineNumber, tokenValue);
            default:
                return new Token(State.ID.getStateID(), lineNumber, tokenValue);
        } 
    }
}



