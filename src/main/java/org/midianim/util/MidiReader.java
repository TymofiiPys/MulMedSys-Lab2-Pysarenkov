package org.midianim.util;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.midianim.GUI.MainWindow;
import org.midianim.GUI.MidiVisualizer;

import javax.sound.midi.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.IOException;

@RequiredArgsConstructor
public class MidiReader {
    private String filepath;
    private final MidiVisualizer mv;
    private double bpm;
    public static FileFilter getMidiFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".mid") | pathname.isDirectory();
            }

            @Override
            public String getDescription() {
                return "(*.mid) MIDI-файл";
            }
        };
    }

    public void start(String filepath, double bpm) {
        SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                read(filepath, bpm);
                return null;
            }
        };

        worker.execute();
    }

    private void read(String filepath, double bpm) {
        File midiFile = new File(filepath);
        if (!midiFile.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        try {
            Sequence sequence = MidiSystem.getSequence(midiFile);

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(sequence);

            sequencer.open();

            long tempoMSec = Math.round(BPMConverter.convertBPMToMilliSec(bpm));

            for (Track track : sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();

                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;

                        if (sm.getCommand() == ShortMessage.NOTE_ON) {
                            int note = sm.getData1();
                            int velocity = sm.getData2();

                            mv.onNoteRead(note, velocity);

                            Thread.sleep(tempoMSec);
                        }
                    }
                }
            }

            sequencer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
