package core;

import java.util.*;

/**
 * 进程控制块池，系统启动初期建立一定数量的Pcb，以供使用
 *
 * @author yecq
 */

public class PcbPool {

    static final int CAPACITY = 100;
    //static int head=1;
    private Map<Integer, Pcb> map;  // 存放预先建立的pcb

    public PcbPool()
    {
        map=new HashMap();
        for(int i=1;i<=100;i++){
            map.put(i,new Pcb(i));
        }
        System.out.println("进程池初始化成功.......");
    }

    // 从表头取出一个Pcb
    synchronized Pcb removeOne() {
        int head=100;
        for (Map.Entry<Integer, Pcb> entry : map.entrySet()) {
            if (entry.getKey() <= head) {
                head = entry.getKey();
            }
        }
        return map.remove(head);
    }

    // 归还一个
    synchronized void returnOne(Pcb pcb) {
        if(pcb!=null) {
            map.put(pcb.getPid(), pcb);
        }
        else return;
    }
}
