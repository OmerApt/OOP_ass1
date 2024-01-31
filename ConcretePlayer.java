public class ConcretePlayer implements Player{
    private int wins;
    private final Boolean attacker;

    public ConcretePlayer(Boolean is_attacker){
        this.attacker = is_attacker;
    }

    @Override
    public boolean isPlayerOne() {

        return !this.attacker;

    }

    @Override
    public int getWins() {
        return this.wins;
    }

    public void addWin(){
        this.wins +=1;
    }
}
