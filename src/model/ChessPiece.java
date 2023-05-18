package model;


public class ChessPiece {
    private PlayerColor owner;
    private String name;
    private int rank;
    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }
    public boolean canCapture(ChessPiece target) {
        if (this.owner != target.owner) {
            if (this.rank == 1 && target.rank == 8) {
                return true;
            } else if (this.rank == 8 && target.rank == 1) {
                return false;
            } else {
                return this.rank >= target.rank;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }


    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
}
