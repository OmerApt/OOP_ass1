import java.util.Objects;

public class GameLogic implements PlayableLogic{
    // +row is right +column is down
    private ConcretePiece[][] board;
    private ConcretePlayer attacker;
    private ConcretePlayer defender;
    private  ConcretePlayer currentlyPlaying;
    private  StatisticInfoOnGame stats;

    private final int boardsize = 11;
    private  final String type_king="♔";
    private  final String type_pawn="♙";

    private boolean game_finished;
   private enum Directions{
        HORIZONTAL,VERTICAL,BOTH
    }


    /**
     * Initializes the GameLogic instance and sets up the initial game state.
     * This includes creating players, initializing the board, and placing pieces.
     */
    public GameLogic(){
        setUpGame();
    }

    /**
     * Sets up the initial game state, including players, board, and pieces.
     * If attacker or defender is not initialized, creates new instances.
     * The current player is set to the attacker, and game statistics are initialized.
     * The game is marked as not finished, and SetUpBoard is called to set up the board.
     */
    private void setUpGame(){
       if(this.attacker == null)
       {this.attacker = new ConcretePlayer(true);}
       if(this.defender == null)
        { this.defender = new ConcretePlayer(false);}
        this.currentlyPlaying = this.attacker;
       this.stats = new StatisticInfoOnGame();
        game_finished = false;
        SetUpBoard();
    }

    /**
     * Initializes the game board and sets up pieces for both players.
     * Calls setDefendersPieces and setAttackersPieces to populate the board.
     */
    private void SetUpBoard() {
        this.board = new ConcretePiece[boardsize][boardsize];

        setDefendersPieces();
        setAttackersPieces();
    }



    /**
     * Sets up defender's pieces on the board, including pawns and a king.
     * The pieces are placed at specific positions on the board, and their information is added to game statistics.
     *  curr_id - The current ID assigned to the pieces.
     *
     */
private void setDefendersPieces(){
    int curr_id = 1 ;

    board[5][3]=new Pawn(defender,curr_id++);
    stats.addPieceToPos(board[5][3],new Position(5,3));
        for(int i=4;i<=6;i++){
            board[i][4]=new Pawn(defender,curr_id++);
            stats.addPieceToPos(board[i][4],new Position(i,4));
        }
        for(int i=3;i<=7;i++){
            if(i==5){
                board[i][5] = new King(defender,curr_id++);
                stats.addPieceToPos(board[i][5],new Position(i,5));
            }
            else {
                board[i][5] = new Pawn(defender,curr_id++);
                stats.addPieceToPos(board[i][5],new Position(i,5));
            }
    }
        for(int i=4;i<=6;i++){
            board[i][6]=new Pawn(defender,curr_id++);
            stats.addPieceToPos(board[i][6],new Position(i,6));
        }
    board[5][7]=new Pawn(defender,curr_id++);
    stats.addPieceToPos(board[5][7],new Position(5,7));
    }


    /**
     * Sets up pieces for the attacker on the game board.
     * Places pawns and handles their specific positions, updating game statistics.
     *  curr_id - The current ID assigned to the pieces.
     */
    private void setAttackersPieces(){

        int curr_id = 1 ;

        // Places attacker pawns in the leftmost column.
        for (int i = 3; i <= 7; i++) {
            board[i][0] = new Pawn(attacker,curr_id++);
            stats.addPieceToPos(board[i][0],new Position(i,0));
        }
        board[5][1]= new Pawn(attacker,curr_id++);
        stats.addPieceToPos(board[5][1],new Position(5,1));
        for(int i=3;i<=7;i++){
            if(i==5){
                // Places pawns and updates statistics for the central row.
                board[0][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[0][i] ,new Position(0,i));
                board[1][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[1][i],new Position(1,i));
                board[9][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[9][i],new Position(9,i));
                board[10][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[10][i],new Position(10,i));
            }else{
                // Places pawns and updates statistics for the edges.
                board[0][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[0][i],new Position(0,i));
                board[10][i] = new Pawn(attacker,curr_id++);
                stats.addPieceToPos(board[10][i],new Position(10,i));
            }
        }

        board[5][9]= new Pawn(attacker,curr_id++);
        stats.addPieceToPos(board[5][9],new Position(5,9));
        for (int i = 3; i <= 7; i++) {
            board[i][10] = new Pawn(attacker,curr_id++);
            stats.addPieceToPos(board[i][10],new Position(i,10));

        }
    }



    /**
     * Moves a piece from position a to position b on the game board.
     * Checks if the move is legal, updates the board, game statistics, and player turns accordingly.
     * @param a - The starting position of the piece.
     * @param b - The destination position for the piece.
     * @return boolean - True if the move is legal, false otherwise.
     */
    @Override
    public boolean move(Position a, Position b) {
        //System.out.println("check moving from:" + a.toString() + " to " + b.toString());
        boolean legal = isLegalMove(a,b);
        if(legal){
            ConcretePiece currpiece = (ConcretePiece) getPieceAtPosition(a);
            board [a.getRow()][a.getCol()]=null;
            board[b.getRow()][b.getCol()]= currpiece;
            stats.addPieceToPos(currpiece,b);
            PieceStatistcs piecestat = stats.getPieceStatistics(currpiece.getOwner().equals(attacker),currpiece.getId_piece());
            if(piecestat.getNumOfMoves()==0){
                piecestat.addMove(a);
            }
            piecestat.addMove(b);
            if(Objects.equals(currpiece.getType(), type_king) && isEdge(b)){
                game_finished=true;
            }
            checkKills(b);
            handlePlayersTurn();
        }

        return legal;
    }


    /**
     * Handles the transition between player turns.
     * If the game is finished, prints game statistics and declares the winner.
     * Otherwise, switches the turn to the next player.
     */
private void handlePlayersTurn(){
       if(game_finished){
           this.currentlyPlaying.addWin();
           String text = stats.PrintGameStats(this.currentlyPlaying.equals(this.attacker));
           System.out.println(text);
       }else {
           // Switches the turn to the next player.
           if(this.currentlyPlaying == this.attacker){
               this.currentlyPlaying = this.defender;
           }else{
               this.currentlyPlaying = this.attacker;
           }
       }

}



    /**
     * Checks if a move from one position to another is legal.
     * Validates the move based on piece ownership, type, and specific movement rules.
     * @param from - The starting position of the piece.
     * @param to - The destination position for the piece.
     * @return boolean - True if the move is legal, false otherwise.
     */
    private boolean isLegalMove (Position from,Position to ){
        Piece current = getPieceAtPosition(from);

        // Checks if the piece belongs to the current player.
        if(current.getOwner() != this.currentlyPlaying){
            return false;
        }

        // Ensures that pawns cannot move to the edge positions.
        if(Objects.equals(current.getType(), type_pawn) && isEdge(to)){
            return  false;
        }


        boolean is_same = ((from.getCol() == to.getCol()) && (from.getRow() == to.getRow()));
        // Ensures that pawns cannot move to the edge positions.
        if ((from.getRow() != to.getRow() && from.getCol() != to.getCol() )|| is_same){
            return  false;
        }
        else{
            int diff=0;
            int dir;
            Piece next;
            if (from.getCol()==to.getCol()){
                diff=from.getRow()-to.getRow();
                dir = (diff>0)?-1:1;
                // Checks for pieces in the vertical path.
                for(int i=from.getRow();i!=to.getRow();i+=dir){
                    next = board[i+dir][from.getCol()];
                    if(next != null)
                        return  false;
                }
            } else{
                diff=from.getCol()-to.getCol();
                dir = (diff>0)?-1:1;
                // Checks for pieces in the horizontal path.
                for(int i=from.getCol();i!=to.getCol();i+=dir){
                    next = board[from.getRow()][i+dir];
                    if(next != null)
                        return  false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if a piece at a specified position is killed based on its surroundings.
     * The method checks if the piece is in danger and determines if it should be killed.
     * @param a - The position of the piece to check for being killed.
     * direct - The direction to check for danger (VERTICAL or HORIZONTAL).
     * @return int - 1 if the piece is killed, 0 otherwise.
     */

    private void checkKills(Position a){
        Piece curr = getPieceAtPosition(a);
       if(curr == null|| Objects.equals(curr.getType(), type_king)){
            return ;
        }

        Position currUp = new Position(a.getRow(),a.getCol()-1);
        Position currDown = new Position(a.getRow(),a.getCol()+1);
        Position currLeft= new Position(a.getRow()-1,a.getCol());
        Position currRight = new Position(a.getRow()+1,a.getCol());

       if(1==isKilled(currUp,Directions.VERTICAL)){
           killPiece(a,currUp);
       }
        if(1==isKilled(currDown,Directions.VERTICAL)){
            killPiece(a,currDown);
        }
        if(1==isKilled(currLeft,Directions.HORIZONTAL)){
            killPiece(a,currLeft);
        }
        if(1==isKilled(currRight,Directions.HORIZONTAL)){
            killPiece(a,currRight);
        }
    }
    // returns 1 if the piece at pos(row,col) is killed (deleted) and 0 (not deleted) otherwise
    private int isKilled(Position a,Directions direct){
        Piece p = getPieceAtPosition(a);
        if (p == null) {
            return 0;
        }else
        {
            if(p.getType()==type_king){
                // Special handling for the king.
                return isKingKilled(a)?2:0;
            }
            else switch (direct) {
                case VERTICAL -> {
                    // Checks for danger in both up and down directions.
                    boolean down_danger = isDanger(a, new Position(a.getRow(), a.getCol()+1));
                    boolean up_danger = isDanger(a, new Position(a.getRow(), a.getCol()-1));
                    if (up_danger && down_danger) {
                        return 1;
                    }
                }
                case HORIZONTAL -> {
                    // Checks for danger in both left and right directions.
                    boolean left_danger = isDanger(a, new Position(a.getRow()-1, a.getCol()));
                    boolean right_danger = isDanger(a, new Position(a.getRow()+1, a.getCol()));
                    if (left_danger && right_danger) {
                        return 1;
                    }
                }


            }
        }
        // If no danger, the piece is not killed.
        return  0;
    }
private boolean isKingKilled(Position a){
    boolean left_danger = isDanger(a, new Position(a.getRow() - 1, a.getCol()));
    boolean right_danger = isDanger(a, new Position(a.getRow() + 1, a.getCol()));
    boolean up_danger = isDanger(a, new Position(a.getRow(), a.getCol() + 1));
    boolean down_danger = isDanger(a, new Position(a.getRow(), a.getCol() - 1));
    if (left_danger && right_danger && up_danger && down_danger) {
        game_finished = true;
        return true;
    }
    return  false;
}
   private void killPiece(Position eater,Position eaten){
        // credit for eater by killing eaten
       ConcretePiece p = (ConcretePiece) getPieceAtPosition(eater);
       PieceStatistcs ps = stats.getPieceStatistics(p.getOwner().equals(attacker),p.getId_piece());
       ps.addKill();

        board[eaten.getRow()][eaten.getCol()] = null;
    }

    // check if piece in position "b" is a danger to piece in position "a"
    private boolean isDanger(Position a,Position b){
        if(isInBounds(b.getRow(),b.getCol())||isEdge(b)){
            Piece a_piece = getPieceAtPosition(a);
            Piece b_piece = getPieceAtPosition(b);
            return  (b_piece != null && a_piece.getOwner() != b_piece.getOwner());
        }else{
            return  true;
        }
    }

    private  boolean isInBounds(int row, int column){
        return  (row >= 0)&&(row <=10)&&(column>=0)&&(column<=10);
    }
    @Override
    public Piece getPieceAtPosition(Position position) {
        if(isInBounds(position.getRow(), position.getCol())){
            return board[position.getRow()][position.getCol()];
        }else {
            return  null;
        }
    }
    private Piece getPieceAtPosition (int row, int col) {
        if(isInBounds(row,col)){
            return board[row][col];
        }else {
            return  null;
        }
    }
    private  boolean isEdge(Position to){
        int row = to.getRow();
        int col = to.getCol();
        boolean edge = false;
        if(row==0){
            if(col==0 || col ==10){
                edge=true;
            }
        }
        if(row == 10){
            if(col == 0 || col==10){
                edge=true;
            }
        }
        return edge;
    }
    @Override
    public Player getFirstPlayer() {

        return defender;
    }

    @Override
    public Player getSecondPlayer() {
        return attacker;
    }

    @Override
    public boolean isGameFinished() {
//       if(game_finished){
//          String text = stats.PrintGameStats(this.currentlyPlaying.equals(this.attacker));
//          System.out.println(text);
//       }
        return game_finished;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return (this.currentlyPlaying == this.defender);
    }

    @Override
    public void reset() {
        setUpGame();
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return this.boardsize;
    }
}
