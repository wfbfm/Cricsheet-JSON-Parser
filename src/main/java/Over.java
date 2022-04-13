import java.util.ArrayList;

public class Over {
    protected int id = 0;
    protected int runs = 0;
    protected int batterRuns = 0;
    protected int extras = 0;
    protected int wickets = 0;
    protected String bowler = "bowlername";


    protected int wides = 0;
    protected int noBalls = 0;
    protected int byes = 0;
    protected int legByes = 0;
    protected boolean isMaiden = false;

    protected ArrayList<Delivery> overDeliveries;

    public Over(int val){
        id = val;
        overDeliveries = new ArrayList<Delivery>();
    }

    // Getters
    public ArrayList<Delivery> getOverDeliveries() {
        return overDeliveries;
    }

    // Setters
    public void setRuns(int val){runs = val;}
    public void setBatterRuns(int val){batterRuns = val;}
    public void setExtras(int val){extras = val;}
    public void setWickets(int val){wickets = val;}
    public void setBowler(String val){bowler = val;}
    public void setWides(int val){wides = val;}
    public void setNoBalls(int val){noBalls = val;}
    public void setByes(int val){byes = val;}
    public void setLegByes(int val){legByes = val;}
    public void addDelivery(Delivery delivery){
        overDeliveries.add(delivery);
    }
    public void calculateMaiden(){
        if(batterRuns == 0 & wides == 0 & noBalls == 0){
            this.isMaiden = true;
        }
        else{
            this.isMaiden = false;
        }
    }

    // Print statement
    public String getInfo(){
        return "Over: " + id + "; Bowled by: " + bowler + "; Runs: " + runs + "; BatterRuns: " + batterRuns
                + "; Extras: " + extras + "; Wickets: " + wickets;
    }
    public String showDeliveries(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.overDeliveries.size(); i++) {
            if(!str.isEmpty()){
                str.append(" ");
            }
            str.append(this.overDeliveries.get(i).ballNotation);
        }
        return str.toString();
    }
}
