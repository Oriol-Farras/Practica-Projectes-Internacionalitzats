public class Cell {

    private static final char C_FORBIDDEN = '#';
    private static final char C_FILLED = 'o';
    private static final char C_EMPTY = '·';

    //Definicio de les instancies de la classe per a cada estat de celda
    public static final Cell FORBIDDEN = new Cell(C_FORBIDDEN); // Per a les celdes prohivides
    public static final Cell FILLED = new Cell(C_FILLED); // Per a les celdes plenes
    public static final Cell EMPTY = new Cell(C_EMPTY); // Per a les celdes vuides

    private final char status;  //variable per a guardar el estat de cada celda

    // constructor de la classe
    private Cell(char status) {
        this.status = status;
    }

    //Metode de la classe que crea un objecte cell en funcio del caracter introduit
    public static Cell fromChar(char status) {
        if(status == C_FORBIDDEN){
            return new Cell(C_FORBIDDEN);
        }

        if (status == C_FILLED){
            return new Cell(C_FILLED);
        }

        if (status == C_EMPTY){
            return new Cell(C_EMPTY);
        }
        // En el cas que el caracter no sigui valid retrna null
        return null;
    }

    // Comprova si la celda te com a estat FORBIDDEN
    public boolean isForbidden() {
        return this.status == C_FORBIDDEN;
    }

    // Comprova si la celda te com a estat FILLED
    public boolean isFilled() {
        return this.status == C_FILLED;

    }

    // // Comprova si la celda te com a estat EMPTY
    public boolean isEmpty() {
        return this.status == C_EMPTY;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }
}
