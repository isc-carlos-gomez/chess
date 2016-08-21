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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Unit tests {@link Player}.
 *
 * @author Carlos Gomez
 */
public class PlayerTest {

    private Player player;
    private PlayerStrategy strategy;
    private Board board;
    private Player opponent;

    @Before
    public void setUp() {
        this.strategy = mock(PlayerStrategy.class);
        this.board = mock(Board.class);
        this.opponent = mock(Player.class);
        this.player = new Player(this.strategy, this.board, this.opponent);
    }

    @Test
    public void yourTurn() {
        // Arrange
        final PlayerAction action = mock(PlayerAction.class);
        final Board boardCopy = mock(Board.class);

        when(this.board.copy()).thenReturn(boardCopy);
        when(this.strategy.nextAction(boardCopy)).thenReturn(action);
        when(action.accept(any())).thenReturn(true);

        // Act
        final PlayerAction result = this.player.yourTurn();

        // Assert
        assertEquals(action, result);
    }

    @Test
    public void yourTurnOnActionRejected() {
        // Arrange
        final PlayerAction action = mock(PlayerAction.class);

        when(this.strategy.nextAction(any())).thenReturn(action);
        when(action.accept(notNull(PlayerActionExecutorVisitor.class))).thenReturn(false, true);

        // Act
        this.player.yourTurn();

        // Assert
        final ArgumentCaptor<PlayerActionExecutorVisitor> visitorCaptor = ArgumentCaptor
                .forClass(PlayerActionExecutorVisitor.class);
        verify(action, times(2)).accept(visitorCaptor.capture());
        assertSame("The same visitor instance must be used along a turn", visitorCaptor.getAllValues().get(0),
                visitorCaptor.getAllValues().get(1));
    }

    @Test
    public void acceptDraw() {
        // Arrange
        final boolean acceptDraw = true;
        when(this.strategy.acceptDraw()).thenReturn(acceptDraw);

        // Act
        final boolean result = this.player.acceptDraw();

        // Assert
        assertEquals(acceptDraw, result);
    }

    @Test
    public void gameOver() {
        // Arrange
        final GameState gameState = GameState.DRAW_BY_AGREEMENT;

        // Act
        this.player.gameOver(gameState);

        // Assert
        verify(this.strategy).gameOver(gameState);
    }

    @Test
    public void getOpponent() {
        // Act
        final Player result = this.player.getOpponent();

        // Assert
        assertEquals(this.opponent, result);
    }

}
