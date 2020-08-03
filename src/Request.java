public class Request {
    private int ID;
    private int arrivetime;
    private int endtime;
    //private int fileID;
    private double speed;
    private String label;
    private int cdnIndex;

    public int getID() {
        return ID;
    }

    public int getArrivetime() {
        return arrivetime;
    }

    public int getEndtime() {
        return endtime;
    }

    public double getSpeed() {
        return speed;
    }

    public String getLabel() {
        return label;
    }

    public int getCdnIndex() {
        return cdnIndex;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setArrivetime(int arrivetime) {
        this.arrivetime = arrivetime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCdnIndex(int cdnIndex) {
        this.cdnIndex = cdnIndex;
    }
}
