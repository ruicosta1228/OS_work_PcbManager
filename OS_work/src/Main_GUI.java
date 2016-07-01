import core.CoreInit;
import core.ProcessManager;
import core.Scheduler;
import gui.MainFrame;
import org.eclipse.swt.widgets.Table;


/**
 * Created by neo on 16/4/30.
 */
public class Main_GUI {
    public static void main(String[] args){
        /*CoreInit init=new CoreInit();
        ProcessManager manager=init.getManager();
        Table table=new MainFrame(manager).getThreadTable();
        init.getSched().setTable(table);


        CoreInit init=new CoreInit();
        ProcessManager manager=init.getManager();
        Table table=new MainFrame(manager).getThreadTable();
        Scheduler scheduler=init.getSched();
        scheduler.setTable(table);*/

        new MainFrame();
    }
}
