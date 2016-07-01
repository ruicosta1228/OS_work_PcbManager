package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * Created by neo on 16/5/1.
 */
public class AuthorInfo {

    AuthorInfo(Shell parent) {
        Shell author = new Shell(parent);
        int Width=Constants.getMainWidth()/2;
        int Height=Constants.getMainHeight()/2;
        author.setText("~~~");
        author.setBounds(Width/4*3,Height/2,Width/2,Height/2);
        Label authotInfo=new Label(author, SWT.CENTER);
        authotInfo.setText("\n\n\n\n\n2014020100048-孙一飞");
        authotInfo.setBounds(author.getClientArea());

        author.open();
    }
}
