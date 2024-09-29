package org.midianim;

import org.midianim.GUI.MainWindow;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JFrame frame = new JFrame("ЛР№2. Анімація по MIDI");
        MainWindow mw = new MainWindow(frame);
        frame.setContentPane(mw.getMainPanel());
        frame.setMinimumSize(mw.mainWindowDims);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
