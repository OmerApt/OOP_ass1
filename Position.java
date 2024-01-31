public class Position {
   private int col;
   private int row;

   public Position(int row,int col){
      this.col = col;
      this.row = row;
   }

   public int getCol() {
      return col;
   }

   public int getRow() {
      return row;
   }

   @Override
   public String toString(){
      String output = "("+ this.row + ", " + this.col + ")";
      return output;
   }

   public boolean equals(Position pos){
      return  this.getRow()==pos.getRow() && this.getCol()==pos.getCol();
   }

}
