package org.midianim.GUI;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public class MidiVisualizer {
    private final MainWindow mw;
    public void onNoteRead(int note, int velocity) {
        SwingUtilities.invokeLater(() -> {
            JPanel drawPanel = mw.getDrawPanel();
            Graphics2D gr = (Graphics2D) drawPanel.getGraphics();
            gr.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
            gr.drawOval(50 - velocity / 2, 50 - velocity / 2, velocity, velocity);
            gr.setColor(Color.getHSBColor((float) note / 88, 1, 1));
            gr.drawRect(100, 100, 100, 100);
        });
    }
}
