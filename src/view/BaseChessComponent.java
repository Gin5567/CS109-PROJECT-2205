package view;

import model.PlayerColor;

import javax.swing.*;
import java.util.jar.JarEntry;

public class BaseChessComponent extends JComponent {
    private PlayerColor owner;
    private String name;

    private boolean selected;
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
