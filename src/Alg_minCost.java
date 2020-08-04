import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Alg_minCost {

    public double algMinCost(LinkedList<Request> request,HashMap<String,ArrayList<CDN>> cdn){
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
            tar_s = request.get(ps.getIndex());  //在初始request列表中的request,为了查到对应的CDN节点来归还带宽

            if(ps.getLabel().equals("arrivetime")){   //分带宽
                String label_s =s.getLabel();
                if(cdn.containsKey(label_s)) {
                    ArrayList<CDN> cdns = cdn.get(label_s);
                    int size = cdns.size();
                    tar_cdn = mincostCDN(tar_s,cdns,cdn);  //选mincost CDN
                    request.get(ps.getIndex()).setCdnIndex(target);
                    tar_cdn.setBandwidth(tar_cdn.getBandwidth()-s.getSpeed());
                    if (tar_cdn.getPeak() < (tar_cdn.getBandwidth_cap() - tar_cdn.getBandwidth()))  //更新peak
                        tar_cdn.setPeak((tar_cdn.getBandwidth_cap() - tar_cdn.getBandwidth()));
                }else
                    continue;

            }else if(ps.getLabel().equals("endtime")){   //释放带宽
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


    //input：即将被分派的request,当前可选的subnet，所有CDN列表（为了计算cost,这个计算cost函数可以修改为计算一个subnet的cost）
    private CDN mincostCDN(Request request, ArrayList<CDN> cdns, HashMap<String,ArrayList<CDN>> cdn){
        CDN tar_cdn;
        double min_cost = Double.MAX_VALUE;
        double cost=0;
        int tar_index = -1;
        double peak=0;

        for(int i=0;i<cdns.size();i++){    //遍历所有候选CDN
            tar_cdn=cdns.get(i);
            if(tar_cdn.getBandwidth()>request.getSpeed()){
                if(tar_cdn.getPeak()<(tar_cdn.getBandwidth_cap()-(tar_cdn.getBandwidth()-request.getSpeed()))) {
                    peak = tar_cdn.getPeak();
                    tar_cdn.setPeak(tar_cdn.getBandwidth_cap() - (tar_cdn.getBandwidth() - request.getSpeed()));
                }
                cost = CDN.computeCost(cdn);
                if(min_cost>cost){
                    min_cost=cost;
                    tar_index = i;
                }
                tar_cdn.setPeak(peak);
            } else
                continue;
        }

        if(tar_index == -1){
            System.out.print("mincostCDN bug!");
            System.exit(1);
        }

        return cdns.get(tar_index);
    }

}
