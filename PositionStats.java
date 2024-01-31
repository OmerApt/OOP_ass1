import java.util.ArrayList;

public class PositionStats {

    private ArrayList<ConcretePiece> pieces_stepped;

    private Position pos;

    public PositionStats(int row,int col){
        pieces_stepped = new ArrayList<>();
        this.pos = new Position(row,col);
    }

    public Position getpos() {
        return pos;
    }

    public  void addPiece(ConcretePiece p){
        if(!containsPiece(p)){
            pieces_stepped.add(p);
        }
    }

    public int getNumOfPieces(){
        return pieces_stepped.size();
    }
    public  String printPieceSteps(){
        return this.pos.toString()+this.getNumOfPieces()+" pieces" ;
    }
    private boolean containsPiece(ConcretePiece p){
        for (int i = 0; i < pieces_stepped.size(); i++) {
            if(pieces_stepped.get(i).equals(p))
                return  true;
        }
        return  false;
    }

}
