import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class StatisticInfoOnGame {

    private  static  final String attackers_side = "A";
    private  static  final String defenders_side = "D";
    private  static  final String king_side = "K";

    private PieceStatistcs[] defenders_stats;
    private PieceStatistcs[] attackers_stats;
    private ArrayList<PositionStats> positions;

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
    private String printMoves(PieceStatistcs[] arr){
        StringBuilder text = new StringBuilder();
        Arrays.sort(arr,numStepscompare);
        for (int i = 0; i < arr.length; i++) {
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

    private  String printKills(boolean isattackerwin){
        StringBuilder text = new StringBuilder();
        Arrays.sort(defenders_stats,killscompare.reversed());
        Arrays.sort(attackers_stats,killscompare.reversed());
        int d=0,a=0;
        int comp;
        while (d<defenders_stats.length && a < attackers_stats.length){
            comp = killscompare.compare(defenders_stats[d],attackers_stats[a]);
                if((comp==0 && isattackerwin) || comp<0){
                    if(attackers_stats[a].getKills()>0){
                        text.append(attackers_stats[a].printKills());
                        text.append("\n");
                    }
                    a+=1;
                }else {
                    if(defenders_stats[d].getKills()>0){
                        text.append(defenders_stats[d].printKills());
                        text.append("\n");
                    }
                    d+=1;

                }
        }

        while (d<defenders_stats.length){
            if(defenders_stats[d].getKills()>0){
                text.append(defenders_stats[d].printKills());
                text.append("\n");
            }
            d+=1;
        }

        while (a < attackers_stats.length){
            if(attackers_stats[a].getKills()>0){
                text.append(attackers_stats[a].printKills());
                text.append("\n");
            }
            a+=1;
        }
        return text.toString();
    }

    private String printDistance(boolean isattackerwin){
        StringBuilder text = new StringBuilder();
        Arrays.sort(defenders_stats,distancecompare.reversed());
        Arrays.sort(attackers_stats,distancecompare.reversed());
        int d=0,a=0;
        int comp;
        while (d<defenders_stats.length && a < attackers_stats.length){
            comp = distancecompare.compare(defenders_stats[d],attackers_stats[a]);
            if((comp==0 && isattackerwin) || comp<0){
                if(attackers_stats[a].getDistance()>0){
                    text.append(attackers_stats[a].printDistance());
                    text.append("\n");
                }
                a+=1;
            }else {
                if(defenders_stats[d].getDistance()>0){
                    text.append(defenders_stats[d].printDistance());
                    text.append("\n");
                }
                d+=1;

            }
        }

        while (d<defenders_stats.length){
            if(defenders_stats[d].getDistance()>0){
                text.append(defenders_stats[d].printDistance());
                text.append("\n");
            }
            d+=1;
        }

        while (a < attackers_stats.length){
            if(attackers_stats[a].getDistance()>0){
                text.append(attackers_stats[a].printDistance());
                text.append("\n");
            }
            a+=1;
        }
        return text.toString();
    }
    private String printStepsOnPosition(){
        StringBuilder text = new StringBuilder();
        positions.sort(positionnumstepscompare.reversed());

        for (int i = 0; i < positions.size(); i++) {
            if(positions.get(i).getNumOfPieces()<2){
                i = positions.size();
            }else{
                text.append(positions.get(i).printPieceSteps());
                text.append("\n");
            }
        }
        return text.toString();
    }

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
