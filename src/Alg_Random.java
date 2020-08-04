import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class Alg_Random {

    public double algRandom(LinkedList<Request> request,HashMap<String,ArrayList<CDN>> cdn){
        double total_cost =0;
        int target=0;
        CDN tar_cdn=new CDN();
        Request tar_s=new Request();
        //加入优先级队列
        PriorityQueue queue = new PriorityQueue();
        for(int i=0;i<request.size();i++){
            queue.push(request.get(i),i);
        }

        PriorityStruct ps = new PriorityStruct();
        while(!queue.isEmpty()){
            ps=queue.pop();
            Request s = ps.getRequest();

            if(ps.getLabel().equals("arrivetime")){   //分带宽
                String label_s =s.getLabel();
                if(cdn.containsKey(label_s)) {
                    ArrayList<CDN> cdns = cdn.get(label_s);
                    int size = cdns.size();
                    do{
                        target = randomCDN(size);
                        tar_cdn = cdns.get(target);     //随机被分配到tar_cdn节点
                    }while(tar_cdn.getBandwidth()<s.getSpeed());
                    request.get(ps.getIndex()).setCdnIndex(target);
                    tar_cdn.setBandwidth(tar_cdn.getBandwidth()-s.getSpeed());
                    if (tar_cdn.getPeak() < (tar_cdn.getBandwidth_cap() - tar_cdn.getBandwidth()))  //更新peak
                        tar_cdn.setPeak((tar_cdn.getBandwidth_cap() - tar_cdn.getBandwidth()));
                }else
                    continue;

            }else if(ps.getLabel().equals("endtime")){   //释放带宽
                tar_s = request.get(ps.getIndex());  //在初始request列表中的request,为了查到对应的CDN节点来归还带宽
                String label_s =tar_s.getLabel();
                if(cdn.containsKey(label_s)) {
                    ArrayList<CDN> cdns = cdn.get(label_s);
                    tar_cdn=cdns.get(tar_s.getCdnIndex());
                    tar_cdn.setBandwidth(tar_cdn.getBandwidth()+tar_s.getSpeed());
                }else
                    continue;
            }else{
                System.out.print("algRandom label bug!");
                System.exit(1);
            }

        }

        total_cost = CDN.computeCost(cdn);
        return total_cost;
    }


    //random select a CDN node
    private int randomCDN(int num) {
        Random ran = new Random();
        return ran.nextInt(num);
    }


}



/**错误的尝试！！当s.getArrivetime()+time < cur_time时无法解决
    public double algRandom(LinkedList<Request> request,HashMap<String,ArrayList<CDN>> cdn){
        double total_cost =0;
        String label_s="";
        int size=0;
        int target=0;
        CDN tar_cdn;
        double bandwidth;
        CountDownLatch countDownLatch = new CountDownLatch(request.size());
        long time, cur_time;

        time = System.currentTimeMillis()/1000;
        for(Request s: request){
            label_s =s.getLabel();
            if(cdn.containsKey(label_s)){

                ArrayList<CDN> cdns = cdn.get(label_s);
                size=cdns.size();
                target=randomCDN(size);
                tar_cdn=cdns.get(target);     //随机被分配到tar_cdn节点

                cur_time = System.currentTimeMillis()/1000;
                if(s.getArrivetime()+time > cur_time) {
                    try {
                        Thread.sleep((cur_time - s.getArrivetime() - time) * 1000);
                    } catch (InterruptedException e) {
                        //执行出错
                    }
                }

                tar_cdn.setBandwidth(tar_cdn.getBandwidth()-s.getSpeed());   //占用指定CDN节点带宽
                if(tar_cdn.getPeak()<(tar_cdn.getBandwidth_cap()-tar_cdn.getBandwidth()))  //更新peak
                    tar_cdn.setPeak((tar_cdn.getBandwidth_cap()-tar_cdn.getBandwidth()));

                //执行完后归还带宽
                ReturnBandwidth task1 = new ReturnBandwidth();
                task1.setCountDownLatch(countDownLatch);  //
                task1.setCdn(tar_cdn);
                task1.setR_speed(s.getSpeed());
                Timer timer = new Timer();
                timer.schedule(task1, s.getEndtime()-s.getArrivetime());

            }else{
                countDownLatch.countDown();
            }

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            //执行出错
        }
        total_cost = CDN.computeCost(cdn);
        return total_cost;
    }



}

class ReturnBandwidth extends TimerTask{

    private double R_speed;
    private CDN cdn;
    private CountDownLatch countDownLatch;

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public CDN getCdn() {
        return cdn;
    }

    public double getR_speed() {
        return R_speed;
    }

    public void setCdn(CDN cdn) {
        this.cdn = cdn;
    }

    public void setR_speed(double r_speed) {
        R_speed = r_speed;
    }

    public void run(){
        this.cdn.setBandwidth(this.cdn.getBandwidth()+this.R_speed);
        countDownLatch.countDown();

    }

}*/


