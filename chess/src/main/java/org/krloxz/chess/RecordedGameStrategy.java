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

import java.util.Iterator;
import java.util.List;

/**
 * @author Carlos Gomez
 */
public class RecordedGameStrategy implements PlayerStrategy {

    final Iterator<String> moveIterator;

    /**
     * @param recordedMoves
     */
    public RecordedGameStrategy(final List<String> recordedMoves) {
        this.moveIterator = recordedMoves.iterator();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerStrategy#nextAction(org.krloxz.chess.Board)
     */
    @Override
    public PlayerAction nextAction(final Board board) {
        final String san = this.moveIterator.next();
        final PieceType pieceType = PieceType.fromSanAbbreviation("");
        final Square to = new Square(san);
        final SquareSpecification specification = new WithPieceTypeThatReachesSquareSpecification(
                pieceType, to);
        final List<Square> squares = board.findSquares(specification);
        final Square from = squares.get(0);
        return new Move(from, to);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerStrategy#actionRejected(org.krloxz.chess.PlayerAction)
     */
    @Override
    public void actionRejected(final PlayerAction action) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerStrategy#acceptDraw()
     */
    @Override
    public boolean acceptDraw() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerStrategy#gameOver(org.krloxz.chess.GameState)
     */
    @Override
    public void gameOver(final GameState gameState) {
        // TODO Auto-generated method stub

    }

}
