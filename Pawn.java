public class Pawn extends ConcretePiece{

    public Pawn(Player owner,int id){
        this.owner = owner;
        this.type = "♙";
        setId_piece(id);
    }

}
