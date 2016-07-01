package gui;

import core.Pcb;
import core.ProcessManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Map;


/**
 * Created by neo on 16/5/3.
 */
public class CreateDialog {
    CreateDialog(Shell parent, Table table, ProcessManager manager,Map<Integer, TableItem> map){
        Shell createDialog=new Shell(parent);
        int Width=Constants.getMainWidth()/2;
        int Height=Constants.getMainHeight()/2;
        createDialog.setText("创建进程");
        createDialog.setBounds(Width/4*3,Height/2,Width/24*7,Height/24*7);

        GridLayout layout=new GridLayout();
        layout.numColumns=2;
        createDialog.setLayout(layout);

        GridData labelStyle=new GridData();
        labelStyle.widthHint=Width/8;
        labelStyle.heightHint=25;

        Label name=new Label(createDialog, SWT.LEFT);
        name.setText("创建进程:");
        name.setLayoutData(labelStyle);

        Text txtName=new Text(createDialog,SWT.LEFT|SWT.BORDER);
        txtName.setLayoutData(labelStyle);

        Label time=new Label(createDialog, SWT.LEFT);
        time.setText("运行时间(秒):");
        time.setLayoutData(labelStyle);

        Text txtTime=new Text(createDialog,SWT.LEFT|SWT.BORDER);
        txtTime.setLayoutData(labelStyle);
        txtTime.setText("60");

        Button btnCancel=new Button(createDialog,SWT.CENTER);
        //btnCancel.setLayoutData(labelStyle);
        btnCancel.setText("取消");

        Button btnSubmit=new Button(createDialog,SWT.CENTER);
        //btnSubmit.setLayoutData(labelStyle);
        btnSubmit.setText("确定");

        btnCancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                createDialog.close();
            }
        });

        btnSubmit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int i=manager.createProcess(txtName.getText(),Integer.parseInt(txtTime.getText()));
                TableItem item=new ThreadModel(i,txtName.getText(),"就绪",Integer.parseInt(txtTime.getText())).add_to_table(table);
                item.setForeground(2,Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
                //pcbid 和 tableitem控件作为键值对放入map
                map.put(i,item);
                createDialog.close();
            }
        });

        createDialog.open();
    }
/*
    TableItem item=new TableItem(table,0);
    String[] sample={String.valueOf(i),txtName.getText(),"就绪",txtTime.getText()};
    item.setText(sample);
*/

}
