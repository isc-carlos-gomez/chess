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
public class Player {

    private final Color color;
    private final PlayerStrategy strategy;
    private final Player opponent;
    private final Board board;
    private final BoardBroker boardBroker;
    private boolean drawAlreadyOffered;

    /**
     * @param color
     * @param strategy
     * @param boardBroker
     */
    public Player(final Color color, final PlayerStrategy strategy, final Player opponent, final Board board,
            final BoardBroker boardBroker) {
        this.color = color;
        this.strategy = strategy;
        this.opponent = opponent;
        this.board = board;
        this.boardBroker = boardBroker;
        this.drawAlreadyOffered = false;
    }

    /**
     * Play a turn.
     */
    public void play() {
        this.strategy.play(this.board)
                .execute(this);
    }

    /**
     * @param movement
     */
    public void move(final Movement movement) {
        if (this.boardBroker.updateBoard(movement)) {
            this.drawAlreadyOffered = false;
            this.opponent.play();
        } else {
            this.strategy.moveRejected(movement);
            play();
        }
    }

    /**
     *
     */
    public void offerDraw() {
        if (this.drawAlreadyOffered) {
            throw new IllegalStateException("Too much draw offers, only once per turn is allowed");
        }
        if (this.opponent.acceptDraw()) {
            this.strategy.gameOver(GameResult.DRAW_BY_AGREEMENT);
        } else {
            this.drawAlreadyOffered = true;
            this.strategy.drawOfferRejected();
        }
    }

    /**
     * @return
     */
    public boolean acceptDraw() {
        return this.strategy.acceptDraw();
    }

    /**
     *
     */
    public void resign() {
        GameResult result = GameResult.WHITE_RESIGNATION;
        if (this.color == Color.BLACK) {
            result = GameResult.BLACK_RESIGNATION;
        }
        endGame(result);
    }

    /**
     * Ends the current game notifying the game result to the strategy and the opponent.
     * 
     * @param result
     *        the game result.
     */
    public void endGame(final GameResult result) {
        this.strategy.gameOver(result);
        this.opponent.gameOver(result);
    }

    /**
     * @param result
     */
    public void gameOver(final GameResult result) {
        // TODO Auto-generated method stub

    }

}