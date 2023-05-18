package view;

import model.ChessPiece;
import model.PlayerColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    private ImageIcon icon;
    private Image image;
    private String Name;



    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
        loadImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public void setIcon(ImageIcon icon) {
        icon = new  ImageIcon("src/PieceImage/River.png");
        this.icon = icon;
        repaint();
    }

    private void loadImage() {
        try {if(background == Color.LIGHT_GRAY)
                image = ImageIO.read(new File("src/PieceImage/Grass.png"));
            else image = ImageIO.read(new File("src/PieceImage/River.png"));
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

