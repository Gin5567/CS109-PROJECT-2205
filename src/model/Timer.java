package model;
import controller.GameController;
public class Timer extends Thread {
    private int PlayerTime = 60;
    private PlayerColor player;
    public GameController gameController = GameController.getInstance();


    public void run() {

        synchronized (this) {
            while (true) {
                player = GameController.currentPlayer;
                while (PlayerTime > 0) {
                    PlayerTime--;
                    try {
                        Thread.sleep(1000);
                        gameController.timeLabel.setText("Time: " + PlayerTime);
                        if (GameController.currentPlayer != player) {
                            gameController.swapColor();
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                PlayerTime = 60;
            }
        }
    }
    public void setPlayerTime(int playerTime) {
        PlayerTime = playerTime;
    }
    public int getPlayerTime() {
        return PlayerTime;
    }
}
