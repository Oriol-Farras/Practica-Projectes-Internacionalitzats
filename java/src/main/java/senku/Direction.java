package senku;
public class Direction {

    // definim les posibles direccions com a objectes Direction
    public static final Direction UP = new Direction(0,-1);  // Amunt (0,-1)
    public static final Direction RIGHT = new Direction(1,0); // Dreta (1,0)
    public static final Direction DOWN = new Direction(0,1); // Avall (0, 1)
    public static final Direction LEFT = new Direction(-1,0); // esquerra (-1, 0)

    public static final Direction[] ALL = new Direction[]{UP, RIGHT, DOWN, LEFT};

    private final int dx;
    private final int dy;

    //Creem el constructor de la classe
    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Metode que aplica una direccio a una posicio i retorna el reultat
    public Position apply(Position from) {
        return new Position(from.getX() + this.dx, from.getY() + this.dy);
    }
}
