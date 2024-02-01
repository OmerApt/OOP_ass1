import java.util.ArrayList;



/**
 * The PositionStats class keeps track of pieces that have stepped on a specific position on the game board.
 */
public class PositionStats {

    private ArrayList<ConcretePiece> pieces_stepped;

    private Position pos;


    /**
     * Constructor to initialize PositionStats with the specified row and column values.
     * @param row - The row coordinate.
     * @param col - The column coordinate.
     */
    public PositionStats(int row,int col){
        pieces_stepped = new ArrayList<>();
        this.pos = new Position(row,col);
    }


    public Position getpos() {
        return pos;
    }


    /**
     * Adds a piece to the list of pieces that have stepped on the position.
     * @param p - The ConcretePiece to be added.
     */
    public  void addPiece(ConcretePiece p){
        if(!containsPiece(p)){
            pieces_stepped.add(p);
        }
    }


    /**
     * Gets the number of pieces that have stepped on the position.
     * @return int - The number of pieces.
     */
    public int getNumOfPieces(){
        return pieces_stepped.size();
    }


    /**
     * Generates a formatted string representing the position and the number of pieces that have stepped on it.
     * @return String - Formatted string with the position and the number of pieces.
     */
    public  String printPieceSteps(){
        return this.pos.toString()+this.getNumOfPieces()+" pieces" ;
    }


    /**
     * Checks if a specific piece has already stepped on the position.
     * @param p - The ConcretePiece to check.
     * @return boolean - True if the piece has stepped on the position, false otherwise.
     */
    private boolean containsPiece(ConcretePiece p){
        for (int i = 0; i < pieces_stepped.size(); i++) {
            if(pieces_stepped.get(i).equals(p))
                return  true;
        }
        return  false;
    }

}
