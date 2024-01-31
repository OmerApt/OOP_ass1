public class King extends ConcretePiece {
    public King (Player owner,int id){
        this.owner =  owner;
        this.type = "♔";
        setId_piece(id);
    }
}
