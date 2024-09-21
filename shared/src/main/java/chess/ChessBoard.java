package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares = new ChessPiece[8][8];

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                this.squares[x][y] = null;
            }
        }

        ChessPiece.PieceType[] typeArray = {ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KING, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK};
        int x = 1;
        int y = 1;
        //White Pieces
        for (ChessPiece.PieceType type : typeArray){
            addPiece(new ChessPosition(x, y), new ChessPiece(ChessGame.TeamColor.WHITE, type));
            y++;
        }
        //White Pawns
        x = 2;
        for (int i = 1; i < 9; i++){
            addPiece(new ChessPosition(x, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }
        //Black Pieces
        x = 8;
        y = 1;

        for (ChessPiece.PieceType type : typeArray){
            addPiece(new ChessPosition(x, y), new ChessPiece(ChessGame.TeamColor.BLACK, type));
            y++;
        }
        //Black Pawns
        x = 7;
        for (int i = 1; i < 9; i++){
            addPiece(new ChessPosition(x, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
    }
}
