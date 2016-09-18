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
        final boolean isOneSquareMove = move.getTarget().getY() - move.getSource().getY() == 1;
        final boolean isInitialMove = move.getSource().getY() == 1;
        final boolean isTwoSquareMove = move.getTarget().getY() - move.getSource().getY() == 2;
        final boolean isLegalTwoSquareMove = isInitialMove && isTwoSquareMove;
        final boolean isCapture = Math.abs(move.getSource().getX() - move.getSource().getX()) == 1;
        final Square targetSquare = board.getSquare(move.getTarget());
        final boolean isLegalCapture = isCapture && targetSquare.isOccupied();
        final boolean isLegalMove = isOneSquareMove || isLegalTwoSquareMove || isLegalCapture;
        if (move instanceof Promotion) {
            return isLegalMove && move.getTarget().getY() == 7;
        }
        return isLegalMove;
    }

}
