import acm.graphics.GDimension;
import acm.graphics.GPoint;

public class Geometry {

    private final int windowWidth;
    private final int windowHeight;
    private final int numCols;
    private final int numRows;
    private final double boardPadding;
    private final double cellPadding;

    // Constructor de la classe
    public Geometry(int windowWidth, int windowHeight, int numCols, int numRows, double boardPadding, double cellPadding) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.numCols = numCols;
        this.numRows = numRows;
        this.boardPadding = boardPadding;
        this.cellPadding = cellPadding;
    }

    // Retorna el numero de files del tauler
    public int getRows() {
        return this.numRows;
    }

    // retorna el numero de columnes del tauler
    public int getColumns() {
        return this.numCols;
    }

    // Retorna les dimensions del tauler restantli el padding
    public GDimension boardDimension() {
        //les dimensions de la pantalla seran les dimensions que indiquem al constructor menos dos vegades el padding ja que esta en els dos cantons de pantalla
        //ja que el pading es relatiu ja que es amb tant per 1 els caclculo a part

        double widthPadding = this.windowWidth * this.boardPadding;
        double heightPadding = this.windowHeight * this.boardPadding;

        return new GDimension(this.windowWidth -2*widthPadding, this.windowHeight -2*heightPadding);
    }

    // Metode que retorna el punt superior esquerra del tauler
    public GPoint boardTopLeft() {
        double widthPadding = this.windowWidth * this.boardPadding;
        double heightPadding = this.windowHeight * this.boardPadding;

        return new GPoint(widthPadding, heightPadding);
    }

    // metode que retorna les dimensions de cada celda
    public GDimension cellDimension() {
        return new GDimension(this.boardDimension().getWidth() / this.getColumns(), this.boardDimension().getHeight() / this.getRows());
    }

    // metode que retorna el canto superior esquerra de la celda amb cordenades x e y
    public GPoint cellTopLeft(int x, int y) {

        // desde el origen de cordenades i sumem la longitud duna casella tantes vegades com x o y
        double cellX = this.boardTopLeft().getX();
        cellX = cellX + this.cellDimension().getWidth() * x;

        double cellY = this.boardTopLeft().getY();
        cellY = cellY + this.cellDimension().getHeight() * y;

        return new GPoint(cellX, cellY);
    }

    // metode que retorna la dimensio de cada ficha
    public GDimension tokenDimension() {

        double cellWidth = this.cellDimension().getWidth();
        double cellHeight = this.cellDimension().getHeight();

        double widthTokenPadding = cellWidth * this.cellPadding;
        double heightTokenPadding = cellHeight * this.cellPadding;

        return new GDimension(cellWidth -2 * widthTokenPadding, cellHeight -2 * heightTokenPadding);
    }

    // metode que retorna el canto superior esquerra de la ficha que esta a la celda amb cordenades x e y
    public GPoint tokenTopLeft(int x, int y) {

        double positionX = cellTopLeft(x,y).getX();
        positionX = positionX + this.cellDimension().getWidth() * this.cellPadding;

        double positionY = cellTopLeft(x,y).getY();
        positionY = positionY + this.cellDimension().getHeight() * this.cellPadding;

        return new GPoint(positionX, positionY);
    }

    // Calculem a partir de una posicio x e y en pantalla en quina posicio es troba
    public Position xyToCell(double x, double y) {

        GPoint TopLeft = boardTopLeft();
        GDimension dimension = cellDimension();

        double dx = x - TopLeft.getX();
        double dy = y - TopLeft.getY();

        int cellX = (int) (dx / dimension.getWidth());
        int cellY = (int) (dy / dimension.getHeight());

        return new Position(cellX, cellY);
    }

    // Retornem el centre del token
    public GPoint centerAt(int x, int y) {

        double middleX = this.tokenTopLeft(x,y).getX() + (this.tokenDimension().getWidth() / 2);
        double middleY = this.tokenTopLeft(x,y).getY() + (this.tokenDimension().getHeight() / 2);

        return new GPoint(middleX, middleY);
    }
}
