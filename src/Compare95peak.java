import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.util.*;

public class Compare95peak {

    public double comparePeak(LinkedList<Request> request, ArrayList<CDN> cdn){
        double total_cost =0;
        int cdn_num = cdn.size();
        int req_num = request.size();
        int min_time = Integer.MAX_VALUE, max_time = Integer.MIN_VALUE;
        int t;

        //找到t的取值范围
        for(int j=0;j<req_num;j++){
            Request req = request.get(j);
            t=req.getArrivetime();
            if(t<min_time)
                min_time=t;
            if(t>max_time)
                max_time=t;
            t=req.getEndtime();
            if(t<min_time)
                min_time=t;
            if(t>max_time)
                max_time=t;
        }

        try{
            IloCplex cplex = new IloCplex();

            //设置B_i的上下界，上界为bandwidth capacity,下界为0
            double[] lb = new double[cdn_num];
            double[] ub = new double[cdn_num];
            for(int i=0;i<cdn_num;i++){
                lb[i]=0;
                ub[i]=cdn.get(i).getBandwidth_cap();
            }
            IloNumVar[] B_i = cplex.numVarArray(cdn_num,lb,ub);
            //设置x_{i,j}上下界，下界为0，上界为1
            int[] lb_x = new int[cdn_num*req_num];
            int[] ub_x = new int[cdn_num*req_num];
            for(int j=0;j<req_num;j++){
                for(int i=0;i<cdn_num;i++){
                    lb_x[cdn_num*j+i]=0;
                    ub_x[cdn_num*j+i]=1;
                }
            }
            IloNumVar[] x_ij = cplex.intVarArray(req_num*cdn_num, lb_x,ub_x);  //这个上下界是否包含这个值？

            //min sum{B_i * c_i}
            double[] cost = new double[cdn_num];
            for (int i = 0; i < cdn_num; i++){
                cost[i]=cdn.get(i).getBcost();
            }
            cplex.addMinimize(cplex.scalProd(cost, B_i));

            //约束 sum x_ij =1
            for(int j=0;j<req_num;j++){
                IloNumExpr objExpr = cplex.numExpr();  //numExpr? IloIntExpr？
                for(int i =0;i<cdn_num;i++){
                    objExpr = cplex.sum(objExpr,x_ij[cdn_num*j+i]);
                }
                cplex.addEq(objExpr,1);
            }
            //带宽容量约束
            int time;
            for(int i =0; i<cdn_num;i++){
                for(t=min_time; t<=max_time;t++){
                    IloNumExpr objExpr = cplex.numExpr();  //numExpr? IloIntExpr？
                    for(int j=0;j<req_num;j++){
                        time=request.get(j).getArrivetime();
                        if(t==time){
                            objExpr = cplex.sum(objExpr,cplex.prod(x_ij[cdn_num*j+i],request.get(j).getSpeed()));
                        }
                        time=request.get(j).getEndtime();
                        if(t==time){
                            objExpr = cplex.sum(objExpr,cplex.prod(x_ij[cdn_num*j+i],request.get(j).getSpeed()));
                        }
                    }
                    cplex.addLe(objExpr,B_i[i]);
                }
            }

            //solve
            cplex.solve();
            total_cost = cplex.getObjValue();  //得到优化目标的值
            cplex.end();

        }catch (IloException e){
            e.printStackTrace();
        }

        return total_cost;
    }

    public double compare95(LinkedList<Request> request, ArrayList<CDN> cdn) {

        return 0;

    }
}
