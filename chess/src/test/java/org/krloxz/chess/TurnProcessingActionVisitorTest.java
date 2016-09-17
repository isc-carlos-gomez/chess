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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link TurnProcessingActionVisitor}.
 *
 * @author Carlos Gomez
 */
public class TurnProcessingActionVisitorTest {

    private TurnProcessingActionVisitor visitor;
    private Board board;
    private PlayerStrategy strategy;
    private Player opponent;
    private Move move;
    private DrawOffer drawOffer;

    @Before
    public void setUp() {
        this.board = mock(Board.class);
        this.strategy = mock(PlayerStrategy.class);
        this.opponent = mock(Player.class);
        this.visitor = new TurnProcessingActionVisitor(this.board, this.strategy, this.opponent);
        this.move = mock(Move.class);
        this.drawOffer = mock(DrawOffer.class);
    }

    @Test
    public void visitMove() {
        // Arrange
        when(this.board.update(this.move)).thenReturn(true);

        // Act
        final boolean result = this.visitor.visit(this.move);

        // Assert
        assertTrue("Move must be accepted", result);
    }

    @Test
    public void visitMoveOnIllegal() {
        // Arrange
        when(this.board.update(this.move)).thenReturn(false);

        // Act
        final boolean result = this.visitor.visit(this.move);

        // Assert
        assertFalse("Move must be rejected", result);
        verify(this.strategy).actionRejected(this.move);
    }

    @Test
    public void visitDrawOffer() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(true);

        // Act
        final boolean result = this.visitor.visit(this.drawOffer);

        // Assert
        assertTrue("Draw must be accepted", result);
    }

    @Test
    public void visitDrawOfferOnRejection() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(false);

        // Act
        final boolean result = this.visitor.visit(this.drawOffer);

        // Assert
        assertFalse("Draw must be rejected", result);
        verify(this.strategy).actionRejected(this.drawOffer);
    }

    @Test(expected = IllegalStateException.class)
    public void visitDrawOfferTwice() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(false);

        // Act
        this.visitor.visit(this.drawOffer);
        this.visitor.visit(this.drawOffer);
    }

    @Test
    public void visitResignation() {
        // Arrange
        final Resignation resignation = mock(Resignation.class);

        // Act
        final boolean result = this.visitor.visit(resignation);

        // Assert
        assertTrue("Resignation must be always accepted", result);
    }

    @Test
    public void visitGameEnding() {
        // Arrange
        final GameEnding gameEnding = mock(GameEnding.class);

        // Act
        final boolean result = this.visitor.visit(gameEnding);

        // Assert
        assertTrue("Game ending must be always accepted", result);
    }

}
