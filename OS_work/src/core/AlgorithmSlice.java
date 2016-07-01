package core;

import java.util.Iterator;
import java.util.Map;

/**
 * 时间片轮转算法
 *
 * @author yecq
 */
public class AlgorithmSlice implements Algorithm {

    @Override
    public String toString() {
        return "时间片轮转调度算法";
    }

    @Override
    public int getSwitchOut(Map<Integer, Pcb> queue) {
        Iterator iter=queue.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            return (int)entry.getKey();
        }
        return -1;
    }

    @Override
    public int getSwitchIn(Map<Integer, Pcb> queue) {
        /*
        Iterator iter=queue.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            return (int)entry.getKey();
        }
        return -1;*/
        int pri=100;
        if(queue.size()==0){return -1;}
        else {
            for (Map.Entry<Integer, Pcb> entry : queue.entrySet()) {
                if (entry.getKey() <= pri) {
                    pri = entry.getKey();
                }
            }
            return pri;
        }
    }
}
