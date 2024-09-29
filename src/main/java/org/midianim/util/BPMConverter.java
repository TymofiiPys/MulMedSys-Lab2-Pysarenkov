package org.midianim.util;

public class BPMConverter {
    public static double convertBPMToMilliSec(double bpm) {
        return 60000 / bpm;
    }
}
