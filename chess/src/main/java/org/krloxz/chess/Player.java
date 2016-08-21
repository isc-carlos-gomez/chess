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

    private final PlayerStrategy strategy;
    private final Board board;
    private final Player opponent;

    /**
     * @param strategy
     */
    public Player(final PlayerStrategy strategy, final Board board, final Player opponent) {
        this.strategy = strategy;
        this.board = board;
        this.opponent = opponent;
    }

    /**
     * @return
     */
    public PlayerAction yourTurn() {
        final PlayerActionExecutorVisitor actionExecutor = new PlayerActionExecutorVisitor(
                this.board, this.strategy, getOpponent());
        PlayerAction action = null;
        do {
            action = this.strategy.nextAction(this.board.copy());
        } while (!action.accept(actionExecutor));
        return action;
    }

    /**
     * @return
     */
    public boolean acceptDraw() {
        return this.strategy.acceptDraw();
    }

    /**
     * @param state
     */
    public void gameOver(final GameState state) {
        this.strategy.gameOver(state);
    }

    /**
     * @return
     */
    public Player getOpponent() {
        return this.opponent;
    }

}