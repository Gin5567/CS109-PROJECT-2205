package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import model.Chessboard;
import controller.GameController;

public class Menu extends BasePanel{
    public int MenuWidth;
    public  int MenuHeight;
    public static volatile Menu sInstance = null;

    public MyButton newGame = new MyButton("New Game",0);
    public MyButton load = new MyButton("Load",1);
    public MyButton settings = new MyButton("Settings",0);
    public MyButton rank = new MyButton("Ranking",1);
    public MyButton quit = new MyButton("Quit",0);
    public Box MenuPanel = new Box(BoxLayout.Y_AXIS);
    public JLabel title = new JLabel("Jungle");
    public Image MenuImage;

    {
        try {
            MenuImage = ImageIO.read(new File("src/Image/menu.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BackgroundImagePanel BackPanel = new BackgroundImagePanel(MenuImage);

    public Menu(int width,int height){
        initButton(newGame);
        initButton(load);
        initButton(settings);
        initButton(rank);
        initButton(quit);
        initLabel(title);
        initBorder(title);
        title.setForeground(new Color(96, 255, 0, 211));
        this.MenuWidth = width;
        this.MenuHeight = height;
        setSize(this.MenuWidth, this.MenuHeight);
        add(BackPanel);
        BackPanel.setLayout(new BorderLayout());
        BackPanel.setSize(1024,768);
        MenuPanel.add(title);
        MenuPanel.add(Box.createHorizontalStrut(15));
        MenuPanel.add(Box.createVerticalStrut(50));
        MenuPanel.add(newGame);
        MenuPanel.add(Box.createVerticalStrut(50));
        MenuPanel.add(load);
        MenuPanel.add(Box.createVerticalStrut(50));
        MenuPanel.add(rank);
        MenuPanel.add(Box.createVerticalStrut(50));
        MenuPanel.add(settings);
        MenuPanel.add(Box.createVerticalStrut(50));
        MenuPanel.add(quit);
        MenuPanel.setVisible(true);
        MenuPanel.add(Box.createVerticalGlue());
        BackPanel.add(MenuPanel);
        BackPanel.setVisible(true);
        newGame.addActionListener((e) -> {
            GameController CurrentGameController = GameController.instance(ChessGameFrame.instance().getChessboardComponent(),Chessboard.getInstance());
            CurrentGameController.resetGame();
            View.changePanel("ChessGameFrame");
        });
        load.addActionListener((e) -> View.changePanel("Load"));
        rank.addActionListener((e) -> View.changePanel("Ranking"));
        settings.addActionListener((e) -> View.changePanel("Settings"));
        quit.addActionListener((e) -> View.window.dispose());
    }
    public void initButton(MyButton jButton){
        jButton.setSize(200,60);
        jButton.setFont(new Font("ROCKWELL",Font.BOLD,50));
        jButton.setForeground(Color.black);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton.setForeground(Color.BLACK);
            }
        });

    }

    public void initLabel(JLabel jLabel){
        jLabel.setSize(200,60);
        jLabel.setFont(new Font("ROCKWELL", Font.BOLD,50));
    }
    public void initBorder(JLabel jLabel){
        Border border = BorderFactory.createBevelBorder(2,Color.YELLOW,Color.GRAY);
        jLabel.setBorder(border);
    }
    public static Menu instance() {
        if (sInstance == null) {
            synchronized (BasePanel.class) {
                if (sInstance == null) {
                    sInstance = new Menu(1024,768);
                }
            }
        }
        return sInstance;
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (MenuImage != null) {
            g.drawImage(MenuImage, 0, 0, getWidth(), getHeight(), null);
        }
        super.paintComponent(g);
    }


}
