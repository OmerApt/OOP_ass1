public class ConcretePiece implements Piece {

    // ♔
    // ♙
    private int id_piece;
    protected Player owner;
    //♟ for pawn
    protected String type;

    public Player getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }


    public int getId_piece() {
        return id_piece;
    }

    protected void setId_piece(int id_piece) {
        this.id_piece = id_piece;
    }

    public boolean equals(ConcretePiece cp){
        return  this.id_piece == cp.id_piece && this.getOwner().equals(cp.getOwner());
    }
}
