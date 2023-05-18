package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class View {

    public static final JFrame window = new JFrame();
    public static final CardLayout layout = new CardLayout();
    public static final JPanel sceneHolder = new JPanel(layout);
    private static final Map<String, BasePanel> stages = new HashMap<>();
    private static BasePanel currentpanel;
    private double CurrentVolume = 0;

    static {
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1024, 768);
        window.setContentPane(sceneHolder);
        window.setVisible(true);
        window.setTitle("Jungle");
        addPanel("Menu", Menu.instance());
        addPanel("Load", Load.instance());
        addPanel("Game", Game.instance());
        addPanel("Ranking", Ranking.instance());
        addPanel("Settings", Settings.instance());
        addPanel("ChessGameFrame", ChessGameFrame.instance());
        currentpanel = Menu.instance();
    }
    public static void addPanel(String name, BasePanel stage) {
        stages.put(name, stage);
        sceneHolder.add(stage, name);
    }
    public static BasePanel getPanel(String name) {
        return stages.get(name);
    }
    public static void changePanel(String name) {
        if (!stages.containsKey(name)) {
            System.err.println("No stage found: " + name);
            return;
        }
        currentpanel.exit();
        currentpanel = stages.get(name);
        currentpanel.enter();
        layout.show(sceneHolder, name);
    }
    public double getVolume() {
        return CurrentVolume;
    }
    public void setVolume(double volume) {
        CurrentVolume = volume;
    }


}
