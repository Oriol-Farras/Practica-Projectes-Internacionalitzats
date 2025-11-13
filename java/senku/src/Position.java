public class Position {

    private final int x;
    private final int y;

    // Constructor de la classe Position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Retorna la cordenade X de la posició
    public int getX() {
        return this.x;
    }

    //Retorna la cordenada Y de la posició
    public int getY() {
        return this.y;
    }

    // Comprova si esta a la mateixa fila o cloumna que una Posicio donada
    public boolean colinear(Position other) {
        if(this.x == other.x || this.y == other.y){
            return true;
        } else {
            return false;
        }
    }

    //Calcula la distancia de Manhattan entre la posicio actual i un altra posició
    public int distance(Position other) {
        int xDistance = this.x - other.x;
        int yDistance = this.y - other.y;

        // Si la distancia en x es negativa la converteix en positiva
        if (xDistance < 0){
            xDistance = xDistance * -1;
        }

        //Si la distancia en y es negativa la converteix en positiva
        if (yDistance < 0){
            yDistance = yDistance * -1;
        }

        int manhattanDistance = xDistance + yDistance;

        return manhattanDistance;
    }

    //Retorna la posició que esta al mig entre la posicio actual i una donada
    public Position middle(Position other) {

        int middleX = (this.x + other.x) / 2;
        int middleY = (this.y + other.y) / 2;

        return  new Position(middleX, middleY);
    }

    // Needed for testing

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
