package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import java.awt.*;

/**
 * Created by neo on 16/4/30.
 */
public class Constants {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int screenWidth = screenSize.width;
    private final static int screenHeight = screenSize.height;
    //mainframe constants
    public static int getMainWidth(){return screenWidth;}
    public static int getMainHeight(){return screenHeight;}

}
