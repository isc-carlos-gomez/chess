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
import java.util.Optional;

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
        final Optional<GameEnding> optionalEnding = this.moveParser.getGameEnding(move);
        if (optionalEnding.isPresent()) {
            return optionalEnding.get();
        }
        final Square targetSquare = this.moveParser.getTargetSquare(move);
        final Square sourceSquare = findSourceSquare(board, move, targetSquare);
        return new Move(sourceSquare, targetSquare);
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

    private Square findSourceSquare(final Board board, final String move, final Square to) {
        final Optional<Square> optionalSquare = this.moveParser.getSourceSquare(move);
        if (optionalSquare.isPresent()) {
            return optionalSquare.get();
        }
        final PieceType pieceType = this.moveParser.getPieceType(move);
        final String file = this.moveParser.getFile(move);
        final String rank = this.moveParser.getRank(move);
        final SquareSpecification specification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(pieceType)
                .targetSquare(to)
                .file(file)
                .rank(rank)
                .build();
        final List<Square> squares = board.findSquares(specification);
        if (squares.size() != 1) {
            throw new IllegalArgumentException("The move '" + move + "' is illegal or ambiguous");
        }
        return squares.get(0);
    }

}
