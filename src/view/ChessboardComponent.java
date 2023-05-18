package view;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import controller.GameController;


import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    public final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private GameController gameController;


    public ChessboardComponent(int chessSize) {

        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = getComponentAt(e.getX(), e.getY());
                String name = clickedComponent.getName();
                System.out.println(name);
            }
        });
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        gridComponents[2][6].add(new ElephantChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[2][6].setName("Elephant");
        gridComponents[2][4].add(new WolfChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[2][4].setName("Wolf");
        gridComponents[2][2].add(new LeopardChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[2][2].setName("Leopard");
        gridComponents[2][0].add(new RatChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[2][0].setName("Rat");
        gridComponents[1][1].add(new DogChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[1][1].setName("Dog");
        gridComponents[1][3].add(new TrapChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[1][3].setName("Trap");
        gridComponents[1][5].add(new CatChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[1][5].setName("Cat");
        gridComponents[0][0].add(new LionChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[0][0].setName("Lion");
        gridComponents[0][6].add(new TigerChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[0][6].setName("Tiger");
        gridComponents[0][2].add(new TrapChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[0][2].setName("Trap");
        gridComponents[0][3].add(new HoleChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[0][3].setName("Hole");
        gridComponents[0][4].add(new TrapChessComponent(PlayerColor.RED, CHESS_SIZE));
        gridComponents[0][4].setName("Trap");

        gridComponents[6][0].add(new ElephantChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[6][0].setName("Elephant");
        gridComponents[6][2].add(new WolfChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[6][2].setName("Wolf");
        gridComponents[6][4].add(new LeopardChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[6][4].setName("Leopard");
        gridComponents[6][6].add(new RatChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[6][6].setName("Rat");
        gridComponents[7][5].add(new DogChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[7][5].setName("Dog");
        gridComponents[7][3].add(new TrapChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[7][3].setName("Trap");
        gridComponents[7][1].add(new CatChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[7][1].setName("Cat");
        gridComponents[8][6].add(new LionChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[8][6].setName("Lion");
        gridComponents[8][0].add(new TigerChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[8][0].setName("Tiger");
        gridComponents[8][2].add(new TrapChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[8][2].setName("Trap");
        gridComponents[8][3].add(new HoleChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[8][3].setName("Hole");
        gridComponents[8][4].add(new TrapChessComponent(PlayerColor.BLUE, CHESS_SIZE));
        gridComponents[8][4].setName("Trap");
        setVisible(true);
    }


    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }

    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, BaseChessComponent chess) {
        getGridComponentAt(point).add(chess);
        getGridComponentAt(point).setName(chess.getName());
        getGridComponentAt(point).revalidate();
        getGridComponentAt(point).repaint();
        for (Component c : getGridComponentAt(point).getComponents()) {
            if (c instanceof CellComponent) {
                c.revalidate();
                c.repaint();
            }
        }
    }

    public BaseChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        if (getGridComponentAt(point).getComponents().length != 0) {
            BaseChessComponent chess = (BaseChessComponent) getGridComponentAt(point).getComponents()[0];
            getGridComponentAt(point).setName("");
            getGridComponentAt(point).removeAll();
            getGridComponentAt(point).revalidate();
            chess.setSelected(false);
            return chess;
        } else {
            return null;
        }
    }




    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    public ChessboardComponent instance() {
        return this;
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            // 获取被点击的组件
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());

            // 检查被点击的组件是否为一个棋子
            if (clickedComponent.getName() != null) {
                switch (clickedComponent.getName()) {
                    case "Elephant":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ElephantChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Lion":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (LionChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Leopard":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (LeopardChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Tiger":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (TigerChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Wolf":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (WolfChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Dog":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (DogChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Cat":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (CatChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Rat":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (RatChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Trap":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (TrapChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    case "Hole":
                        gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (HoleChessComponent) clickedComponent.getComponents()[0]);
                        break;
                    default:
                        System.out.print("None chess here and ");
                        gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
                }
            } else {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            }
        }
    }

    public void removeAllChessComponent() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (getGridComponentAt(point).getComponents().length != 0) {
                    BaseChessComponent chess = (BaseChessComponent) getGridComponentAt(point).getComponents()[0];
                    getGridComponentAt(point).setName("");
                    getGridComponentAt(point).removeAll();
                    getGridComponentAt(point).revalidate();
                    chess.setSelected(false);
                }
            }
        }
    }
}



