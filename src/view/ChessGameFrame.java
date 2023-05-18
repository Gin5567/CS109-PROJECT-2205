package view;

import listener.GameListener;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import model.Timer;
import model.Chessboard;
import model.ChessboardPoint;
import model.ChessPiece;
import model.Step;
import model.Timer;
import model.Chessboard;
import model.ChessboardPoint;
import controller.GameController;

import java.io.IOException;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends BasePanel {
    private Chessboard model;
    private ChessboardComponent view;


    private static volatile ChessGameFrame sInstance = null;

    public static ChessGameFrame instance() {
        if (sInstance == null) {
            synchronized (BasePanel.class) {
                if (sInstance == null) {
                    sInstance = new ChessGameFrame(1024, 768, "Chess/src/Image/BackGround.jpg");
                }
            }
        }
        return sInstance;
    }

    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public PlayerColor CurrentPlayer = PlayerColor.BLUE;

    private final int ONE_CHESS_SIZE;
    public JLabel TurnLabel = new JLabel();
    public JLabel PlayerLabel = new JLabel();
    private Timer timer;

    private GameListener gameListener;
    private ChessboardComponent chessboardComponent;


    public ChessGameFrame(int width, int height , String imagePath) {
        this.WIDTH = width;
        this.HEIGTH = height;
        this.timer = new Timer();
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLayout(null);
        addChessboard();
        addBackButton();
        addSaveButton();
        addUndoButton();
        addSurrenderButton();
        addRedoButton();
        addResetButton();
        initPlayerLabel(PlayerLabel);
        initTurnLabel(TurnLabel);
        PlayerLabel.setText("Player: BLUE");
        TurnLabel.setText("Turn: 1");
        setVisible(true);
        revalidate();
        repaint();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 10+100, HEIGTH / 10-25);
        add(chessboardComponent);
        repaint();
        revalidate();
    }

    private void addBackButton() {
        JButton button = new MyButton("Back");
        button.setLocation(HEIGTH, HEIGTH / 10);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);

        button.addActionListener(e -> {
            View.changePanel("Menu");
        });
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
    }

    private void addSaveButton() {
        Chessboard chessboard = new Chessboard();
        JButton button = new MyButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 80);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
        button.addActionListener(e -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),chessboard.getChessboard());
            String name = JOptionPane.showInputDialog("Please input the name of the file");
            CurrentGameController.Save(name);
        });

    }
    public void addUndoButton() {
        Chessboard chessboard = new Chessboard();
        JButton button = new MyButton("Undo");
        button.setLocation(HEIGTH, HEIGTH / 10 + 160);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
        button.addActionListener(e -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),chessboard.getChessboard());
            CurrentGameController.undo();
        });

    }
    public void addRedoButton(){
        Chessboard chessboard = new Chessboard();
        JButton button = new MyButton("Redo");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
        button.addActionListener(e -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),chessboard.getChessboard());
            CurrentGameController.redo();
        });
    }
    public void addSurrenderButton() {
        Chessboard chessboard = new Chessboard();
        JButton button = new MyButton("Surrender");
        button.setLocation(HEIGTH, HEIGTH / 10 + 320);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
        button.addActionListener(e -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),chessboard.getChessboard());
            if(CurrentGameController.getTurn()%2 == 1){
            CurrentGameController.doWinAlter();}
            else{
                CurrentGameController.doWin();
            }
        });

    }

    public void addBackground(String imagePath) {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(imagePath));
        label.setSize(WIDTH, HEIGTH);
        label.setLocation(0, 0);
        add(label);
    }
    public void addResetButton() {
        JButton button = new MyButton("Reset");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        add(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });
        button.addActionListener(e -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),Chessboard.getInstance());
            CurrentGameController.resetGame();
        });

    }
    public void addTimeLabel() {
        JLabel label = new JLabel();
        label.setText("Time");
        label.setSize(200, 60);
        label.setLocation(HEIGTH, HEIGTH / 10 + 560);
        label.setFont(new Font("Rockwell", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        add(label);
    }
    public void initTurnLabel(JLabel label) {
        label.setSize(200, 60);
        label.setLocation(50, HEIGTH / 10 + 240);
        label.setFont(new Font("Rockwell", Font.BOLD, 25));
        label.setForeground(Color.BLACK);
        add(label);
    }
    public JLabel getTurnLabel() {
        return TurnLabel;
    }
    public void initPlayerLabel(JLabel label) {
        label.setSize(200, 60);
        label.setLocation(25, HEIGTH / 10 + 300);
        label.setFont(new Font("Rockwell", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        add(label);
    }
    public JLabel getPlayerLabel() {
        return PlayerLabel;
    }







}
