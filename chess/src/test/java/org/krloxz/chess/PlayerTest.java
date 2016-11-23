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
import org.krloxz.chess.player.Command;

/**
 * Unit tests {@link Player}.
 *
 * @author Carlos Gomez
 */
public class PlayerTest {

    private Player player;
    private PlayerStrategy strategy;
    private Player opponent;
    private Board board;
    private MovementExecutor movementExecutor;

    @Before
    public void setUp() {
        this.strategy = mock(PlayerStrategy.class);
        this.opponent = mock(Player.class);
        this.board = mock(Board.class);
        this.movementExecutor = mock(MovementExecutor.class);
        this.player = Player.builder()
                .strategy(this.strategy)
                .color(Color.WHITE)
                .opponent(this.opponent)
                .board(this.board)
                .movementExecutor(this.movementExecutor)
                .build();
    }

    @Test
    public void play() {
        // Arrange
        final Command command = mock(Command.class);
        when(this.strategy.play(this.board))
                .thenReturn(command);

        // Act
        this.player.play();

        // Assert
        verify(command).execute(this.player);
    }

    @Test
    public void move() {
        // Arrange
        final Movement movement = mock(Movement.class);
        when(this.movementExecutor.execute(movement))
                .thenReturn(true);

        // Act
        this.player.move(movement);

        // Assert
        verify(this.opponent).play();
    }

    @Test
    public void moveOnIllegalMovement() {
        // Arrange
        final Movement movement = mock(Movement.class);
        final Command command = mock(Command.class);
        when(this.movementExecutor.execute(movement))
                .thenReturn(false);
        when(this.strategy.play(this.board))
                .thenReturn(command);

        // Act
        this.player.move(movement);

        // Assert
        verify(this.strategy).moveRejected(movement);
        verify(command).execute(this.player);
    }

    @Test
    public void offerDraw() {
        // Arrange
        when(this.opponent.acceptDraw())
                .thenReturn(true);

        // Act
        this.player.offerDraw();

        // Assert
        verify(this.strategy).gameOver(GameResult.DRAW_BY_AGREEMENT);
        verify(this.opponent).gameOver(GameResult.DRAW_BY_AGREEMENT);
    }

    @Test
    public void offerDrawOnDrawRejected() {
        // Arrange
        when(this.opponent.acceptDraw())
                .thenReturn(false);

        // Act
        this.player.offerDraw();

        // Assert
        verify(this.strategy).drawOfferRejected();
    }

    @Test(expected = IllegalStateException.class)
    public void offerDrawOnConsecutiveOffers() {
        // Arrange
        when(this.opponent.acceptDraw())
                .thenReturn(false);

        // Act
        this.player.offerDraw();
        this.player.offerDraw();
    }

    @Test
    public void offerDrawOnOffersAlternatedWithMovements() {
        // Arrange
        final Movement movement = mock(Movement.class);
        when(this.opponent.acceptDraw())
                .thenReturn(false);
        when(this.movementExecutor.execute(movement))
                .thenReturn(true);

        // Act
        this.player.offerDraw();
        this.player.move(movement);
        this.player.offerDraw();

        // Assert
        verify(this.strategy, times(2)).drawOfferRejected();
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
    public void resignOnWhitePlayer() {
        // Act
        this.player.resign();

        // Assert
        verify(this.strategy).gameOver(GameResult.WHITE_RESIGNATION);
        verify(this.opponent).gameOver(GameResult.WHITE_RESIGNATION);
    }

    @Test
    public void resignOnBlackPlayer() {
        // Arrange
        final Player whitePlayer = mock(Player.class);
        final Player blackPlayer = Player.builder()
                .strategy(this.strategy)
                .color(Color.BLACK)
                .board(this.board)
                .opponent(whitePlayer)
                .movementExecutor(this.movementExecutor)
                .build();

        // Act
        blackPlayer.resign();

        // Assert
        verify(this.strategy).gameOver(GameResult.BLACK_RESIGNATION);
        verify(whitePlayer).gameOver(GameResult.BLACK_RESIGNATION);
    }

    @Test
    public void endGame() {
        // Arrange
        final GameResult result = GameResult.DRAW_BY_AGREEMENT;

        // Act
        this.player.endGame(result);

        // Assert
        verify(this.strategy).gameOver(result);
        verify(this.opponent).gameOver(result);
    }

    @Test
    public void gameOver() {
        // Arrange
        final GameResult result = GameResult.DRAW_BY_AGREEMENT;

        // Act
        this.player.gameOver(result);

        // Assert
        verify(this.strategy).gameOver(result);
    }

}
