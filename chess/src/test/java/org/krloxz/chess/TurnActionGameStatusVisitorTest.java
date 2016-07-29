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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests {@link TurnActionGameStatusVisitor}.
 *
 * @author Carlos Gomez
 */
public class TurnActionGameStatusVisitorTest {

    private TurnActionGameStatusVisitor visitor;
    private PlayerStrategy strategy;
    private Player opponent;
    private DrawOfferAction drawOfferAction;

    @Before
    public void setUp() {
        this.strategy = mock(PlayerStrategy.class);
        this.opponent = mock(Player.class);
        this.visitor = new TurnActionGameStatusVisitor(this.strategy, this.opponent);
        this.drawOfferAction = new DrawOfferAction(() -> mock(TurnAction.class));
    }

    @Ignore
    @Test
    public void visitMove() {

    }

    @Ignore
    @Test(expected = IllegalStateException.class)
    public void visitMoveOnIllegalMove() {

    }

    @Test
    public void visitDrawOffer() {
        // Arrange
        final DrawOfferAction drawOffer = 
        when(this.opponent.acceptDraw()).thenReturn(true);

        // Act
        final GameStatus result = this.visitor.visit(this.drawOfferAction);

        // Assert
        assertEquals(GameStatus.DRAW_BY_AGREEMENT, result);
    }

    @Test
    public void visitDrawOfferOnRejection() {
        // Arrange
        final TurnAction anotherAction = mock(TurnAction.class);
        when(this.opponent.acceptDraw()).thenReturn(false);
        when(this.strategy.getTurnAction()).thenReturn(anotherAction);
        when(anotherAction.accept(this.visitor)).thenReturn(GameStatus.PLAYING);

        // Act
        final GameStatus result = this.visitor.visit(this.drawOfferAction);

        // Assert
        verify(this.strategy).turnActionRejected(this.drawOfferAction);
        assertEquals(GameStatus.PLAYING, result);
    }

    @Test(expected = IllegalStateException.class)
    public void visitDrawOfferOnDoubleRejection() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(false);
        when(this.strategy.getTurnAction()).thenReturn(this.drawOfferAction);

        // Act
        this.visitor.visit(this.drawOfferAction);
    }

}
