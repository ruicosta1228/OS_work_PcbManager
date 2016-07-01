package core;

/**
 * 进程控制块
 * Created by neo on 16/5/6.
 */
public class Pcb {
    private final int pid;      // 进程id
    private String name;        // 进程名称
    private Status status;      // 进程状态
    private long remainTime;    // 剩余运行时间

    Pcb(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return this.pid;
    }

    void setRemainTime(long time) {
        this.remainTime = time;
    }

    public long getRemainTime() {
        return this.remainTime;
    }

    void decreaseTime(long slice) {
        this.remainTime -= slice;
        if (this.remainTime < 0) {
            this.remainTime = 0;
        }
    }

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    Status getStatus() {
        return this.status;
    }

    void setRunning() {
        this.status = Status.RUNNING;
    }

    void setReady() {
        this.status = Status.READY;
    }

    void setWaiting() {
        this.status = Status.WAITING;
    }
}
