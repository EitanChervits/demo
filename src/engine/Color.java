package engine;

public enum Color {
    WHITE, BLACK;

    public Color opponent() {
        return this == WHITE ? BLACK : WHITE;
    }

    @Override
    public String toString() {
        String str = super.toString().toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
