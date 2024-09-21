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
