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
public class Game {

    private final Player lightPlayer;
    private final Player darkPlayer;
    private final PlayerActionProcessor<GameState> actionProcessor;

    /**
     * @param actionProcessor
     * @param lightPlayer
     */
    public Game(final Player lightPlayer, final Player darkPlayer,
            final PlayerActionProcessor<GameState> actionProcessor) {
        this.lightPlayer = lightPlayer;
        this.darkPlayer = darkPlayer;
        this.actionProcessor = actionProcessor;
    }

    /**
     *
     */
    public void play() {
        GameState gameState = null;
        PlayerAction action = null;
        Player playerInTurn = this.lightPlayer;
        do {
            action = playerInTurn.yourTurn();
            playerInTurn = getOpponent(playerInTurn);
            gameState = this.actionProcessor.process(action);
        } while (gameState == GameState.RUNNING);
        this.lightPlayer.gameOver(gameState);
        this.darkPlayer.gameOver(gameState);
    }

    /**
     * @param playerInTurn
     * @return
     */
    private Player getOpponent(final Player playerInTurn) {
        if (playerInTurn.equals(this.lightPlayer)) {
            return this.darkPlayer;
        }
        return this.lightPlayer;
    }

}
