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
    @Getter
    private JPanel drawPanel;
    @Getter
    private JLabel notePropLabel;
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

        launchButton.addActionListener(e -> {
            if(!pathTextField.getText().isBlank()) {
                if(!pathTextField.getText().endsWith(".mid"))
                {
                    JOptionPane.showMessageDialog(frame, "Файл має містити розширення .mid!", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
                midiFileName = pathTextField.getText();
            }
            else {
                JOptionPane.showMessageDialog(frame, "Введіть назву файлу", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
            if(bpmTextField.getText().isBlank())
            {
               JOptionPane.showMessageDialog(frame, "Не введено BPM. Використовується BPM за замовчуванням (120)","Не введено BPM", JOptionPane.INFORMATION_MESSAGE);
               bpmTextField.setText("120");
            }
            try {
                double bpm = Double.parseDouble(bpmTextField.getText());
                if(bpm <= 0) throw new NumberFormatException();

                MidiVisualizer mv = new MidiVisualizer(this);

                MidiReader reader = new MidiReader(mv);
                reader.start(midiFileName, bpm);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "BPM має бути додатним дійсним числом", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
