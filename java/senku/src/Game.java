// It knows the game rules

public class Game {

    private final Board board;

    // Constructor de la classe
    public Game(Board board) {
        this.board = board;
    }

    // metode que comprova si una posicio de origen es valida per a realitzar un moviment i matar una pesa
    public boolean isValidFrom(Position from) {
        return (this.board.isFilled(from) && canKill(from));
    }

    // Comprova que hi ha una casella on pot fer un moviment per a matar una fixa des de una posicio indicada
    private boolean canKill(Position from){
        //comprovar per a cada direccio (guardada al array Direction.ALL)
        for (int i = 0; i < Direction.ALL.length; i++){
            Direction movement = Direction.ALL[i];
            //per a que sigui valida la posicio, en aquella direccio hi ha de haver consecutivament
            //una posicio ocupada i una de buida
            if(this.board.isFilled(movement.apply(from)) && this.board.isEmpty(Direction.ALL[i].apply(movement.apply(from)))){
                return true;
            }
        }
        return false;
    }

    // Assumes validFrom is a valid starting position

    // metode que comprova su una posicio de desti es valida per a realitzar un moviment des de una posició d'origen valida
    public boolean isValidTo(Position validFrom, Position to) {
        // Comprova si la casella que es vol colocar no esta ocupada i que la posicio inicial sigui valida
        if(this.board.isEmpty(to) && isValidFrom(validFrom) && validFrom.colinear(to) && validFrom.distance(to) == 2 && this.board.isFilled(validFrom.middle(to))){

            //comprova que les dos posicions sigui colinears
            //comprova que la distancia entre les dos sigui de dos peces
            //comprova que la pesa del mig sigui FILLED

            return true;

        }
        return false;
    }

    // Assumes both positions are valid

    // metode que realitza un moviment valid desde una posicio de origen a una posicio de desti
    public Position move(Position validFrom, Position validTo) {

        // Es vuida la posicio del mig i la posicio de origen, a mes s'ocupa la posicio de desti

        this.board.emptyPosition(validFrom.middle(validTo));
        this.board.emptyPosition(validFrom);
        this.board.fillPosition(validTo);

        return validFrom.middle(validTo);
    }

    public boolean hasValidMovesFrom() {
        // per a comprovar si hi ha algun moviment valid comprovarem per a cada casella si es pot fer un moviment
        // Utilitzo un metode (boardCounter) per no repetir codi

        return this.boardCounter() >= 1;
    }

    // Metode que compta el numero de moviments valids
    public int countValidMovesFrom() {
        return this.boardCounter();
    }

    // metode que compta el numero de moviments valids des de una posicio concreta
    public int countValidMovesTo(Position validFrom) {
        int counter = 0;
        for(int i = 0; i < this.board.getWidth(); i++){
            for(int j = 0; j < this.board.getHeight(); j++){
                Position pos = new Position(i, j);
                if(isValidTo(validFrom, pos)){
                    counter += 1;
                }
            }
        }
        return counter;
    }

    // metode privat per a comptar els moviments restansts
    private int boardCounter(){
        int counter = 0;
        for(int i = 0; i < this.board.getWidth(); i++){
            for(int j = 0; j < this.board.getHeight(); j++){
                Position pos = new Position(i, j);
                if(isValidFrom(pos)){
                    counter += 1;
                }
            }
        }
        return counter;
    }
}
