/*
 * Copyright 2016 Carlos Gomez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.krloxz.chess;

import java.util.List;
import java.util.Optional;

/**
 * @author Carlos Gomez
 */
public class King extends Piece {

    protected static final Square INITIAL_POSITION = new Square("e1");
    protected static final Square KINGSIDE_ROOK_POSITION = new Square("h1");
    private static final int XDELTA_ON_CASTLING = 2;

    private Square position;

    /**
     * @param color
     */
    public King(final Color color) {
        super(color);
        this.position = INITIAL_POSITION;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.Piece#confirmLegalMove(org.krloxz.chess.Move, org.krloxz.chess.Board)
     */
    @Override
    protected boolean confirmLegalMove(final BasicMovement move, final Board board) {
        final int xDelta = Math.abs(move.getTarget().getX() - move.getSource().getX());
        final int yDelta = Math.abs(move.getTarget().getY() - move.getSource().getY());
        if (xDelta == XDELTA_ON_CASTLING) {
            return isLegalCastling(move, board);
        }
        return xDelta <= 1 && yDelta <= 1;
    }

    private boolean isLegalCastling(final BasicMovement move, final Board board) {
        final List<Square> squares = board.findSquares(new OccupiedAndBetweenSquaresSpecification());
        if (squares.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public Square getPosition() {
        return this.position;
    }

    /**
     * @param board
     * @param string
     * @return
     */
    public boolean move(final Square targetSquare, final Board board) {
        if (isCastlingMove(targetSquare)) {
            return doCastle(targetSquare, board);
        }
        return doMove(targetSquare, board);
    }

    private boolean isCastlingMove(final Square targetSquare) {
        final int xDelta = Math.abs(targetSquare.getX() - this.position.getX());
        return xDelta == XDELTA_ON_CASTLING;
    }

    private boolean doCastle(final Square targetSquare, final Board board) {
        final Optional<Rook> optional = board.findPiece(
                new PieceInSquareSpecification(Rook.class, KINGSIDE_ROOK_POSITION));
        if (optional.isPresent()) {
            return true;
            // final Rook rook = optional.get();
            // if (isLegalCastling(targetSquare, board) && rook.castle()) {
            // this.position = targetSquare;
            // return true;
            // }
        }
        return false;
    }

    private boolean isLegalCastling(final Square targetSquare, final Board board) {
        return true;
    }

    private boolean doMove(final Square targetSquare, final Board board) {
        if (isLegalMove(targetSquare)) {
            final Optional<Piece> optional = board.findPiece(new InSquareSpecification(targetSquare));
            if (optional.isPresent()) {
                final Piece piece = optional.get();
                if (piece.getColor() == getColor()) {
                    return false;
                } else {
                    piece.captured();
                }
            }
            this.position = targetSquare;
            return true;
        }
        return false;
    }

    private boolean isLegalMove(final Square targetSquare) {
        final int xDelta = Math.abs(targetSquare.getX() - this.position.getX());
        final int yDelta = Math.abs(targetSquare.getY() - this.position.getY());
        return xDelta <= 1 && yDelta <= 1;
    }

}
