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

/**
 * @author Carlos Gomez
 */
public class Pawn extends Piece {

    /**
     * @param color
     */
    public Pawn(final Color color) {
        super(color);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.Piece#confirmLegalMove(org.krloxz.chess.Move, org.krloxz.chess.Board)
     */
    @Override
    protected boolean confirmLegalMove(final Move move, final Board board) {
        if (move instanceof Promotion) {
            return isPawnMove(move, board) && move.getTarget().getY() == getPromotionRank();
        }
        return isPawnMove(move, board);
    }

    private boolean isPawnMove(final Move move, final Board board) {
        if (move.getSource().getX() == move.getTarget().getX()) {
            return isOneSquareMove(move) || isTwoSquareMove(move);
        }
        return isCapture(move, board);
    }

    private boolean isOneSquareMove(final Move move) {
        return move.getTarget().getY() - move.getSource().getY() == getOrientation();
    }

    private boolean isTwoSquareMove(final Move move) {
        final boolean isInitialMove = move.getSource().getY() == getInitialRank();
        final boolean isTwoSquareMove = move.getTarget().getY() - move.getSource().getY() == getOrientation() * 2;
        return isInitialMove && isTwoSquareMove;
    }

    private boolean isCapture(final Move move, final Board board) {
        final boolean isForwardMove = move.getTarget().getY() - move.getSource().getY() == getOrientation();
        final boolean isCapture = Math.abs(move.getSource().getX() - move.getTarget().getX()) == 1;
        final Square targetSquare = board.getSquare(move.getTarget());
        return isForwardMove && isCapture && targetSquare.isOccupied();
    }

    private int getOrientation() {
        return getColor() == Color.LIGHT ? 1 : -1;
    }

    private int getInitialRank() {
        return getColor() == Color.LIGHT ? 1 : 6;
    }

    private int getPromotionRank() {
        return getColor() == Color.LIGHT ? 7 : 0;
    }

}
