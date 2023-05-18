package model;
import java.util.ArrayList;
import java.util.List;
import controller.GameController;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    public ArrayList<Step> steps;
    public ArrayList<ChessPiece> blueCapturedPieces = new ArrayList<>();
    public ArrayList<ChessPiece> redCapturedPieces = new ArrayList<>();

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        steps = new ArrayList<>();
        initGrid();
        initPieces();
    }
    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    public void initPieces() {
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[1][3].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[0][2].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));
        grid[0][3].setPiece(new ChessPiece(PlayerColor.RED, "Hole", 0));
        grid[0][4].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));

        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[7][3].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[8][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
        grid[8][3].setPiece(new ChessPiece(PlayerColor.BLUE, "Hole", 0));
        grid[8][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
    }
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }
    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }
    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        } else if (isValidMove(src, dest)) {
            steps.add(new Step(src, dest, GameController.currentPlayer));
            setChessPiece(dest, removeChessPiece(src));
        }
    }
    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        } else if (isValidCapture(src, dest)) {
            steps.add(new Step(src, dest, GameController.currentPlayer, getChessPieceAt(dest)));
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
        }
    }
    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }
    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (destPiece!=null && destPiece.getName().equals("Hole") && srcPiece.getOwner()==destPiece.getOwner()){
            return false;
        }
        boolean isValidDistance = calculateDistance(src, dest) == 1;
        if (srcPiece.getName().equals("Rat")){
            return isValidDistance;
        }
        if (srcPiece.getName().equals("Cat")){
            return isValidDistance && !isRiver(dest);
        }
        if (srcPiece.getName().equals("Dog")){
            return isValidDistance && !isRiver(dest);
        }
        if (srcPiece.getName().equals("Wolf")){
            return isValidDistance && !isRiver(dest);
        }
        if (srcPiece.getName().equals("Leopard")){
            return isValidDistance && !isRiver(dest);
        }
        if (srcPiece.getName().equals("Tiger")){
            return (isValidDistance && !isRiver(dest)) || isValidJump(src, dest);
        }
        if (srcPiece.getName().equals("Lion")){
            return (isValidDistance && !isRiver(dest)) || isValidJump(src, dest);
        }
        if (srcPiece.getName().equals("Elephant")){
            return isValidDistance && !isRiver(dest);
        }
        return false;
    }
    public boolean isValidCapture(ChessboardPoint src,ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (srcPiece == null || destPiece == null){
            return false;
        }
        if (srcPiece.getOwner() == destPiece.getOwner()){
            return false;
        }
        boolean b = calculateDistance(src, dest) == 1;

        if (srcPiece.getName().equals("Elephant")){
            return  b && !isRiver(dest) && destPiece.getRank() != 1;
        }
        if (srcPiece.getName().equals("Lion")){
            return ((b && !isRiver(dest)) || isValidJump(src, dest)) && destPiece.getRank() <= 7;
        }
        if (srcPiece.getName().equals("Tiger")){
            return ((b && !isRiver(dest)) || isValidJump(src, dest)) && destPiece.getRank() <= 6;
        }
        if (srcPiece.getName().equals("Leopard")){
            return b && !isRiver(dest) && destPiece.getRank() <= 5;
        }
        if (srcPiece.getName().equals("Wolf")){
            return b && !isRiver(dest) && destPiece.getRank() <= 4;
        }
        if (srcPiece.getName().equals("Dog")){
            return b && !isRiver(dest) && destPiece.getRank() <= 3;
        }
        if (srcPiece.getName().equals("Cat")){
            return b && !isRiver(dest) && destPiece.getRank() <= 2;
        }
        if (srcPiece.getName().equals("Rat")){
            return b && (destPiece.getRank() <= 1 || destPiece.getRank() == 8) && !(isRiver(src) && !isRiver(dest));
        }
        if (destPiece.getName().equals("Trap")){
            getChessPieceAt(src).setRank(0);
            return b;
        }

        return false;
    }
    private boolean isRiver(ChessboardPoint point){
        return point.getRow() >= 3 && point.getRow() <= 5 &&
                (point.getCol() == 1 || point.getCol() == 2 || point.getCol() == 4 || point.getCol() == 5);
    }
    public boolean isValidJump(ChessboardPoint src, ChessboardPoint dest) {
        if ((src.getRow() == 2 && src.getCol() == 1 && dest.getRow() == 6 && dest.getCol() == 1)
                || (src.getRow() == 6 && src.getCol() == 1 && dest.getRow() == 2 && dest.getCol() == 1)) {
            return getChessPieceAt(new ChessboardPoint(3, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 1)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 2 && dest.getRow() == 6 && dest.getCol() == 2)
                || (src.getRow() == 6 && src.getCol() == 2 && dest.getRow() == 2 && dest.getCol() == 2)) {
            return getChessPieceAt(new ChessboardPoint(3, 2)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 2)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 2)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 4 && dest.getRow() == 6 && dest.getCol() == 4)
                || (src.getRow() == 6 && src.getCol() == 4 && dest.getRow() == 2 && dest.getCol() == 4)) {
            return getChessPieceAt(new ChessboardPoint(3, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 4)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 5 && dest.getRow() == 6 && dest.getCol() == 5)
                || (src.getRow() == 6 && src.getCol() == 5 && dest.getRow() == 2 && dest.getCol() == 5)) {
            return getChessPieceAt(new ChessboardPoint(3, 5)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 5)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 5)) == null;
        }
        if ((src.getRow() == 3 && src.getCol() == 0 && dest.getRow() == 3 && dest.getCol() == 3)
                || (src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 0)) {
            return getChessPieceAt(new ChessboardPoint(3, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(3, 2)) == null;
        }
        if ((src.getRow() == 3 && src.getCol() == 6 && dest.getRow() == 3 && dest.getCol() == 3)
                || (src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 6)) {
            return getChessPieceAt(new ChessboardPoint(3, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(3, 5)) == null;
        }
        if ((src.getRow() == 4 && src.getCol() == 0 && dest.getRow() == 4 && dest.getCol() == 3)
                || (src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 0)) {
            return getChessPieceAt(new ChessboardPoint(4, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 2)) == null;
        }
        if ((src.getRow() == 4 && src.getCol() == 6 && dest.getRow() == 4 && dest.getCol() == 3)
                || (src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 6)) {
            return getChessPieceAt(new ChessboardPoint(4, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 5)) == null;
        }
        if ((src.getRow() == 5 && src.getCol() == 0 && dest.getRow() == 5 && dest.getCol() == 3)
                || (src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 0)) {
            return getChessPieceAt(new ChessboardPoint(5, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 2)) == null;
        }
        if ((src.getRow() == 5 && src.getCol() == 6 && dest.getRow() == 5 && dest.getCol() == 3)
                || (src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 6)) {
            return getChessPieceAt(new ChessboardPoint(5, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 5)) == null;
        }
        return false;
    }
    public int getAllChessPieces(PlayerColor currentPlayer) {
        int a = 0;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                ChessPiece piece = getChessPieceAt(point);
                if (piece != null && piece.getRank()!= 0) {
                    piece.getOwner().equals(currentPlayer);
                    a++;
                }
            }
        }
        return a;
    }
    public static Chessboard getInstance(){
        Chessboard chessboard = new Chessboard();
        chessboard.initGrid();
        chessboard.initPieces();
        return chessboard;
    }
    public  Chessboard getChessboard() {
        Chessboard chessboard = new Chessboard();
        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);
            chessboard.moveChessPiece(step.src, step.dest);
        }
        return chessboard;
    }
    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }
    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }
    public void removeAllPiece(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                removeChessPiece(new ChessboardPoint(i,j));
            }
        }
    }
    public ChessPiece getChessPiece(PlayerColor playerColor, String name){
        ChessPiece chessPiece = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
            if (getChessPieceAt(new ChessboardPoint(i, j)).getOwner() == playerColor && getChessPieceAt(new ChessboardPoint(i, j)).getName().equals(name)){
                chessPiece = getChessPieceAt(new ChessboardPoint(i,j));
                }
            }
        }
        return chessPiece;
    }
}