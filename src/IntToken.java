public class IntToken extends Token {
    private int value;

    public IntToken(int id, String name, int value) {
        super(id, name);
        this.value = value;
    }
}
