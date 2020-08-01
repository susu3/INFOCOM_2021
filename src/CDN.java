public class CDN {
    private int ID;
    private double bandwidth;
    private String label;
    private double bcost;

    public int getID() {
        return ID;
    }

    public double getBandwidth(){
        return bandwidth;
    }

    public String getLabel(){
        return label;
    }

    public double getBcost(){
        return bcost;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setBandwidth(double bandwidth){
        this.bandwidth=bandwidth;
    }

    public void setLabel(String label){
        this.label=label;
    }

    public void setBcost(double bcost){
        this.bcost=bcost;
    }
}
