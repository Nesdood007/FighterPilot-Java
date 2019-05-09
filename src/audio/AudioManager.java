package audio;

import java.io.File;
import java.util.ArrayList;
//import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
//import javax.sound.sampled.LineUnavailableException;

/**This class will hold all audio components. This is essentially an all-in-one class to take care of the audio needs of the game.
 * 
 * @author Brady
 *
 */
public class AudioManager implements Runnable {

    private static ArrayList<SoundWrapper> sounds = new ArrayList<SoundWrapper>();

    /**This method loads the Sound from the given filename
     * 
     * @param fname File Name to load background music from
     * @param loop Plays on continuous loop until stopped if true
     * @return Sound object to control it with.
     */
    public static audio.Sound loadSound(String fname, boolean loop) {
        System.out.println("Loading Music: " + fname);
        SoundWrapper sw = null;
        try {
            sw = new SoundWrapper(fname, loop, AudioSystem.getClip());
            File file = new File(fname);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audio.getFormat();
            System.out.println("Format: " + format);
            sw.clip.open(audio);
            sounds.add(sw);
        } catch (Exception e) {
            System.err.println("Something went Wrong:\n" + e);
        }
        return sw;
    }

    public void fadeAllOut() {
        while (true) {
            for (int i = 0; i < sounds.size();i++) {
                sounds.get(i).fadeOut(1.0);
            }
        }
    }

    /**Starts the Audio Thread. Mainly used for fading out audio.
     * 
     */
    public void run() {
        while (true) {
            System.out.print("");
            for (int i = 0; i < sounds.size();i++) {
                sounds.get(i).run();
            }
        }

    }

    /**This is the way that the AudioManager keeps track of all of the sounds that are playing.
     * 
     * @author Brady
     *
     */
    private static class SoundWrapper implements Sound {

        private Clip clip;
        private String name;
        double timeInSeconds = 0.0;
        boolean loop = false;

        boolean isFadingOut = false;
        double fadingTime = 0.0;
        long lastTime;

        float lastVolume = 0.0f;

        FloatControl volume;

        /**Creates a new Sound Object
         * 
         * @param fname File Name
         * @param type Type of Audio (SFX or BGM)
         * @param c Clip to be created
         */
        public SoundWrapper(String fname, boolean loop, Clip c) {
            this.name = fname;
            this.loop = loop;
            this.clip = c;
            volume = null;
        }
        /**Plays the audio
         * @param loop Loops the audio if true
         */
        public void play() {
        	if (!this.clip.isOpen()) return; //Dpon't do anything if line isn't open
            stop();
            if(loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.loop(0);
            }
            clip.start();
            try {
                volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        /**Pauses the audio
         * 
         */
        public void pause() {
        	if (!this.clip.isOpen()) return; //Don't do anything if line isn't open
            clip.stop();
        }
        /**Stops the audio
         * 
         */
        public void stop() {
        	if (!this.clip.isOpen()) return; //Don't do anything if line isn't open
            clip.stop();
            clip.setFramePosition(0);
        }
        /**Skips to a point in the track
         * 
         */
        public void goTo(int frames) {
        	if (!this.clip.isOpen()) return; //Don't do anything if line isn't open
            clip.setFramePosition(frames);
        }
        /**Fades out the music
         * 
         */
        public void fadeOut(double seconds) {
        	if (!this.clip.isOpen()) return; //Don't do anything if line isn't open

            //FloatControl masterGain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (volume != null) {
                //lastVolume = volume.getValue();
                //volume.shift(volume.getValue(), volume.getMinimum(), (int) (seconds*1000000));
                isFadingOut = true;
                fadingTime = seconds;
                lastTime = System.currentTimeMillis();
                System.out.println(volume.getMinimum());
            } else {
                System.out.println("Play First!");
            }
        }
        /**Gets the Name of the current song playing.
         * 
         */
        public String getName() {
            return name;
        }
        /**Tells if the Sound is currently playing.
         * 
         */
        public boolean isPlaying() {
            return false;
        }
        /**If Something needs to be done, do it.
         * 
         */
        public void run(){
        	if (!this.clip.isOpen()) return; //Don't do anything if line isn't open
            if(isFadingOut == true) {
                int milliseconds = (int) (fadingTime * 1000);
                double distance = volume.getValue() - volume.getMinimum();
                double rate = distance / milliseconds;
                if(lastTime + rate >= System.currentTimeMillis() && volume.getValue() >= volume.getMinimum()) {
                    lastTime = System.currentTimeMillis();
                    volume.setValue((float) (volume.getValue() - rate));
                    System.out.println(volume.getValue());
                }
            }
        }
    }

    public static void main(String[] args) {
        AudioManager am = new AudioManager();
        (new Thread(am)).start();
        Sound music = AudioManager.loadSound("test.wav", true);
        music.play();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fading out...");
        music.fadeOut(1.0);
        while(true) {

        }

    }

}
