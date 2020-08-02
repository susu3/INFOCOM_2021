# INFOCOM_2021

**数据处理：**

1）user.xls中删除了跟CDN相关的列，coderate为0的行、start_time>=end_time的行，现在只剩下五个字段，分别为：[ stream（ID,但代码中并未使用这个ID）、ip_province（对应于我们论文中的label）、start_time、end_time、coderate（对应于我们论文中的speed）---*在原表上修改是真的费时间，重新生成个新表快很多*

2）统计了每个CDN节点的峰值带宽，我们对每个CDN设置带宽时为该值的1.2倍；每个省内有多个CDN节点，有的省只有一个节点上有数据，有的省有多个上有数据



**实验：**

1）baseline:

Random, greedy, Gale-Shapley algorithm(check)

2）变化的变量：

CDN数量、request密度、带宽容量、

