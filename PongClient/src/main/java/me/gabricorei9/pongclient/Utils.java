package main.java.me.gabricorei9.pongclient;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Utils {

    public static float SAMPLE_RATE = 8000f;

    public static void tone(int hz, int mSecs, double vol) {
        try {
            byte[] buf = new byte[1];
            AudioFormat af =
                    new AudioFormat(
                            SAMPLE_RATE, // sampleRate
                            8,           // sampleSizeInBits
                            1,           // channels
                            true,        // signed
                            false);      // bigEndian
            SourceDataLine sdl;
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < mSecs * 8; i++) {
                double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0 * vol);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException ignored) {}
    }

    public static void beep(int hz, int mSecs, double vol) {
        new AudioThread(hz, mSecs, vol);
    }

}

class AudioThread extends Thread {

    private final int hz;
    private final int mSecs;
    private final double vol;

    public AudioThread(int hz, int mSecs, double vol) {
        this.hz = hz;
        this.mSecs = mSecs;
        this.vol = vol;
        this.start();
    }

    @Override
    public void run() {
        Utils.tone(hz, mSecs, vol);
    }
}
