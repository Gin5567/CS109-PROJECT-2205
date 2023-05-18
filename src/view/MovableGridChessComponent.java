package view;

import model.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class MovableGridChessComponent extends BaseChessComponent{
    public BufferedImage image;
    public MovableGridChessComponent(ChessboardPoint point,int size) {
        setSize(size/2, size/2);
        setLocation(0,0);
        loadImage();
        setVisible(true);
        setName("MovableGrid");

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

    }
    private void loadImage() {
        try {
            {
                image = ImageIO.read(new File("src/PieceImage/Highlight.png")); // Replace "path/to/red_dog.jpg" with the actual path to your red dog image file.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
