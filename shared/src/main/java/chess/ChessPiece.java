package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return pieceColor == piece.pieceColor && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (type == PieceType.BISHOP){
            return (diagonalHelper(board, myPosition));
        } else if (type == PieceType.ROOK) {
            return (linearHelper(board, myPosition));
        } else if (type == PieceType.QUEEN) {
            return (queenHelper(board, myPosition));
        } else if (type == PieceType.KING) {
            return (kingHelper(board, myPosition));
        } else if (type == PieceType.KNIGHT) {
            return (knightHelper(board, myPosition));
        } else if (type == PieceType.PAWN) {
            return (pawnHelper(board, myPosition));
        } else {
            throw new RuntimeException("Piece not recognized");
        }
    }
    public ArrayList<ChessMove> diagonalHelper(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[][] direction = {{1,1}, {-1,1}, {1,-1}, {-1,-1}};

        for(int[] dir : direction){
            int x = dir[0];
            int y = dir[1];
            ChessPosition newPosition = new ChessPosition(row + x, col + y);

            while(isValidPosition(newPosition) && board.getPiece(newPosition) == null){
                moves.add(new ChessMove(myPosition, newPosition, null));
                x += dir[0];
                y += dir[1];
                newPosition = new ChessPosition(row + x, col + y);
            }

            if (isValidPosition(newPosition) && isDifferentColor(board, myPosition, newPosition)){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return moves;
    }

    public ArrayList<ChessMove> linearHelper(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[][] direction = {{1,0}, {-1,0}, {0,-1}, {0,1}};

        for(int[] dir : direction){
            int x = dir[0];
            int y = dir[1];
            ChessPosition newPosition = new ChessPosition(row + x, col + y);

            while(isValidPosition(newPosition) && board.getPiece(newPosition) == null){
                moves.add(new ChessMove(myPosition, newPosition, null));
                x += dir[0];
                y += dir[1];
                newPosition = new ChessPosition(row + x, col + y);
            }

            if (isValidPosition(newPosition) && isDifferentColor(board, myPosition, newPosition)){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return moves;
    }


    public ArrayList<ChessMove> pawnHelper(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (getTeamColor() == ChessGame.TeamColor.WHITE){
            //This piece is WHITE
            //Diagonals
            int[][] diagonals = {{1,1}, {1,-1}};

            for(int[] diag : diagonals) {
                int x = diag[0];
                int y = diag[1];
                ChessPosition newPosition = new ChessPosition(row + x, col + y);

                if (isValidPosition(newPosition) && (board.getPiece(newPosition) != null && isDifferentColor(board, myPosition, newPosition))) {
                    //Check conditions for promotion

                    if (row == 7){
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    } else {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }

            //Get forward once move
            ChessPosition newPosition = new ChessPosition(row + 1, col);

            if (isValidPosition(newPosition) && board.getPiece(newPosition) == null) {
                //Check conditions for promotion

                if (row == 7){
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
            //Get double move
            ChessPosition doublePosition = new ChessPosition(row + 2, col);
            ChessPosition singlePosition = new ChessPosition(row + 1, col);

            if (row == 2 && board.getPiece(singlePosition) == null && board.getPiece(doublePosition) == null) {
                moves.add(new ChessMove(myPosition, doublePosition, null));
            }
        } else {
            //This piece is BLACK
            //Diagonals
            int[][] diagonals = {{-1,1}, {-1,-1}};

            for(int[] diag : diagonals) {
                int x = diag[0];
                int y = diag[1];
                ChessPosition newPosition = new ChessPosition(row + x, col + y);

                if (isValidPosition(newPosition) && (board.getPiece(newPosition) != null && isDifferentColor(board, myPosition, newPosition))) {
                    //Check conditions for promotion

                    if (row == 2){
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    } else {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }

            //Get forward once move
            ChessPosition newPosition = new ChessPosition(row - 1, col);

            if (isValidPosition(newPosition) && board.getPiece(newPosition) == null) {
                //Check conditions for promotion

                if (row == 2){
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
            //Get double move
            ChessPosition doublePosition = new ChessPosition(row - 2, col);
            ChessPosition singlePosition = new ChessPosition(row - 1, col);

            if (row == 7 && board.getPiece(singlePosition) == null && board.getPiece(doublePosition) == null) {
                moves.add(new ChessMove(myPosition, doublePosition, null));
            }
        }

        return moves;
    }


    public ArrayList<ChessMove> knightHelper(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[][] spots = {{2,1}, {-2,1}, {2,-1}, {-2,-1}, {1,2}, {-1,2}, {1,-2}, {-1,-2}};

        for(int[] spot : spots){
            int x = spot[0];
            int y = spot[1];
            ChessPosition newPosition = new ChessPosition(row + x, col + y);

            if(isValidPosition(newPosition) && board.getPiece(newPosition) == null){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            if (isValidPosition(newPosition) && (board.getPiece(newPosition) != null && isDifferentColor(board, myPosition, newPosition))){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return moves;
    }

    public ArrayList<ChessMove> queenHelper(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        moves.addAll(linearHelper(board, myPosition));
        moves.addAll(diagonalHelper(board, myPosition));
        return moves;
    }

    public ArrayList<ChessMove> kingHelper(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[][] spots = {{1,1}, {1,-1}, {-1,1}, {-1,-1}, {1,0},{-1,0},{0,1},{0,-1}};

        for(int[] spot : spots){
            int x = spot[0];
            int y = spot[1];
            ChessPosition newPosition = new ChessPosition(row + x, col + y);

            if(isValidPosition(newPosition) && (board.getPiece(newPosition) == null || (board.getPiece(newPosition) != null && isDifferentColor(board, myPosition, newPosition)))){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            if (isValidPosition(newPosition) && (board.getPiece(newPosition) != null && isDifferentColor(board, myPosition, newPosition))){
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return moves;
    }

    public Boolean isValidPosition(ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        return (row >=1 && row <=8 && col >= 1 && col <=8);
    }

    public Boolean isDifferentColor(ChessBoard board, ChessPosition firstPosition, ChessPosition nextPosition){
        ChessPiece piece1 = board.getPiece(firstPosition);
        ChessPiece piece2 = board.getPiece(nextPosition);
        return piece1.getTeamColor() != piece2.getTeamColor();
    }
}
