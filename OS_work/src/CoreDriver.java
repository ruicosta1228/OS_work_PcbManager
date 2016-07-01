import core.CoreInit;
import core.ProcessManager;

/**
 * Created by neo on 16/5/27.
 */
public class CoreDriver {
    public static void main(String[] args){
        ProcessManager manager=new CoreInit().getManager();
        manager.createProcess("alpha",10);
        //manager.createProcess("beta",15);
        //manager.createProcess("omega",15);

        manager.waiting(1);

        manager.ready(1);

        manager.createProcess("beta",10);

        manager.destroyAllProcess();
    }
}
