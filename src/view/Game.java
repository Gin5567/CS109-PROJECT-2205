package view;

public class Game extends BasePanel{
    private static volatile Game sInstance = null;
    public static Game instance() {
        if (sInstance == null) {
            synchronized (BasePanel.class) {
                if (sInstance == null) {
                    sInstance = new Game();
                }
            }
        }
        return sInstance;
    }
}
