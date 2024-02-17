import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class test {
    //节点列表
    Node[] nodes;

    //初始化节点，创建堆
    // 因为开始时各节点连接数都为0，所以直接填充数组即可
    test(String ns){
        String[] ns1 = ns.split(",");
        nodes = new Node[ns1.length+1];
        for (int i = 0; i < ns1.length; i++) {
            nodes[i+1] = new Node(ns1[i]);
        }
    }

    //节点下沉，与左右子节点比对，选里面最小的交换
    //目的是始终保持最小堆的顶点元素值最小
    //i:要下沉的顶点序号
    void down(int i) {
        //顶点序号遍历，只要到1半即可，时间复杂度为O(log2n)
        while ( i << 1  <  nodes.length){
            //左子，为何左移1位？回顾一下二叉树序号
            int left = i<<1;
            //右子，左+1即可
            int right = left+1;
            //标记，指向 本节点，左、右子节点里最小的，一开始取i自己
            int flag = i;
            //判断左子是否小于本节点
            if (nodes[left].get() < nodes[i].get()){
                flag = left;
            }
            //判断右子
            if (right < nodes.length && nodes[flag].get() > nodes[right].get()){
                flag = right;
            }
            //两者中最小的与本节点不相等，则交换
            if (flag != i){
                Node temp = nodes[i];
                nodes[i] = nodes[flag];
                nodes[flag] = temp;
                i = flag;
            }else {
                //否则相等，堆排序完成，退出循环即可
                break;
            }

        }

    }

    //请求。非常简单，直接取最小堆的堆顶元素就是连接数最少的机器
    void request(){
        System.out.println("---------------------");
        //取堆顶元素响应请求
        Node node = nodes[1];
        System.out.println(node.name + " accept");
        //连接数加1
        node.inc();
        //排序前的堆
        System.out.println("before:"+ Arrays.toString(nodes));
        //堆顶下沉
        down(1);
        //排序后的堆
        System.out.println("after:"+Arrays.toString(nodes));
    }

    public static void main(String[] args) {
        //假设有7台机器
        test lc = new test("a,b,c,d,e,f,g");
        //模拟10个请求连接
        for (int i = 0; i < 10; i++) {
            lc.request();
        }
    }

    class Node{
        //节点标识
        String name;
        //计数器
        AtomicInteger count = new AtomicInteger(0);
        public Node(String name){
            this.name = name;
        }
        //计数器增加
        public void inc(){
            count.getAndIncrement();
        }
        //获取连接数
        public int get(){
            return count.get();
        }

        @Override
        public String toString() {
            return name+"="+count;
        }
    }
}
