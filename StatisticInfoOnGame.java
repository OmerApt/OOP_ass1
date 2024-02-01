import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * The StatisticInfoOnGame class keeps track of various statistics during the gameplay, including moves, kills, distances, and positions.
 */
public class StatisticInfoOnGame {

    private  static  final String attackers_side = "A";
    private  static  final String defenders_side = "D";
    private  static  final String king_side = "K";

    private PieceStatistcs[] defenders_stats;
    private PieceStatistcs[] attackers_stats;
    private ArrayList<PositionStats> positions;



    // Comparators for sorting statistics
    private Comparator<PieceStatistcs> distancecompare = new Comparator<PieceStatistcs>() {
        @Override
        public int compare(PieceStatistcs o1, PieceStatistcs o2) {
            if(o1.getDistance() == o2.getDistance())
            {
                return Integer.compare(o2.getId(),o1.getId());
            }else
            return Integer.compare(o1.getDistance(),o2.getDistance());
        }
    };
    private Comparator<PieceStatistcs> numStepscompare = new Comparator<PieceStatistcs>() {
        @Override
        public int compare(PieceStatistcs o1, PieceStatistcs o2) {
            return Integer.compare(o1.getNumOfMoves(),o2.getNumOfMoves());
        }
    };

    private Comparator<PieceStatistcs> killscompare = new Comparator<PieceStatistcs>() {
        @Override
        public int compare(PieceStatistcs o1, PieceStatistcs o2) {
            return Integer.compare(o1.getKills(),o2.getKills());
        }
    };

    private Comparator<PieceStatistcs> idcompare = new Comparator<PieceStatistcs>() {
        @Override
        public int compare(PieceStatistcs o1, PieceStatistcs o2) {
            return Integer.compare(o1.getId(),o2.getId());
        }
    };
     private Comparator<PositionStats> positionnumstepscompare = new Comparator<PositionStats>() {
         @Override
         public int compare(PositionStats o1, PositionStats o2) {
             if(o1.getNumOfPieces()==o2.getNumOfPieces()){
                if (o1.getpos().getRow()==o2.getpos().getRow()) {
                   return Integer.compare(o2.getpos().getCol(),o1.getpos().getCol());
                }
                else {
                    return Integer.compare(o2.getpos().getRow(),o1.getpos().getRow());
                }

             }

             return  Integer.compare(o1.getNumOfPieces(),o2.getNumOfPieces());
         }
     };

    /**
     * Constructor to initialize the StatisticInfoOnGame object with initial statistics.
     */
    public StatisticInfoOnGame(){
        this.positions = new ArrayList<PositionStats>();
        this.attackers_stats = new PieceStatistcs[24];
        this.defenders_stats = new PieceStatistcs[13];
        for(int i = 0; i < this.attackers_stats.length;i++){
            attackers_stats[i] = new PieceStatistcs(i+1,attackers_side);
        }
        for (int i = 0; i < this.defenders_stats.length; i++) {
            if(i == 6){
                defenders_stats[i] = new PieceStatistcs(i+1,king_side);
            }else{
                defenders_stats[i] = new PieceStatistcs(i+1,defenders_side);
            }
        }

    }


    /**
     * Adds a ConcretePiece to the statistics associated with a specific position on the game board.
     * @param cp - The ConcretePiece to be added.
     * @param pos - The position on the game board.
     */
    public  void addPieceToPos(ConcretePiece cp,Position pos){
        PositionStats ps = find(pos);
        if(ps != null){
          ps.addPiece(cp);
        }else {
            ps = new PositionStats(pos.getRow(),pos.getCol());
            ps.addPiece(cp);
            positions.add(ps);
        }
    }
    private PositionStats find(Position pos){
        int i = 0;
        while (i<positions.size()){
            if(pos.equals(positions.get(i).getpos())){
                return positions.get(i);
            }
            i++;
        }
        return null;
    }


    /**
     * Generates a string with various game statistics, including moves, kills, distances, and positions.
     * @param isattackerwin - A boolean indicating if the attackers won the game.
     * @return String - A formatted string containing game statistics.
     */
    public String PrintGameStats(boolean isattackerwin){
        String text="";

        if(isattackerwin){
            text += printMoves(attackers_stats);
            text += printMoves(defenders_stats);
        }else {
            text += printMoves(defenders_stats);
            text += printMoves(attackers_stats);
        }

        text += "*".repeat(75).concat("\n");

        text+= printKills(isattackerwin);

        text += "*".repeat(75).concat("\n");

        text+= printDistance(isattackerwin);

        text += "*".repeat(75).concat("\n");

        text+= printStepsOnPosition();

        text += "*".repeat(75);

        return  text;
    }


    /**
     * Generates a string representation of piece moves, sorted by the number of moves in ascending order.
     * @param arr - Array of PieceStatistcs to be printed.
     * @return String - Formatted string containing piece moves.
     */
    private String printMoves(PieceStatistcs[] arr){
        StringBuilder text = new StringBuilder();
        Arrays.sort(arr,numStepscompare);
        for (int i = 0; i < arr.length; i++) {
            // Check if the piece has made any moves
            if(arr[i].getNumOfMoves()>0)
            {
                text.append(arr[i].printId());
                text.append(": ");
                text.append(arr[i].printMoveHistory());
                text.append("\n");
            }
        }
        return text.toString();
    }


    /**
     * Generates a string representation of piece kills, sorted by the number of kills in descending order.
     * @param isattackerwin - A boolean indicating if the attackers won the game.
     * @return String - Formatted string containing piece kills.
     */
    private  String printKills(boolean isattackerwin){
        StringBuilder text = new StringBuilder();
        Arrays.sort(defenders_stats,killscompare.reversed());
        Arrays.sort(attackers_stats,killscompare.reversed());
        int d=0,a=0;
        int comp;
        while (d<defenders_stats.length && a < attackers_stats.length){
            comp = killscompare.compare(defenders_stats[d],attackers_stats[a]);
                if((comp==0 && isattackerwin) || comp<0){
                    // Check if the attacker piece has made any kills
                    if(attackers_stats[a].getKills()>0){
                        text.append(attackers_stats[a].printKills());
                        text.append("\n");
                    }
                    a+=1;
                }else {
                    // Check if the defender piece has made any kills
                    if(defenders_stats[d].getKills()>0){
                        text.append(defenders_stats[d].printKills());
                        text.append("\n");
                    }
                    d+=1;

                }
        }
        // Add remaining defender kills
        while (d<defenders_stats.length){
            if(defenders_stats[d].getKills()>0){
                text.append(defenders_stats[d].printKills());
                text.append("\n");
            }
            d+=1;
        }
        // Add remaining attacker kills
        while (a < attackers_stats.length){
            if(attackers_stats[a].getKills()>0){
                text.append(attackers_stats[a].printKills());
                text.append("\n");
            }
            a+=1;
        }
        return text.toString();
    }


    /**
     * Generates a string representation of piece distances, sorted by the distance in descending order.
     * @param isattackerwin - A boolean indicating if the attackers won the game.
     * @return String - Formatted string containing piece distances.
     */
    private String printDistance(boolean isattackerwin){
        StringBuilder text = new StringBuilder();
        Arrays.sort(defenders_stats,distancecompare.reversed());
        Arrays.sort(attackers_stats,distancecompare.reversed());
        int d=0,a=0;
        int comp;
        while (d<defenders_stats.length && a < attackers_stats.length){
            comp = distancecompare.compare(defenders_stats[d],attackers_stats[a]);
            if((comp==0 && isattackerwin) || comp<0){
                // Check if the attacker piece has covered any distance
                if(attackers_stats[a].getDistance()>0){
                    text.append(attackers_stats[a].printDistance());
                    text.append("\n");
                }
                a+=1;
            }else {
                // Check if the defender piece has covered any distance
                if(defenders_stats[d].getDistance()>0){
                    text.append(defenders_stats[d].printDistance());
                    text.append("\n");
                }
                d+=1;

            }
        }
        // Add remaining defender distances
        while (d<defenders_stats.length){
            if(defenders_stats[d].getDistance()>0){
                text.append(defenders_stats[d].printDistance());
                text.append("\n");
            }
            d+=1;
        }
        // Add remaining attacker distances
        while (a < attackers_stats.length){
            if(attackers_stats[a].getDistance()>0){
                text.append(attackers_stats[a].printDistance());
                text.append("\n");
            }
            a+=1;
        }
        return text.toString();
    }


    /**
     * Generates a string representation of steps on each position, sorted by the number of pieces on a position in descending order.
     * @return String - Formatted string containing steps on positions.
     */
    private String printStepsOnPosition(){
        StringBuilder text = new StringBuilder();
        positions.sort(positionnumstepscompare.reversed());

        for (int i = 0; i < positions.size(); i++) {
            if(positions.get(i).getNumOfPieces()<2){
                // Check if the position has at least two pieces that stepped on it.

                i = positions.size();
            }else{
                text.append(positions.get(i).printPieceSteps());
                text.append("\n");
            }
        }
        return text.toString();
    }


    /**
     * Retrieves the PieceStatistcs for a specific piece based on its owner (attackers/defenders) and ID.
     * @param attackers - A boolean indicating if the piece belongs to attackers.
     * @param id - The ID of the piece.
     * @return PieceStatistcs - The statistics for the specified piece.
     */
    public  PieceStatistcs getPieceStatistics(boolean attackers, int id){
        if(attackers){
            return this.attackers_stats[id-1];
        }else return this.defenders_stats[id - 1];
    }
    public void add_move(boolean attackers,int id,Position to){
        PieceStatistcs cur;
        if(attackers){
            cur = this.attackers_stats[id-1];
            cur.addMove(to);
        }else{
            cur = this.defenders_stats[id-1];
            cur.addMove(to);
        }
    }


    /**
     * Adds a kill to the statistics of a specific piece based on its owner (attackers/defenders) and ID.
     * @param attackers - A boolean indicating if the piece belongs to attackers.
     * @param id - The ID of the piece.
     */
    public void add_kill(boolean attackers,int id){
        PieceStatistcs cur;
        if(attackers){
            cur = this.attackers_stats[id-1];
            cur.addKill();
        }else{
            cur = this.defenders_stats[id-1];
            cur.addKill();;
        }
    }
}
