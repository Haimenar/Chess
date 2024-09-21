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
}
