package org.midianim.GUI;

import lombok.Getter;
import org.midianim.util.MidiReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainWindow {
    public final Dimension mainWindowDims = new Dimension(600, 500);

    private JButton launchButton;
    @Getter
    private JPanel mainPanel;
    private JLabel bpmLabel;
    private JTextField bpmTextField;
    private JLabel pathLabel;
    private JTextField pathTextField;
    private JButton chooseFileButton;
    private final JFrame frame;
    private String midiFileName;
    private boolean openFile(){
        JFileChooser openFileDialog = new JFileChooser();
        openFileDialog.setDialogTitle("Відкрити файл...");
        openFileDialog.setDialogType(JFileChooser.OPEN_DIALOG);
        openFileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
        openFileDialog.setFileFilter(MidiReader.getMidiFileFilter());
        int result = openFileDialog.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            midiFileName = openFileDialog.getSelectedFile().getAbsolutePath();
            return true;
        }
        return false;
    }

    public MainWindow(JFrame frame) {
        this.frame = frame;

        chooseFileButton.addActionListener(e -> {
            if(openFile()) {
                pathTextField.setText(midiFileName);
            }
        });
    }
}
