package component;

import window.Window;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.sound.sampled.*;

public class Sound {
    public static final Sound WEAPON_SHOOT_0 = new Sound("/assets/SingleTap.wav");
    public static final Sound TRACK_1 = new Sound("/assets/sakura_stage.wav");
    public static final Sound TRACK_2 = new Sound("/assets/versus.wav");
    public final Set<Clip> clips = Collections.synchronizedSet(new HashSet<>());

    private final AudioFormat format;
    private final byte[] bytes;
    private static float currVolume = 0;
    private float volumeF;

    public Sound(String path) {
        volumeF =  1f;
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(Window.class.getResourceAsStream(path)));

            this.format = stream.getFormat();
            this.bytes = stream.readAllBytes();

            for (int i = 0; i < 4; i++) {
                this.createNewClip();
            }
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new Error(e);
        }
    }

    private Clip createNewClip() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(this.format, this.bytes, 0, this.bytes.length);
            clips.add(clip);
            return clip;
        } catch (LineUnavailableException e) {
            throw new Error(e);
        }
    }

    public void play() {
        new Thread(() -> {
            Clip clip = clips.stream()
                    .filter(c ->
                            c.getFramePosition() == 0 ||
                                    c.getFramePosition() == c.getFrameLength())
                    .findFirst()
                    .orElseGet(this::createNewClip);

            clip.setFramePosition(0);
            clip.start();
        }).start();
    }

    public static final Clip[] musicClip = new Clip[1];
//    static Thread musicPlayer;

    public static void playMusic(Clip s){

        if(musicClip[0] != null ){
            musicClip[0].stop();
        }
        musicClip[0] = s;
        musicClip[0].setFramePosition(0);
        musicClip[0].loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void setVolume(float f, Sound sound){
        for (Clip c: sound.clips){
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(f);
        }
    }
    public static void VolumeUp(Sound sound ,double deltaTime){
        for (Clip c: sound.clips){
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
//            Volume-Up logic
            currVolume += 1.0f * deltaTime;
            currVolume = clamp(currVolume,-100f,5f);

//
            volume.setValue(currVolume);
        }
    }

    public static void VolumeDown(Sound sound, double deltaTime){
        for (Clip c: sound.clips){
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            //Volume-Down logic
            currVolume -= 1.0f * deltaTime;
            currVolume = clamp(currVolume,-100f,5f);

            volume.setValue(currVolume);
        }
    }
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
    public static void setMute(Sound sound){
        for (Clip c: sound.clips){
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-40f);
        }
    }
    public static float getVolume(Sound sound){
        float currentVal = 0;
        for (Clip c: sound.clips){
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            currentVal = volume.getValue();
        }
        return currentVal;
    }

    public Clip getClip(){
        return clips.stream()
                .filter(c ->c.getFramePosition() == 0 || c.getFramePosition() == c.getFrameLength())
                .findFirst()
                .orElseGet(this::createNewClip);

    }

}

//public class Sound {
//
//    Clip clip;
//    URL[] soundURL = new URL[30];
//    public Sound(){
//        soundURL[0] = getClass().getResource("/assets/SingleTap.wav");
//    }
//
//    public void setFile(int i) {
//        try{
//            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
//            clip = AudioSystem.getClip();
//            clip.open(ais);
//        }catch(Exception e){
//        }
//    }
//    public void play(){
////       new Thread(()->{
////            clip.
////            clip.start();
////       }).start();
//        new Thread(() -> {
//            clip.getFrameLength()
//            clip.stream()
//                    clip.
//                    .filter(c ->
//                            c.getFramePosition() == 0 ||
//                                    c.getFramePosition() == c.getFrameLength())
//                    .findFirst()
//                    .orElseGet(this::createNewClip);
//            clip.setFramePosition(0);
//            clip.start();
//        }).start();
//
//    }
//    public void loop(){
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//    public void stop(){
//        clip.stop();
//    }
//}
//