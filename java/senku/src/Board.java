import java.util.StringTokenizer;

// Only a rectangle of cells. Do not know hame rules.

public class Board {

    private final int width;        // Amplada del tauler
    private final int height;       // Altura del tauler
    private final Cell[][] cells;   // Matriu de celdes que componen el tauler

    //Constructor de la classe
    public Board(int width, int height, String board) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        //Separem els caracers per els \n
        //Aixi tenim els strings amb els caracters per a cada fila

        StringTokenizer st = new StringTokenizer(board, "\n");

        String row;     //crem una variable on es guardara la fila

        //per a cada fila i cada columna crea un objecte cell amb l'atribut correponent
        //i el guarde en una matriu on la seva posicio es la mateixa que la del tauler

        for (int i = 0; i < width; i++){
            row = st.nextToken();
            for (int j = 0; j < height; j++){
                this.cells[i][j] = Cell.fromChar(row.charAt(j));
            }
        }

    }

    // Retorna l'amplada del tauler
    public int getWidth() {
        return width;
    }

    // Retorna l'altura del tauler
    public int getHeight() {
        return height;
    }

    //En les seguents tres funcions comprova que estigui dins o fora del cuadre
    // les variables que acoten el cuadre son: que siguin numeros positius per acotar la part de dalt i la esquerra
    // i que siguin mes petits que lamplada i l'alçada per la cota de la dreta i la de dalt respectivament
    public boolean isForbidden(Position pos) {
        return pos.getY() < 0 || pos.getX() < 0 || pos.getY() > getHeight() || pos.getX() > getWidth() || this.cells[pos.getY()][pos.getX()].isForbidden();
    }

    public boolean isFilled(Position pos) {
        return pos.getY() > 0 && pos.getX() > 0 && pos.getY() < getHeight() && pos.getX() < getWidth() && this.cells[pos.getY()][pos.getX()].isFilled();
    }

    public boolean isEmpty(Position pos) {
        return pos.getY() > 0 && pos.getX() > 0 && pos.getY() < getHeight() && pos.getX() < getWidth() && this.cells[pos.getY()][pos.getX()].isEmpty();
    }

    // Rellene una celda en la posicio indicada si es que no esta prohibit
    public void fillPosition(Position pos) {
        if(isForbidden(pos) == false){
            cells[pos.getY()][pos.getX()] = Cell.FILLED;
        }
    }

    // Avuida una celda en la posicio indicada si es que no esta prohibit

    public void emptyPosition(Position pos) {
        if(isForbidden(pos) == false){
            cells[pos.getY()][pos.getX()] = Cell.EMPTY;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(cells[y][x].toString());
            }
            if (y != height - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
