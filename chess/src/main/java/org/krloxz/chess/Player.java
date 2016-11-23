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

import org.apache.commons.lang3.Validate;
import org.krloxz.chess.board.BoardAnalyzer;

/**
 * Encapsulates a chess player that uses a strategy to determine it's behavior.
 *
 * @author Carlos Gomez
 */
public class Player {

    private final PlayerStrategy strategy;
    private final Color color;
    private final Board board;
    private final Player opponent;
    private final MovementExecutor movementExecutor;
    private final BoardAnalyzer boardAnalyzer;
    private boolean drawAlreadyOffered;

    private Player(final Builder builder) {
        this.strategy = builder.strategy;
        this.color = builder.color;
        this.board = builder.board;
        this.opponent = builder.opponent;
        this.movementExecutor = builder.movementExecutor;
        this.boardAnalyzer = Validate.notNull(builder.boardAnalyzer);
        this.drawAlreadyOffered = false;
    }

    /**
     * Plays a turn.
     */
    public void play() {
        this.strategy.play(this.board)
                .execute(this);
    }

    /**
     * Executes a movement and evaluates the result, if a checkmate is identified the game is ended, otherwise the
     * opponent is notified to continue the game.
     *
     * @param movement
     *        movement to execute.
     */
    public void move(final Movement movement) {
        if (this.movementExecutor.execute(movement)) {
            this.drawAlreadyOffered = false;
            if (this.boardAnalyzer.isKingCheckmated(this.color.getOpposite())) {
                GameResult result = GameResult.WHITE_CHECKMATE;
                if (this.color == Color.BLACK) {
                    result = GameResult.BLACK_CHECKMATE;
                }
                endGame(result);
            } else {
                this.opponent.play();
            }
        } else {
            this.strategy.moveRejected(movement);
            play();
        }
    }

    /**
     * Offers to the opponent to end the game as {@link GameResult#DRAW_BY_AGREEMENT} and, if accepted, ends the game.
     */
    public void offerDraw() {
        if (this.drawAlreadyOffered) {
            throw new IllegalStateException("Too much draw offers, only once per turn is allowed");
        }
        if (this.opponent.acceptDraw()) {
            endGame(GameResult.DRAW_BY_AGREEMENT);
        } else {
            this.drawAlreadyOffered = true;
            this.strategy.drawOfferRejected();
        }
    }

    /**
     * Determines if the player accepts to end the game as {@link GameResult#DRAW_BY_AGREEMENT}.
     *
     * @return whether the player accepted to end the game or not.
     */
    public boolean acceptDraw() {
        return this.strategy.acceptDraw();
    }

    /**
     * Ends the current game reporting player resignation.
     */
    public void resign() {
        GameResult result = GameResult.WHITE_RESIGNATION;
        if (this.color == Color.BLACK) {
            result = GameResult.BLACK_RESIGNATION;
        }
        endGame(result);
    }

    /**
     * Ends the current game reporting the given result.
     *
     * @param result
     *        the game result.
     */
    public void endGame(final GameResult result) {
        this.strategy.gameOver(result);
        this.opponent.gameOver(result);
    }

    /**
     * Processes the end of a game reported by somebody else.
     *
     * @param result
     *        the game result.
     */
    public void gameOver(final GameResult result) {
        this.strategy.gameOver(result);
    }

    /**
     * Creates builder to build {@link Player}.
     *
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link Player}.
     */
    public static final class Builder {

        private PlayerStrategy strategy;
        private Color color;
        private Board board;
        private Player opponent;
        private MovementExecutor movementExecutor;
        private BoardAnalyzer boardAnalyzer;

        private Builder() {
        }

        public Builder strategy(final PlayerStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder color(final Color color) {
            this.color = color;
            return this;
        }

        public Builder board(final Board board) {
            this.board = board;
            return this;
        }

        public Builder opponent(final Player opponent) {
            this.opponent = opponent;
            return this;
        }

        public Builder movementExecutor(final MovementExecutor movementExecutor) {
            this.movementExecutor = movementExecutor;
            return this;
        }

        public Builder boardAnalyzer(final BoardAnalyzer boardAnalyzer) {
            this.boardAnalyzer = boardAnalyzer;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

}