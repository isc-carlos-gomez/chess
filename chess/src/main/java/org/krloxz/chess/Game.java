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
    private final GameStateResolver stateResolver;

    /**
     * @param stateResolver
     * @param lightPlayer
     * @param actionExecutor
     */
    public Game(final Player lightPlayer, final GameStateResolver stateResolver) {
        this.lightPlayer = lightPlayer;
        this.stateResolver = stateResolver;
    }

    /**
     *
     */
    public void play() {
        GameState gameState = null;
        Player player = this.lightPlayer;
        do {
            player.yourTurn();
            player = player.getOpponent();
            gameState = this.stateResolver.resolve();
        } while (gameState == GameState.RUNNING);
        this.lightPlayer.gameOver(gameState);
        this.lightPlayer.getOpponent().gameOver(gameState);
    }

}
