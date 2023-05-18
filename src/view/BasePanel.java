package view;

import Audio.AudioPlayer;

import javax.swing.*;
import java.util.concurrent.Future;

public abstract class BasePanel extends JPanel {

    private String bgmPath = null;
    private Future<?> bgmThread = null;

    public BasePanel(){
        setSize(1024,768);
    }
    public void enter() {
        if (bgmPath != null)
            bgmThread = AudioPlayer.playBgm(bgmPath);
    }
    public void exit() {
        if (bgmThread != null)
            bgmThread.cancel(true);
        bgmThread = null;
    }

}
