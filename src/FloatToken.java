public class FloatToken extends Token {
    private double value;

    public FloatToken(int id, String name, double value) {
        super(id, name);
        this.value = value;
    }
}
