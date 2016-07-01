package core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 各个状态进程的队列
 *
 * @author yecq
 */
public class PcbQueue {

    private final Map<Integer, Pcb> running;    // 运行队列
    private final Map<Integer, Pcb> ready;      // 就绪队列
    private final Map<Integer, Pcb> waiting;    // 等待队列

    public PcbQueue() {
        this.running = new ConcurrentHashMap();
        this.ready = new ConcurrentHashMap();
        this.waiting = new ConcurrentHashMap();
        System.out.println("进程队列初始化成功.......");
    }

    public Map<Integer,Pcb> runningQueue(){
        return this.running;
    }

    public Map<Integer,Pcb> readyQueue(){
        return this.ready;
    }

    public Map<Integer,Pcb> waitingQueue(){ return this.waiting; }

    // 加入一个进程到运行队列
    void add2Running(Pcb process) {
        if(process!=null) {
            running.put(process.getPid(), process);
        }else{
            return;
        }
    }

    // 从运行队列中取出一个Process
    Pcb removeFromRunning(int pid) {
        if(running.get(pid)==null){
            return null;
        }
        else {
            return this.running.remove(pid);
        }
    }

    // 加入一个Process到就绪队列
    void add2Ready(Pcb process) {
        if(process!=null) {
            ready.put(process.getPid(), process);
        }else{
            return;
        }
    }

    // 从就绪队列中取出一个
    Pcb removeFromReady(int pid) {
        if(ready.get(pid)==null){
            return null;
        }else {
            return this.ready.remove(pid);
        }
    }

    // 加入到等待队列
    void add2Waiting(Pcb process) {
        if(process!=null) {
            waiting.put(process.getPid(), process);
        }else{
            return;
        }
    }

    // 从等待队列中取出一个
    Pcb removeFromWaiting(int pid) {
        if(waiting.get(pid)==null) {
            return null;
        }else {
            return waiting.remove(pid);
        }
    }

    // 通过pid获得这个进程
    public Pcb getPcb(int pid) {
        if(running.get(pid)!=null) {
            return running.get(pid);
        }
        else if (ready.get(pid)!=null){
            return ready.get(pid);
        }
        else{
            return waiting.get(pid);
        }
    }

    // 根据pid，取得这个pcb
    Pcb removePcb(int pid) {
        if(running.get(pid)!=null) {
            return running.remove(pid);
        }
        else if (ready.get(pid)!=null){
            return ready.remove(pid);
        }
        else{
            return waiting.remove(pid);
        }
    }

  /*  public static void main(String args[]){
        PcbQueue queue=new PcbQueue();
        Pcb p1=new Pcb(1);
        p1.setName("abc");

        queue.add2Running(p1);

        queue.add2Ready(queue.removeFromRunning(1));
    }*/

}
