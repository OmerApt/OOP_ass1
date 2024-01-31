import java.util.ArrayList;
import java.util.Iterator;

public class PieceStatistcs {

    private final String type_king="♔";

   private int kills;
   private final ArrayList<Position> move_history;
   private  int distance;
   private final int id;
   private final String side;

public PieceStatistcs(int id ,String side){
    this.kills=0;
    this.move_history=new ArrayList<Position>();
    this.distance=0;
    this.id = id;
    this.side = side;
}

    public int getKills() {
        return kills;
    }
    public void addKill(){
        kills+=1;
    }
    public String printKills(){
    return this.printId() +": "+ this.kills + " kills";
    }

    public int getId(){
    return  this.id;
    }
    public String printId(){
        return  this.side + this.id;
    }

    public ArrayList<Position> getMove_history() {
        return move_history;
    }

    public String printMoveHistory(){
    return  move_history.toString();
    }


    public void addMove(Position to){
    if(!move_history.isEmpty()){
        Position last_pos = move_history.getLast();
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

    private int calculate_distance(Position a,Position b){
        return Math.abs((a.getCol()-b.getCol())+(a.getRow()-b.getRow()));
    }


    
}
