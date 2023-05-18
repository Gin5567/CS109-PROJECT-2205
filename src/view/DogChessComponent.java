package view;

import model.PlayerColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DogChessComponent extends BaseChessComponent {
    private PlayerColor owner;
    private boolean selected;
    private BufferedImage image;

    public DogChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
        setName("Dog");
        loadImage();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private void loadImage() {
        try {
            if (owner == PlayerColor.RED) {
                image = ImageIO.read(new File("src/PieceImage/Dog-Red.png")); // Replace "path/to/red_dog.jpg" with the actual path to your red dog image file.
            } else {
                image = ImageIO.read(new File("src/PieceImage/Dog-Blue.png")); // Replace "path/to/black_dog.jpg" with the actual path to your black dog image file.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
