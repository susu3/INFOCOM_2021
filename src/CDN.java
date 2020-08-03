import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CDN {
    private int ID;
    private double bandwidth;
    private double bandwidth_cap;
    private double peak;   //peak bandwidth
    private String label;
    private double bcost;

    public int getID() {
        return ID;
    }

    public double getBandwidth(){
        return bandwidth;
    }

    public double getBandwidth_cap() {
        return bandwidth_cap;
    }

    public double getPeak() {
        return peak;
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

    public void setBandwidth_cap(double bandwidth_cap) {
        this.bandwidth_cap = bandwidth_cap;
    }

    public void setPeak(double peak) {
        this.peak = peak;
    }

    public void setLabel(String label){
        this.label=label;
    }

    public void setBcost(double bcost){
        this.bcost=bcost;
    }

    //compute the total cost
    public static double computeCost(HashMap<String,ArrayList<CDN>> cdn){
        double cost =0;
        Set<String> keys=cdn.keySet();
        Iterator<String> iterator1=keys.iterator();
        String key = "";
        while (iterator1.hasNext()){
            key=iterator1.next();
            ArrayList<CDN> cdns = cdn.get(key);
            for(CDN cdn_cur:cdns){
                cost = cost+ cdn_cur.getPeak()*0.95*cdn_cur.getBcost();  //95..........
            }
        }
        return cost;
    }
}
