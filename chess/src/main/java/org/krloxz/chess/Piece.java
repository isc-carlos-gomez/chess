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

/**
 * @author Carlos Gomez
 */
public abstract class Piece {

    private final Color color;

    /**
     *
     */
    public Piece(final Color color) {
        this.color = color;
    }

    /**
     * @return
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @param move
     * @param board
     * @return
     */
    public boolean isLegalMove(final Move move, final Board board) {
        final Square targetSquare = board.getSquare(move.getTarget());
        if (targetSquare.isOccupied()
                && targetSquare.getPiece().getColor() == getColor()) {
            return false;
        }
        // TODO: Prevent on knight
        final List<Square> occupiedSquares = board.findSquares(new OccupiedAndBetweenSquaresSpecification());
        if (!occupiedSquares.isEmpty()) {
            return false;
        }
        return confirmLegalMove(move, board);
    }

    /**
     * @param move
     * @param board
     * @return
     */
    protected abstract boolean confirmLegalMove(Move move, Board board);

    /**
     *
     */
    protected void captured() {
        // TODO Auto-generated method stub

    }

}
