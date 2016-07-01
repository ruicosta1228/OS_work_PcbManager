package core;

/**
 * Created by neo on 16/5/18.
 */
public class CoreInit {
    //public static void main(String[] args){


        PcbPool pool=new PcbPool();
        PcbQueue queue=new PcbQueue();

        AlgorithmSlice alor=new AlgorithmSlice();
        ProcessManager manager=new ProcessManager(pool, queue);
        Scheduler sched=new Scheduler(alor,manager,queue);
        ClockInterrupt clock=new ClockInterrupt(sched);

        /*
        sched.setProcessManager(manager);
        sched.setPcbQueue(queue);
        sched.setAlgorithm(alor);

        clock.setScheduler(sched);

        sched.setAlgorithm(alor);*/
/*
        manager.createProcess("alpha",10);
        manager.createProcess("beta",15);
        manager.createProcess("omega",15);
*/
    //}
    public ProcessManager getManager(){
        return manager;
    }

    public Scheduler getSched(){return sched;}
}
