import java.util.HashMap;
import java.util.LinkedList;


public class PriorityQueue {
    //队列中数据项的值有序
    //队列中数据项的值从队列头到队列尾越来越大
    private LinkedList<PriorityStruct> request;

    public PriorityQueue(){
        this.request = new LinkedList<PriorityStruct>();
    }

    public void push(Request toInsert, int index){
        int time = toInsert.getArrivetime();
        int i = 0;

        PriorityStruct s= new PriorityStruct();
        s.setRequest(toInsert);
        s.setTime(time);
        s.setLabel("arrivetime");
        s.setIndex(index);
        if(this.request.size() == 0){
            this.request.add(s);
        }
        else{
            for(i=0;i<this.request.size();i++){
                int cur_key=this.request.get(i).getTime();
                if(time < cur_key){
                    this.request.add(i,s);
                    break;
                }
            }
        }

        time = toInsert.getEndtime();
        PriorityStruct s2= new PriorityStruct();
        s2.setRequest(toInsert);
        s2.setTime(time);
        s2.setLabel("endtime");
        s2.setIndex(index);
        for(;i<this.request.size();i++) {
            int cur_key = this.request.get(i).getTime();
            if (time < cur_key) {
                this.request.add(i, s2);
                break;
            }
        }
    }

    //删除值返回
    //poll:查询并移除第一个元素
    public PriorityStruct pop(){
//        if(this.request.size() ==0)
//            throw new Exception("Queue is empty");
        return this.request.poll();
    }

    //peek:获取第一个元素但不移除
    public PriorityStruct peek() throws Exception{
        if(this.request.size() ==0)
            throw new Exception("Queue is empty");
        return this.request.peek();
    }

    public int size(){
        return this.request.size();
    }

    public boolean isEmpty(){
        return (this.request.size()==0);
    }

}

class PriorityStruct{
    private int time;
    private Request s;
    private String label;
    private int index;  //在request初始list中的index

    public void setRequest(Request s) {
        this.s = s;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTime() {
        return time;
    }

    public Request getRequest() {
        return s;
    }

    public String getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }
}
