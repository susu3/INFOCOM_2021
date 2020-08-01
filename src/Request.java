public class Request {
    private int ID;
    private int arrivetime;
    private int endtime;
    //private int fileID;
    private int speed;
    private String label;

    public int getID() {
        return ID;
    }

    public int getArrivetime() {
        return arrivetime;
    }

    public int getEndtime() {
        return endtime;
    }

    public int getSpeed() {
        return speed;
    }

    public String getLabel() {
        return label;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
