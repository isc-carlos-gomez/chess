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
public final class StrategicPlayer {

    private final PlayerStrategy strategy;
    private final Board board;
    private final Player opponent;

    /**
     * @param strategy
     * @param board
     * @param opponent
     */
    public StrategicPlayer(final PlayerStrategy strategy, final Board board, final Player opponent) {
        this.strategy = strategy;
        this.board = board;
        this.opponent = opponent;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.Player#youtTurn()
     */
    public GameState yourTurn() {
        // boolean boardUpdated = false;
        // do {
        // Move move = null;
        // try {
        // move = this.strategy.makeMove();
        // } catch (final MoveNotMadeException e) {
        // final PlayerAction action = this.strategy.pickTurnAction();
        // switch (action) {
        // case DRAW_OFFER:
        // final boolean drawAccepted = this.opponent.acceptDraw();
        // return GameStatus.DRAW_BY_AGREEMENT;
        // default:
        // throw new IllegalArgumentException("Turn action not supported: " + action);
        // }
        // }
        // boardUpdated = this.board.update(move);
        // if (!boardUpdated) {
        // this.strategy.moveIllegal(move);
        // }
        // } while (!boardUpdated);
        return GameState.RUNNING;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.Player#acceptDraw()
     */
    public boolean acceptDraw() {
        // TODO Auto-generated method stub
        return false;
    }

}
