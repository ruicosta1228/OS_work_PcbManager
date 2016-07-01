package gui;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Created by neo on 16/5/4.
 */
public class ThreadModel {
    private String threadName;
    private String threadStatus;
    private long restTime;
    private int threadNumber;

    ThreadModel(){}

    public ThreadModel(int number, String name, String status, long time){
        this.threadNumber=number;
        this.threadName=name;
        this.threadStatus=status;
        this.restTime=time;
    }


    public TableItem add_to_table(Table table){
        TableItem item=new TableItem(table,0);
        String[] sample={String.valueOf(threadNumber),threadName,threadStatus,Long.toString(restTime)};
        item.setText(sample);

        return item;
    }
}
