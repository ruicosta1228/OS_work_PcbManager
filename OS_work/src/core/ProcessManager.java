package core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 * Pcb管理类，负责进程的创建、销毁、状态变化等 这是这个包里两个对外开放的public类之一
 *
 * @author yecq
 */
public class ProcessManager {

    private static ProcessManager single = null;

    private PcbPool pool;

    private PcbQueue queue;

    // 单例模式，确保虚拟机里只有这个类的一个对象
    public static ProcessManager getInstance() {
        if (single == null) {
            single = new ProcessManager();
        }
        return single;
    }

    private ProcessManager(){}

    public ProcessManager(PcbPool pool, PcbQueue queue){
        this.pool=pool;
        this.queue=queue;
        System.out.println("进程块管理系统初始化成功......");
    }

    //public PcbQueue getQueue(){
     //   return queue;
    //}
    // 创建进程，成功返回>0的进程号，错误返回负数
    public int createProcess(String name, long time) {
        Pcb process = pool.removeOne();
        if(process==null) {
            return -1;
        }
        process.setName(name);
        process.setRemainTime(time);

        //创建后入就绪队列
        process.setReady();
        queue.add2Ready(process);
        return process.getPid();
    }

    // 撤销进程
    public void destroyProcess(int pid) {
        System.out.println("撤销进程"+pid);
        pool.returnOne(queue.removePcb(pid));
    }

    public void destroyAllProcess() {

        Iterator iter=queue.runningQueue().entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            pool.returnOne(queue.removePcb((Integer) entry.getKey()));
        }
        iter=queue.readyQueue().entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            pool.returnOne(queue.removePcb((Integer) entry.getKey()));
        }
        iter=queue.waitingQueue().entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            pool.returnOne(queue.removePcb((Integer) entry.getKey()));
        }
        System.out.println("撤销所有进程");
    }

    // 设为就绪态
    //运行态和等待态均可以设置为就绪态
    public void ready(int pid) {
        queue.add2Ready(queue.removeFromWaiting(pid));
        queue.add2Ready(queue.removeFromRunning(pid));
        queue.getPcb(pid).setReady();
    }

    // 设为运行态
    //仅就绪态可以设置为运行态 设置成功返回1 失败-1
    //运行队列满时无法设为运行
    public int running(int pid) {
        if(queue.runningQueue().size()==0) {
            if (queue.getPcb(pid).getStatus().equals(Status.READY)) {
                queue.add2Running(queue.removeFromReady(pid));
                queue.getPcb(pid).setRunning();
                return 1;
            } else {
                System.out.println("仅就绪态可以设置为运行态");
                return -1;
            }
        }
        else {
            System.out.println("运行队列满");
            return -1;
        }
    }

    // 设为等待态
    //就绪态,运行态可以设置为等待态
    public void waiting(int pid) {
        queue.add2Waiting(queue.removeFromReady(pid));
        queue.add2Waiting(queue.removeFromRunning(pid));
        queue.getPcb(pid).setWaiting();
    }
}
