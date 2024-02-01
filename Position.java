
/**
 * The Position class represents a location on a 2D game board using row and column coordinates.
 */
public class Position {
   private int col;
   private int row;


   /**
    * Constructor to initialize a Position with specified row and column values.
    * @param row - The row coordinate.
    * @param col - The column coordinate.
    */
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


   /**
    * Provides a string representation of the position in the format (row, col).
    * @return String - Formatted string representing the position.
    */
   @Override
   public String toString(){
      String output = "("+ this.row + ", " + this.col + ")";
      return output;
   }


   /**
    * Checks if two positions are equal by comparing their row and column coordinates.
    * @param pos - The position to compare.
    * @return boolean - True if the positions are equal, false otherwise.
    */
   public boolean equals(Position pos){
      return  this.getRow()==pos.getRow() && this.getCol()==pos.getCol();
   }

}

