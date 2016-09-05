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
package org.krloxz.chess.recorded;

import java.util.Iterator;
import java.util.List;

import org.krloxz.chess.Board;
import org.krloxz.chess.GameEnding;
import org.krloxz.chess.GameState;
import org.krloxz.chess.Move;
import org.krloxz.chess.PlayerAction;
import org.krloxz.chess.PlayerStrategy;
import org.krloxz.chess.Square;
import org.krloxz.chess.SquareSpecification;
import org.krloxz.chess.WithPieceTypeThatReachesSquareSpecification;

/**
 * @author Carlos Gomez
 */
public class RecordedGameStrategy implements PlayerStrategy {

    final Iterator<String> moves;
    final SanParser moveParser;

    /**
     * @param moves
     * @param moveParser
     */
    public RecordedGameStrategy(final Iterator<String> moves, final SanParser moveParser) {
        this.moves = moves;
        this.moveParser = moveParser;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerStrategy#nextAction(org.krloxz.chess.Board)
     */
    @Override
    public PlayerAction nextAction(final Board board) {
        final String move = this.moves.next();
        final SanMove sanMove = this.moveParser.parse(move);
        if (sanMove.isGameEnding()) {
            return new GameEnding(sanMove.getGameEnding());
        }
        final Square sourceSquare = findSourceSquare(board, sanMove);
        return new Move(sourceSquare, sanMove.getSquare(), sanMove.getPiecePromotedToOrNull());
    }

    private Square findSourceSquare(final Board board, final SanMove sanMove) {
        if (sanMove.isFullySpecified()) {
            return new Square(sanMove.getFile(), sanMove.getRank());
        }
        final SquareSpecification specification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(sanMove.getPiece())
                .targetSquare(sanMove.getSquare())
                .file(sanMove.getFile())
                .rank(sanMove.getRank())
                .build();
        final List<Square> squares = board.findSquares(specification);
        if (squares.size() != 1) {
            throw new IllegalArgumentException("The move '" + sanMove + "' is either illegal or ambiguous");
        }
        return squares.get(0);
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
