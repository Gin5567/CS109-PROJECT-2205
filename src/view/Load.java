package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import controller.GameController;
import model.Chessboard;

public class Load extends BasePanel{
    private  Menu menu = new Menu(1024,768);
    private static volatile Load sInstance = null;
    public static Load instance() {
        if (sInstance == null) {
            synchronized (Load.class) {
                if (sInstance == null) {
                    sInstance = new Load();
                }
            }
        }
        return sInstance;
    }
        private MyButton LoadButton = new MyButton("Load Game");
        private JFileChooser fileChooser = new JFileChooser();
        private MyButton BackButton = new MyButton("Back");
        private JLabel label = new JLabel("Load Game");

        public Load() {
            Chessboard chessboard = new Chessboard();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(Box.createHorizontalStrut(334));
            menu.initLabel(label);
            add(label);
            menu.initButton(LoadButton);
            add(Box.createVerticalStrut(250));
            add(LoadButton);
            menu.initButton(BackButton);
            add(Box.createVerticalStrut(250));
            add(BackButton);
            LoadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = fileChooser.showOpenDialog(Load.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),chessboard.getChessboard());
                        CurrentGameController.Load(selectedFile);
                        repaint();
                        revalidate();
                        View.changePanel("ChessGameFrame");
                    }
                }
            });


            BackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.changePanel("Menu");
                }
            });
        }
    }

