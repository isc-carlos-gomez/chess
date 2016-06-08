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
     * @param board
     * @param opponent
     */
    public Player(final PlayerStrategy strategy, final Board board, final Player opponent) {
        this.strategy = strategy;
        this.board = board;
        this.opponent = opponent;
    }

    /**
     *
     */
    public void youtTurn() {
        final Move move = this.strategy.createMove();
        this.board.update(move);
        this.opponent.youtTurn();
    }

}
