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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link StrategicPlayer}.
 *
 * @author Carlos Gomez
 */
public class StrategicPlayerTest {

    private StrategicPlayer player;
    private PlayerStrategy strategy;
    private Board board;
    private Player opponent;

    @Before
    public void setUp() {
        this.strategy = mock(PlayerStrategy.class);
        this.board = mock(Board.class);
        this.opponent = mock(Player.class);
        this.player = new StrategicPlayer(this.strategy, this.board, this.opponent);
    }

    @Test
    public void yourTurn() throws MoveNotMadeException {
        // Arrange
        final Move move = mock(Move.class);
        when(this.strategy.makeMove()).thenReturn(move);
        when(this.board.update(move)).thenReturn(true);

        // Act
        final GameStatus status = this.player.yourTurn();

        // Assert
        assertEquals(GameStatus.PLAYING, status);
    }

    @Test
    public void yourTurnOnIllegalMove() throws MoveNotMadeException {
        // Arrange
        final Move move = mock(Move.class);
        when(this.strategy.makeMove()).thenReturn(move);
        when(this.board.update(move)).thenReturn(false, false, true);

        // Act
        this.player.yourTurn();

        // Assert
        verify(this.strategy, times(2)).moveIllegal(move);
    }

    @Test
    public void yourTurnOnDrawOfferAccepted() throws MoveNotMadeException {
        // Arrange
        final TurnAction action = TurnAction.DRAW_OFFER;
        when(this.strategy.makeMove()).thenThrow(new MoveNotMadeException());
        when(this.strategy.pickTurnAction()).thenReturn(action);
        when(this.opponent.acceptDraw()).thenReturn(true);

        // Act
        final GameStatus gameStatus = this.player.yourTurn();

        // Assert
        assertEquals(GameStatus.DRAW_BY_AGREEMENT, gameStatus);
    }

}
