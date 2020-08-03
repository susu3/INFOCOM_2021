# INFOCOM_2021

**数据：**

1）user.xls中删除了跟CDN相关的列，coderate为0的行、start_time>=end_time的行，现在只剩下五个字段，分别为：[ stream（ID,但代码中并未使用这个ID）、ip_province（对应于我们论文中的label）、start_time、end_time、coderate（对应于我们论文中的speed）---*在原表上修改是真的费时间，重新生成个新表快很多*

2）统计了每个CDN节点的峰值带宽，我们对每个CDN设置带宽时为该值的1.2倍；每个省内有多个CDN节点，有的省只有一个节点上有数据，有的省有多个上有数据

3）原始数据中，开始时间>结束时间的有8万条左右、开始时间小于结束时间的有30万条左右，开始时间=结束时间的有52万条左右



**实验：**

1）baseline:

Random, greedy, Gale-Shapley algorithm(check)

2）变化的变量：

CDN数量、request密度、带宽容量、



**代码：**

1）request用LinkedList<Request>存储

​      CDN用HashMap<String,ArrayList<CDN>>，string对应于我们的label

2）带宽计费用下表来记

![image-20200803170903165](/Users/mengjiaying/Library/Application Support/typora-user-images/image-20200803170903165.png)

