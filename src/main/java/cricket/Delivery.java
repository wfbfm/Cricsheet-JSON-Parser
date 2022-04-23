package cricket;

public class Delivery extends Over {

    protected String batter = "battername";
    protected String nonStriker = "nonstrikername";
    protected String howOut = "notOut";
    protected String whoOut = "noOne";
    protected boolean isDot = false;
    protected String ballNotation = "";
    protected String fielder = "";

    public Delivery(int val) {
        super(val);
    }

    // Setters
    public void setBatter(String val) {
        batter = val;
    }

    public void setNonStriker(String val) {
        nonStriker = val;
    }

    public void setHowOut(String val) {
        howOut = val;
    }

    public void setWhoOut(String val) {
        whoOut = val;
    }

    public void setFielder(String val) {
        fielder = val;
    }

    public void calculateDot() {
        if (runs == 0) {
            this.isDot = true;
        } else {
            this.isDot = false;
        }
    }

    // Scoring notation
    public void setBallNotation() {
        StringBuilder str = new StringBuilder();

        // Append runs
        if (this.runs > 0) {
            if (this.batterRuns > 0) {
                str.append(this.batterRuns);
            }
            // Append extras
            if (this.wides > 0) {
                if (this.extras > 1) {
                    str.append(String.valueOf(this.extras - 1) + "wd");
                } else {
                    str.append("wd");
                }
            }
            if (this.noBalls > 0) {
                if (this.extras > 1) {
                    str.append(String.valueOf(this.extras - 1) + "nb");
                } else {
                    str.append("nb");
                }
            }
            if (this.legByes > 0) {
                str.append(String.valueOf(this.runs) + "lb");
            }
            if (this.byes > 0) {
                str.append(String.valueOf(this.runs) + "b");
            }
            // Append wickets
            if (this.wickets > 0) {
                str.append("W");
            }
        }

        // Check if dot ball/wickets taken
        if (this.isDot) {
            if (this.wickets == 0) {
                str.append(".");
            } else {
                str.append("W");
            }
        }

        this.ballNotation = str.toString();
    }

    // Print statement
    @Override
    public String getInfo() {
        return "Ball: " + id + " " + ballNotation + "; Bowled by: " + bowler + "; Faced by: " + batter + "; Runs: " + runs + "; BatterRuns: " + batterRuns
                + "; Extras: " + extras + "; Wickets: " + wickets + "; How Out: " + howOut + "; Who Out: " + whoOut
                + "; Dot: " + isDot;
    }

}
