package org.midianim.util;

import lombok.RequiredArgsConstructor;

import javax.sound.midi.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.IOException;

@RequiredArgsConstructor
public class MidiReader {
    private final String filepath;

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

    public void read() {
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
            sequencer.setTempoInBPM(113);
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(1000);
            }
            sequencer.close();

            //            for (Track track : sequence.getTracks()) {
//                System.out.println("Track length: " + track.size());
//
//                // Iterate through the events in each track
//                for (int i = 0; i < track.size(); i++) {
//                    MidiEvent event = track.get(i);
//                    MidiMessage message = event.getMessage();
//                    // Only log note events (ShortMessage)
//                    if (message instanceof ShortMessage) {
//                        ShortMessage sm = (ShortMessage) message;
//                        int command = sm.getCommand();
//
//                        // Check if the command is NOTE_ON or NOTE_OFF
//                        if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
//                            int key = sm.getData1(); // The note (key)
//                            int velocity = sm.getData2(); // The velocity
//
//                            // Log the note and velocity
//                            System.out.println("Note: " + key + " Velocity: " + velocity +
//                                    " Time: " + event.getTick() +
//                                    (command == ShortMessage.NOTE_ON ? " (Note On)" : " (Note Off)"));
//                        }
//                    }
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MidiReader m = new MidiReader("C:\\Users\\Tymofii\\Documents\\Image-Line\\FL Studio\\Presets\\Scores\\ets_guitar_1.mid");
        m.read();
    }
}
