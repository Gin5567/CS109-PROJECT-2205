import view.ChessGameFrame;
import view.View;
import model.*;
import javax.swing.*;
import model.Chessboard;
import controller.GameController;

public class Main {
    public static void main(String[] args) {
        View.window.setSize(1024, 768);
        View.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        View.window.setContentPane(View.sceneHolder);
        View.window.setVisible(true);
        View.window.setLocationRelativeTo(null);
        GameController gameController = new GameController(ChessGameFrame.instance().getChessboardComponent(), new Chessboard());
    }
}
