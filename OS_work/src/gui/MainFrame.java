package gui;

import core.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by neo on 16/4/30.
 */
public class MainFrame {
    Display display;
    Shell main;
    Menu menuBar;
    Table threadTable;
    Text result;
    Map<Integer, TableItem> map;


    static PcbPool pool = new PcbPool();
    static PcbQueue queue = new PcbQueue();
    AlgorithmSlice alor = new AlgorithmSlice();
    static ProcessManager manager = new ProcessManager(pool, queue);
    Scheduler sched = new Scheduler(alor, manager, queue);
    ClockInterrupt clock = new ClockInterrupt(sched);



    public MainFrame(){
        //manager.getQueue();

        map=new HashMap<>();
        display=Display.getDefault();
        main=new Shell();
        init(manager);
        main.open();
        while(!main.isDisposed()){
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        //display.dispose();
        System.exit(0);

    }

    private void init(final ProcessManager manager) {
        int MainWidth= Constants.getMainWidth()/2;
        int MainHeight=Constants.getMainHeight()/2;
        main.setText("操作系统实验");
        main.setBounds(MainWidth/2,MainHeight/4,MainWidth,MainHeight/3*4);


        //menuBar
        menuBar = new Menu(main, SWT.BAR);
        MenuItem info = new MenuItem(menuBar, SWT.CASCADE);
        info.setText("信息");
        Menu infomenu = new Menu(main, SWT.DROP_DOWN);
        info.setMenu(infomenu);
        MenuItem authoritem = new MenuItem(infomenu, SWT.PUSH);
        authoritem.setText("author");
        authoritem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new AuthorInfo(main);
            }
        });

        MenuItem help=new MenuItem(menuBar,SWT.CASCADE);
        help.setText("帮助");
        Menu helpmenu=new Menu(main,SWT.DROP_DOWN);
        help.setMenu(helpmenu);
        MenuItem helpitem=new MenuItem(helpmenu,SWT.PUSH);
        helpitem.setText("help");
        main.setMenuBar(menuBar);

        //main
        GridLayout gridLayout=new GridLayout();
        gridLayout.numColumns=2;
        main.setLayout(gridLayout);

        //left
        final Composite left=new Composite(main,SWT.NONE);
        GridData leftStyle=new GridData();
        leftStyle.heightHint=MainHeight;
        GridLayout leftLayout=new GridLayout();
        leftLayout.numColumns=1;
        left.setLayout(leftLayout);
        left.setLayoutData(leftStyle);

        Button btnCreate=new Button(left,SWT.PUSH);
        btnCreate.setText("创建线程");
        btnCreate.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        Button btnDeleteAll=new Button(left,SWT.PUSH);
        btnDeleteAll.setText("结束所有");
        btnDeleteAll.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        //right
        final Composite right=new Composite(main,SWT.NONE);
        GridLayout rightLayout=new GridLayout();
        rightLayout.numColumns=1;
        right.setLayout(rightLayout);

        //Table
        threadTable=new Table(right,SWT.IGNORE);
        threadTable.setLinesVisible(true);
        threadTable.setHeaderVisible(true);
        TableColumn column1 = new TableColumn(threadTable, SWT.CASCADE);
        column1.setText("No");
        column1.setAlignment(SWT.LEFT);
        column1.setWidth(50);

        TableColumn column2 = new TableColumn(threadTable, SWT.CASCADE);
        column2.setText("Name");
        column2.setAlignment(SWT.LEFT);
        column2.setWidth(200);

        TableColumn column3 = new TableColumn(threadTable, SWT.CASCADE);
        column3.setText("Status");
        column3.setAlignment(SWT.LEFT);
        column3.setWidth(190);

        TableColumn column4 = new TableColumn(threadTable, SWT.CASCADE);
        column4.setText("Rest time");
        column4.setAlignment(SWT.LEFT);
        column4.setWidth(175);

        GridData table=new GridData();
        table.widthHint=MainWidth/6*5;
        table.heightHint=MainHeight/4*3;
        threadTable.setLayoutData(table);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!display.isDisposed()){
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            Iterator<Pcb> iterator=queue.runningQueue().values().iterator();
                            TableItem newItem;
                            if(iterator.hasNext()){
                                Pcb pcb=iterator.next();
                                newItem=map.get(pcb.getPid());
                                newItem.setText(2,"运行");
                                newItem.setForeground(2,Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
                                newItem.setText(3, String.valueOf(pcb.getRemainTime()));
                            }

                            iterator=queue.readyQueue().values().iterator();
                            if (iterator.hasNext()){
                                Pcb pcb=iterator.next();
                                newItem=map.get(pcb.getPid());
                                newItem.setText(2,"就绪");
                                newItem.setForeground(2,Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
                                newItem.setText(3, String.valueOf(pcb.getRemainTime()));
                            }

                            Iterator<TableItem> tableitem_iterator=map.values().iterator();
                            if(tableitem_iterator.hasNext()){
                                newItem=tableitem_iterator.next();
                                if(queue.getPcb(Integer.parseInt(newItem.getText(0)))==null&&!newItem.isDisposed()){
                                    threadTable.remove(threadTable.indexOf(newItem));
                                    //不remove这个迭代器会报错
                                    tableitem_iterator.remove();
                                }
                            }
                        }
                    });
                }
            }
        }).start();
/*
        //sample
        ThreadModel model1=new ThreadModel(1," thread1"," 运行",60);
        model1.add_to_table(threadTable);
        ThreadModel model2=new ThreadModel(2," thread2"," 就绪",40);
        model2.add_to_table(threadTable);
        ThreadModel model3=new ThreadModel(3," thread3"," 等待",30);
        model3.add_to_table(threadTable);
*/

        threadTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                if(mouseEvent.button==3){
                    TableItem item = threadTable.getItem(threadTable.getSelectionIndex());
                    int index= Integer.parseInt(item.getText(0));
                    //int row=threadTable.indexOf(item);
                    if(item == null){
                        return;
                    }

                    Menu menu = new Menu(threadTable);
                    threadTable.setMenu(menu);
                    MenuItem run = new MenuItem(menu, SWT.PUSH);
                    run.setText("设置为运行");
                    MenuItem ready = new MenuItem(menu, SWT.PUSH);
                    ready.setText("设置为就绪");
                    MenuItem wait = new MenuItem(menu, SWT.PUSH);
                    wait.setText("设置为等待");
                    MenuItem delete = new MenuItem(menu, SWT.PUSH);
                    delete.setText("终止");


                    run.addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            if(manager.running(Integer.parseInt(item.getText(0)))==1) {
                                item.setText(2, "运行");
                                item.setForeground(2, Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
                            }else{
                                return;
                            }
                            //manager.running(Integer.parseInt(item.getText(0)));
                        }
                    });
                    ready.addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            item.setText(2,"就绪");
                            item.setForeground(2,Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
                            manager.ready(Integer.parseInt(item.getText(0)));
                        }
                    });
                    wait.addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            item.setText(2,"等待");
                            item.setForeground(2,Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
                            manager.waiting(Integer.parseInt(item.getText()));
                        }
                    });
                    delete.addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            //防止select的对象是空指针
                            if(map.get(index)==null){
                                return;
                            }

                            manager.destroyProcess(index);
                            threadTable.remove(threadTable.indexOf(item));
                            map.remove(index);
                            //item.dispose();

                        }
                    });
                }
            }
        });

        //Text
        result=new Text(right,SWT.NONE);
        result.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
        GridData resultText=new GridData();
        resultText.widthHint=MainWidth/6*5;
        resultText.heightHint=MainHeight/3;
        result.setLayoutData(resultText);


        //main.open();
        btnCreate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new CreateDialog(main,threadTable,manager,map);
            }
        });
        btnDeleteAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {

                manager.destroyAllProcess();
                map.clear();
                threadTable.removeAll();

                /*
                int style=SWT.ICON_WARNING|SWT.YES|SWT.NO;
                MessageBox messageBox=new MessageBox(main,style);
                messageBox.setText("?");
                messageBox.setMessage("是否结束所有进程?");
                int rc=messageBox.open();
                if(selectionEvent.doit == (rc == SWT.OK)){
                    manager.destroyAllProcess();
                    map.clear();
                    threadTable.removeAll();
                }*/
            }
        });
        main.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                int style = SWT.APPLICATION_MODAL | SWT.YES ;
                MessageBox messageBox = new MessageBox(main, style);
                messageBox.setText("退出");
                messageBox.setMessage("真的要退出吗?");
                int rc=messageBox.open();
                //event.doit = messageBox.open() == SWT.YES;
                if (event.doit == (rc == SWT.OK)) {
                    ////做窗口关闭事件
                    main.dispose();
                }else if(event.doit == (rc == SWT.CANCEL)) {
                    ////做最小化事件

                }
            }
        });
    }

}
