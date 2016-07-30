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

import static org.mockito.Matchers.any;
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
    private PlayerActionProcessor<GameState> actionProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        this.lightPlayer = mock(Player.class);
        this.darkPlayer = mock(Player.class);
        this.actionProcessor = mock(PlayerActionProcessor.class);
        this.game = new Game(this.lightPlayer, this.darkPlayer, this.actionProcessor);
    }

    @Test
    public void playLoopPlayerTurnsTillGameNotRunning() {
        // Arrange
        final PlayerAction anyAction = mock(PlayerAction.class);
        final PlayerAction finalAction = mock(PlayerAction.class);
        when(this.lightPlayer.yourTurn()).thenReturn(anyAction, finalAction);
        when(this.darkPlayer.yourTurn()).thenReturn(anyAction);
        when(this.actionProcessor.process(anyAction)).thenReturn(GameState.RUNNING);
        when(this.actionProcessor.process(finalAction)).thenReturn(GameState.DRAW_BY_AGREEMENT);

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
        when(this.actionProcessor.process(any())).thenReturn(state);

        // Act
        this.game.play();

        // Assert
        verify(this.lightPlayer).gameOver(state);
        verify(this.darkPlayer).gameOver(state);
    }

}