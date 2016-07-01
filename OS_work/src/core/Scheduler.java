package core;

import gui.ThreadModel;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.Map;

/**
 * Pcb调度程序，被ClockInterrupt的interrupt函数周期性地调用
 *
 * @author yecq
 */
public class Scheduler {

    private Algorithm algo;

    private PcbQueue queue;

    private ProcessManager manager;
    //调用前台的table
    public Table table;
    
    public Scheduler(Algorithm algo, ProcessManager manager, PcbQueue queue){
        this.algo = algo;
        this.manager=manager;
        this.queue=queue;
        System.out.println("进程调度器启动完成......");
    }

    // 设置调度算法
    void setAlgorithm(Algorithm algo) {
        this.algo = algo;
    }

    void setProcessManager(ProcessManager manager){
        this.manager=manager;
    }

    void setPcbQueue(PcbQueue queue){
        this.queue=queue;
    }

    public void setTable(Table table){this.table=table;}

    // 调度
    public void schedule() {
        Map<Integer,Pcb> running=queue.runningQueue();
        Map<Integer,Pcb> ready=queue.readyQueue();

        // 选出一个运行态进程
        int pid_run = this.algo.getSwitchOut(running);

        //选出一个就绪态进程
        int pid_in = this.algo.getSwitchIn(ready);

       // 然后切换 ...

        switchout(pid_run);

        switchin(pid_in);

       // add_to_gui(queue,table);

    }

    private void switchin(int pid_in){
        if(pid_in==-1){
            System.out.println("就绪队列空!");
        }
        else  {
            //TableItem item=table.getItem(pid_in);

            manager.running(pid_in);
            System.out.println("换入进程"+pid_in+" 名称:"+queue.getPcb(pid_in).getName()+" 剩余时间:"+queue.getPcb(pid_in).getRemainTime());

        }

    }

    private void switchout(int pid_run){
        if(pid_run==-1){
            System.out.println("运行队列空!");
        }
        else{
            //TableItem item=table.getItem(pid_run);

            manager.ready(pid_run);
            queue.getPcb(pid_run).decreaseTime(5);
            if(queue.getPcb(pid_run).getRemainTime()<=0){
                manager.destroyProcess(pid_run);
                //item.dispose();
            }
            else {
                System.out.println("换出进程" + pid_run + " 名称:" + queue.getPcb(pid_run).getName() + " 剩余时间:" + queue.getPcb(pid_run).getRemainTime());

            }
        }

    }


}
