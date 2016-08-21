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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link Game}.
 *
 * @author Carlos Gomez
 */
public class GameTest {

    private Game game;
    private Player lightPlayer;
    private Player darkPlayer;
    private GameStateResolver stateResolver;

    @Before
    public void setUp() {
        this.lightPlayer = mock(Player.class);
        this.darkPlayer = mock(Player.class);
        this.stateResolver = mock(GameStateResolver.class);
        this.game = new Game(this.lightPlayer, this.stateResolver);
    }

    @Test
    public void playLoopPlayerTurnsTillGameNotRunning() {
        // Arrange
        when(this.lightPlayer.getOpponent()).thenReturn(this.darkPlayer);
        when(this.darkPlayer.getOpponent()).thenReturn(this.lightPlayer);
        when(this.stateResolver.resolve()).thenReturn(GameState.RUNNING, GameState.RUNNING,
                GameState.DRAW_BY_AGREEMENT);

        // Act
        this.game.play();

        // Assert
        verify(this.lightPlayer, times(2)).yourTurn();
        verify(this.darkPlayer).yourTurn();
    }

    @Test
    public void playNotifyGameOver() {
        // Arrange
        final GameState state = GameState.DRAW_BY_AGREEMENT;
        when(this.stateResolver.resolve()).thenReturn(state);
        when(this.lightPlayer.getOpponent()).thenReturn(this.darkPlayer);

        // Act
        this.game.play();

        // Assert
        verify(this.lightPlayer).gameOver(state);
        verify(this.darkPlayer).gameOver(state);
    }

}