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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

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
    public void yourTurnUpdatesBoard() {
        // Arrange
        final Move move = mock(Move.class);
        when(this.strategy.createMove()).thenReturn(move);

        // Act
        this.player.youtTurn();

        // Assert
        verify(this.board).update(move);
    }

    @Test
    public void yourTurnYieldsToOpponent() {
        // Act
        this.player.youtTurn();

        // Assert
        verify(this.opponent).youtTurn();
    }

}
