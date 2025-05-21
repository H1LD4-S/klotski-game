package utils;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    // 预加载所有音频资源
    public static Clip BACKGROUND_MUSIC;
    public static Clip MOVE_SOUND;

    static {
        try {
            // 背景音乐
            BACKGROUND_MUSIC = loadClip("bg_music.wav");
            // 移动音效
            MOVE_SOUND = loadClip("move_sound.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Clip loadClip(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = AudioPlayer.class.getClassLoader().getResource("audio/" + filename);
        if (url == null) {
            throw new IOException("音频文件 " + filename + " 未找到");
        }
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        return clip;
    }

    public static void playBackgroundMusic() {
        if (BACKGROUND_MUSIC != null) {
            BACKGROUND_MUSIC.loop(Clip.LOOP_CONTINUOUSLY);
            BACKGROUND_MUSIC.start();
        }
    }

    public static void playMoveSound() {
        if (MOVE_SOUND != null) {
            MOVE_SOUND.setFramePosition(0); // 重置播放位置
            MOVE_SOUND.start();
        }
    }

    public static void stopAll() {
        if (BACKGROUND_MUSIC != null) BACKGROUND_MUSIC.stop();
        if (MOVE_SOUND != null) MOVE_SOUND.stop();
    }
}

