package core;

import java.util.Map;

/**
 * 调度算法接口
 *
 * @author yecq
 */
interface Algorithm {

    // 选出换出的进程号，未选到返回-1
    int getSwitchOut(Map<Integer,Pcb> queue);

    // 选出换进的进程号，未选到返回-1
    int getSwitchIn(Map<Integer,Pcb> queue);
}
