package view;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings extends BasePanel{
    private static volatile Settings sInstance = null;
    public static Settings instance() {
        if (sInstance == null) {
            synchronized (Settings.class) {
                if (sInstance == null) {
                    sInstance = new Settings();
                }
            }
        }
        return sInstance;
    }
    private MyButton Back = new MyButton();
    private  Menu menu = new Menu(1024,768);
    private MyButton AIModeButton = new MyButton();
    private MyButton VolumeButton = new MyButton();
    public int AIDifficulty = 0;
    private JLabel title = new JLabel();
    private View View = new View();
    private Settings (){
        setSize(1024,768);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(167));
        addTitle();
        addBackButton();
        addAIModeButton();
        addVolumeButton();
        Back.addActionListener(e -> {
            View.changePanel("Menu");
        });
        AIModeButton.addActionListener(e -> {
            if(AIDifficulty == 0){
                AIDifficulty = 1;
                AIModeButton.setText("AI Mode: Easy");
            }
            else if(AIDifficulty == 1){
                AIDifficulty = 2;
                AIModeButton.setText("AI Mode: Hard");
            }
            else if(AIDifficulty == 2){
                AIDifficulty = 3;
                AIModeButton.setText("AI Mode: Random");
            }
            else if(AIDifficulty == 3){
                AIDifficulty = 0;
                AIModeButton.setText("AI Mode: Off");
            }
        });
        VolumeButton.addActionListener(e -> {
            if(View.getVolume() == 0){
                View.setVolume(0.25);
                VolumeButton.setText("Volume: 25%");
            }
            else if(View.getVolume() == 0.25){
                View.setVolume(0.5);
                VolumeButton.setText("Volume: 50%");
            }
            else if(View.getVolume() == 0.5){
                View.setVolume(0.75);
                VolumeButton.setText("Volume: 75%");
            }
            else if(View.getVolume() == 0.75){
                View.setVolume(1);
                VolumeButton.setText("Volume: 100%");
            }
            else if(View.getVolume() == 1){
                View.setVolume(0);
                VolumeButton.setText("Volume: 0%");
            }

        });


    }
    private void addTitle(){
        title.setLocation(0,0);
        title.setSize(1024,100);
        title.setFont(new Font("Rockwell", Font.BOLD, 50));
        title.setText("Settings");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
    }
    private void addBackButton(){
        Back.setSize(200,50);
        Back.setText("Back");
        add(Box.createVerticalStrut(35));
        menu.initButton(Back);
        add(Back);
    }
    private void addAIModeButton(){
        AIModeButton.setSize(200,50);
        AIModeButton.setText("AI Mode: Off");
        add(Box.createVerticalStrut(35));
        menu.initButton(AIModeButton);
        add(AIModeButton);
    }
    private void addVolumeButton(){
        VolumeButton.setSize(200,50);
        VolumeButton.setText("Volume: 0%");
        add(Box.createVerticalStrut(35));
        menu.initButton(VolumeButton);
        add(VolumeButton);
    }



}
