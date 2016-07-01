package core;

import java.util.Map;

/**
 * 静态优先级调度算法
 *
 * @author yecq
 */
class AlgorithmPriority implements Algorithm {



    @Override
    public String toString() {
        return "优先级调度算法";
    }

    @Override
    public int getSwitchOut(Map<Integer, Pcb> queue) {
        return 0;
    }

    @Override
    public int getSwitchIn(Map<Integer, Pcb> queue) {
        return 0;
    }
}
