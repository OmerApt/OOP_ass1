import java.util.ArrayList;
import java.util.Iterator;

/**
 * PieceStatistcs class represents the statistics of a game piece, such as kills, moves, and distance traveled.
 */
public class PieceStatistcs {

    private final String type_king="♔";

   private int kills;
   private final ArrayList<Position> move_history;
   private  int distance;
   private final int id;
   private final String side;


    /**
     * Constructor to initialize PieceStatistcs with specified id and side.
     * @param id - The unique identifier for the piece.
     * @param side - The side of the player to whom the piece belongs.
     */
public PieceStatistcs(int id ,String side){
    this.kills=0;
    this.move_history=new ArrayList<Position>();
    this.distance=0;
    this.id = id;
    this.side = side;
}


    /**
     * Gets the number of kills associated with the piece.
     * @return int - The number of kills.
     */
    public int getKills() {
        return kills;
    }


    /**
     * Increments the kill count for the piece.
     */
    public void addKill(){
        kills+=1;
    }
    public String printKills(){
    return this.printId() +": "+ this.kills + " kills";
    }


    /**
     * Gets the unique identifier for the piece.
     * @return int - The unique identifier for the piece.
     */
    public int getId(){
    return  this.id;
    }


    /**
     * Generates a formatted string representing the identifier for the piece.
     * @return String - Formatted string with the piece identifier.
     */
    public String printId(){
        return  this.side + this.id;
    }


    /**
     * Gets the list of positions representing the move history of the piece.
     * @return ArrayList<Position> - The list of positions the piece has moved to.
     */
    public ArrayList<Position> getMove_history() {
        return move_history;
    }


    /**
     * Generates a formatted string representing the move history of the piece.
     * @return String - Formatted string with the move history.
     */
    public String printMoveHistory(){
    return  move_history.toString();
    }


    /**
     * Adds a move to the piece's move history and updates the distance traveled.
     * @param to - The destination position for the move.
     */
    public void addMove(Position to){
    if(!move_history.isEmpty()){
        Position last_pos = move_history.get(move_history.size()-1);
            distance+= calculate_distance(last_pos,to);
    }
        move_history.add(to);
    }
    public int getDistance(){
        return  distance;
    }
    public String printDistance(){
    return this.printId()+": "+this.getDistance() +" squares";
    }

    public int getNumOfMoves(){
        return move_history.size();
    }


    /**
     * Calculates the distance between two positions.
     * @param a - The starting position.
     * @param b - The destination position.
     * @return int - The distance between the two positions.
     */
    private int calculate_distance(Position a,Position b){
        return Math.abs((a.getCol()-b.getCol())+(a.getRow()-b.getRow()));
    }


    
}
