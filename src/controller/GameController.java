package controller;


import listener.GameListener;
import model.PlayerColor;
import model.ChessboardPoint;
import model.Timer;
import view.*;
import java.io.*;
import javax.swing.JOptionPane;

import javax.swing.*;
import model.Step;
import java.util.ArrayList;
import model.ChessPiece;
import model.Chessboard;
import view.ChessGameFrame;
import view.ChessboardComponent;
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private ChessGameFrame chessGameFrame = ChessGameFrame.instance();
    public static PlayerColor currentPlayer;
    public PlayerColor winner;
    public JLabel timeLabel;
    public ArrayList<Step> undoList = new ArrayList<>();
    private ChessboardPoint selectedPoint;

    public int turn = 1 ;
    public int RemainingTime = 60;
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }
    public int getTurn() {
        return turn;
    }
    public void RunTimer() {
        synchronized (this) {
            while (!win()) {

                while (RemainingTime > 0) {
                    try {
                        Thread.sleep(1000);
                        RemainingTime--;
                        chessGameFrame.getTimerLabel().setText("Time: " + RemainingTime);
                        if (RemainingTime == 0) {
                            if (currentPlayer == PlayerColor.BLUE) {
                                currentPlayer = PlayerColor.RED;
                                turn++;
                                chessGameFrame.getPlayerLabel().setText("Player: " + currentPlayer);
                                chessGameFrame.getTurnLabel().setText("Turn: " + turn);
                            } else if (currentPlayer == PlayerColor.RED) {
                                currentPlayer = PlayerColor.BLUE;
                                turn++;
                                chessGameFrame.getPlayerLabel().setText("Player: " + currentPlayer);
                                chessGameFrame.getTurnLabel().setText("Turn: " + turn);

                            }
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                RemainingTime = 60;
            }
        }
    }
    public void setRemainingTime(int remainingTime) {
        RemainingTime = remainingTime;
    }
    private boolean win() {
        if (model.getAllChessPieces(currentPlayer) == 0) {
            return true;
        }
        if (currentPlayer == PlayerColor.BLUE) {
            return model.getChessPieceOwner(new ChessboardPoint(0, 3)).equals(PlayerColor.BLUE);
        } else {
            return model.getChessPieceOwner(new ChessboardPoint(8, 3)).equals(PlayerColor.RED);
        }
    }
    public void doWin() {
        JOptionPane.showMessageDialog(view, (winner == PlayerColor.BLUE ? "RED" : "BLUE") + " Win !");
        View.changePanel("Menu");
    }
    public void doWinAlter() {
        JOptionPane.showMessageDialog(view, (winner == PlayerColor.BLUE ? "BLUE" : "RED") + " Win !");
        View.changePanel("Menu");
    }
    public void swapColor() {
        if(win()) {
            if (model.getAllChessPieces(currentPlayer) != 0) {
                doWin();
            }
            else if (model.getAllChessPieces(currentPlayer) == 0) {
                doWinAlter();
            }
        }
        turn++;
        chessGameFrame.getTurnLabel().setText("Turn: " + turn);
        chessGameFrame.getPlayerLabel().setText("Player: " + (currentPlayer == PlayerColor.BLUE ? "RED" : "BLUE"));
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            setMovableGridsUnhighlighted();
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            view.revalidate();
        }
    }
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }
    public void onPlayerClickChessPiece(ChessboardPoint point, RatChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }

    }

    public void onPlayerClickChessPiece(ChessboardPoint point, CatChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }

    public void onPlayerClickChessPiece(ChessboardPoint point, DogChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }

    public void onPlayerClickChessPiece(ChessboardPoint point, WolfChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }

    public void onPlayerClickChessPiece(ChessboardPoint point, TigerChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }

    public void onPlayerClickChessPiece(ChessboardPoint point, LeopardChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }

    public void onPlayerClickChessPiece(ChessboardPoint point, LionChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }
    public void onPlayerClickChessPiece(ChessboardPoint point, TrapChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }
    public void onPlayerClickChessPiece(ChessboardPoint point, HoleChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                setMovableGridsHighlighted(selectedPoint);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            setMovableGridsUnhighlighted();
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidMove(selectedPoint, point)) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else if (model.isValidCapture(selectedPoint, point)) {
                setMovableGridsUnhighlighted();
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                component.repaint();
                component.revalidate();
            }
        }
    }
    public static GameController instance;

    public static GameController instance(ChessboardComponent view, Chessboard model) {
        if (instance == null) {
            instance = new GameController(view, model);
        }
        return instance;
    }

    public void setMovableGridsHighlighted(ChessboardPoint point) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (model.isValidMove(point, dest)) {
                    view.setChessComponentAtGrid(dest, new MovableGridChessComponent(dest, 1));
                    view.repaint();
                    view.revalidate();
                } else if (model.isValidCapture(point, dest)) {
                    view.repaint();
                    view.revalidate();
                }
            }
        }
    }
    public void setMovableGridsUnhighlighted() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (view.getGridComponentAt(dest).getName() != null) {
                    if (view.getGridComponentAt(dest).getName().equals("MovableGrid")) {
                        view.removeChessComponentAtGrid(dest);
                        model.removeChessPiece(dest);
                        view.repaint();
                        view.revalidate();
                    }
                }
            }
        }
    }
    public void resetGame() {
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunTimer();
            }
        });
        timerThread.start();
        RemainingTime = 60;
        model.steps.clear();
        undoList.clear();
        turn = 1;
        chessGameFrame.getTurnLabel().setText("Turn: " + turn);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                view.removeChessComponentAtGrid(point);
                model.removeChessPiece(point);
            }
        }
        model.initGrid();
        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        view.revalidate();
        currentPlayer = PlayerColor.BLUE;
    }

    public void reset() {
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunTimer();
            }
        });
        timerThread.start();
        RemainingTime = 60;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                view.removeChessComponentAtGrid(point);
                model.removeChessPiece(point);
            }
        }
        model.initGrid();
        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        view.revalidate();
        currentPlayer = PlayerColor.BLUE;
    }

    public void undo() {
        if(model.steps.size() != 0) {
            undoList.add(model.steps.get(model.steps.size() - 1));
            model.steps.remove(model.steps.size() - 1);
            ArrayList<Step> list = model.steps;
            turn = 1;
            chessGameFrame.getTurnLabel().setText("Turn: " + turn);
            chessGameFrame.getPlayerLabel().setText("Player: " + (currentPlayer == PlayerColor.BLUE ? "RED" : "BLUE"));


            reset();
            if (list.size() != 0) {


                for (int i = 0; i < list.size(); i++) {
                    Step step = list.get(i);
                    ChessboardPoint src = step.src;
                    ChessboardPoint dest = step.dest;
                    PlayerColor color = step.color;
                    boolean isCapture = step.captured != null;
                    if (!isCapture) {
                        model.setChessPiece(dest, model.removeChessPiece(src));
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        selectedPoint = null;
                        currentPlayer = color;
                        swapColor();
                        view.repaint();
                        view.revalidate();
                    } else {
                        model.removeChessPiece(dest);
                        model.setChessPiece(dest, model.removeChessPiece(src));
                        view.removeChessComponentAtGrid(dest);
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        currentPlayer = color;
                        swapColor();
                        view.repaint();
                        view.revalidate();
                    }

                }
            }
        }
    }


    public void redo() {
        if (undoList.size() == 0) return;
        Step step = undoList.get(undoList.size() - 1);
        ChessboardPoint src = step.src;
        ChessboardPoint dest = step.dest;
        PlayerColor color = step.color;
        ChessPiece captured = step.captured;
        boolean isCapture = captured != null;
        if (!isCapture) {
            model.steps.add(step);
            undoList.remove(undoList.size() - 1);
            model.moveChessPiece(src, dest);
            view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
            selectedPoint = null;
            currentPlayer = color;
            swapColor();
            view.repaint();
            view.revalidate();
        } else {
            model.steps.add(step);
            undoList.remove(undoList.size() - 1);
            model.captureChessPiece(src, dest);
            view.removeChessComponentAtGrid(dest);
            view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
            currentPlayer = color;
            swapColor();
            view.repaint();
            view.revalidate();
        }
    }
    public static GameController getInstance() {
        return instance;
    }




    private static String animalToString(ChessPiece chess){
        if (chess == null || chess.getName().equals("Trap") || chess.getName().equals("Hole")) return "+";
        else if (chess.getName().equals("Elephant")) return "E";
        else if (chess.getName().equals("Lion")) return "L";
        else if (chess.getName().equals("Tiger")) return "T";
        else if (chess.getName().equals("Leopard")) return "l";
        else if (chess.getName().equals("Wolf")) return "w";
        else if (chess.getName().equals("Dog")) return "d";
        else if (chess.getName().equals("Cat")) return "c";
        else if (chess.getName().equals("Rat")) return "r";
        else return "";
    }
    private static boolean checkName(String[] chess){
        for (int i = 0; i < chess.length; i++) {
            if (!chess[i].equals("E") && !chess[i].equals("L") && !chess[i].equals("T") && !chess[i].equals("l")
                    && !chess[i].equals("w") && !chess[i].equals("d") && !chess[i].equals("c") && !chess[i].equals("r")
                    && !chess[i].equals("+")){
                return false;
            }
        }
        return true;
    }
    public void Save(String fileName) {
        String location = "save\\" + fileName + ".txt";
        File file = new File(location);
        try {
            if(file.exists()){
                int n = JOptionPane.showConfirmDialog(view, "存档已存在，是否覆盖?", "", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    file.delete();
                }
            }
            FileWriter fileWriter = new FileWriter(location,true);
            fileWriter.write(model.steps.size() + "");
            fileWriter.write("\n");

            for (int i = 0; i < model.steps.size(); i++){
                fileWriter.write(model.steps.get(i).toString());
                fileWriter.write("\n");
            }

            fileWriter.write(currentPlayer == PlayerColor.BLUE ? "b" : "r");
            fileWriter.write("\n");

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessPiece chess = model.getGrid()[i][j].getPiece();
                    fileWriter.write(animalToString(chess) + " ");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
            System.out.println("Save Done");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean Load(File file){
        if (!file.getName().endsWith(".txt")){
            JOptionPane.showMessageDialog(null, "File Error",
                    "文件后缀错误,并非.txt", JOptionPane.ERROR_MESSAGE);
            reset();
            return false;
        }
        try {
            String temp;
            ArrayList<String> readList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            while((temp = reader.readLine()) != null && !"".equals(temp)){
                readList.add(temp);
            }

            int num = Integer.parseInt(readList.remove(0));
            for (int i = 0; i <= num; i++) {
                String str = readList.get(i);
                if (i % 2 == 0 && str.charAt(0) != 'b'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "File Error",
                            "行棋方错误", JOptionPane.ERROR_MESSAGE);
                    reset();
                    return false;
                }
                if (i % 2 == 1 && str.charAt(0) != 'r'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "File Error",
                            "行棋方错误", JOptionPane.ERROR_MESSAGE);
                    reset();
                    return false;
                }
            }

            try {
                for (int i = num + 1; i < num + 10; i++) {
                    boolean b = true;
                    String[] chess= readList.get(i).split(" ");
                    if (chess.length != 7){
                        JOptionPane.showMessageDialog(null, "File Error",
                                "棋盘规格错误，大小并非9*7", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    if (!checkName(chess)) b = false;
                    if (!b){
                        JOptionPane.showMessageDialog(null, "File Error",
                                "棋子错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "File Error",
                        "棋盘规格错误，大小并非9*7", JOptionPane.ERROR_MESSAGE);
                reset();
                return false;
            }

            reset();
            for (int i = 0; i < num; i++) {
                String[] info = readList.get(i).split(" ");
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(info[1].charAt(1) + ""),
                        Integer.parseInt(info[1].charAt(3) + ""));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(info[2].charAt(1) + ""),
                        Integer.parseInt(info[2].charAt(3) + ""));
                boolean isCapture = !info[3].equals("null");

                if (!isCapture){
                    if (!model.isValidMove(src, dest)){
                        JOptionPane.showMessageDialog(null, "File Error",
                                "行棋步骤错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    selectedPoint = null;
                    swapColor();
                    view.repaint();

                } else {
                    if (!model.isValidCapture(src, dest)){
                        JOptionPane.showMessageDialog(null, "File Error",
                                "行棋步骤错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    swapColor();
                    view.repaint();
                    view.revalidate();
                }
            }
        } catch (Exception ex){
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No File",
                    "错误", JOptionPane.ERROR_MESSAGE);
            reset();
        }
        return true;
    }

}