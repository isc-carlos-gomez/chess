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
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link PlayerActionExecutorVisitor}.
 *
 * @author Carlos Gomez
 */
public class PlayerActionExecutorVisitorTest {

    private PlayerActionExecutorVisitor visitor;
    private Player player;
    private Player opponent;

    @Before
    public void setUp() {
        this.player = mock(Player.class);
        this.opponent = mock(Player.class);
        this.visitor = new PlayerActionExecutorVisitor(this.player);
    }

    @Test
    public void visitDrawOfferOnAccept() {
        // Arrange
        final DrawOfferAction drawOffer = mock(DrawOfferAction.class);
        when(this.player.getOpponent()).thenReturn(this.opponent);
        when(this.opponent.acceptDraw()).thenReturn(true);

        // Act
        final PlayerAction result = this.visitor.visit(drawOffer);

        // Assert
        assertEquals(drawOffer, result);
    }

    @Test
    public void visitDrawOfferOnRejection() {
        // Arrange
        final DrawOfferAction drawOffer = mock(DrawOfferAction.class);
        final PlayerAction otherAction = mock(PlayerAction.class);
        final PlayerAction anyOtherAction = mock(PlayerAction.class);
        when(this.player.getOpponent()).thenReturn(this.opponent);
        when(this.opponent.acceptDraw()).thenReturn(false);
        when(drawOffer.rejected()).thenReturn(otherAction);
        when(otherAction.accept(this.visitor)).thenReturn(anyOtherAction);

        // Act
        final PlayerAction result = this.visitor.visit(drawOffer);

        // Assert
        assertEquals(anyOtherAction, result);
    }

    @Test(expected = IllegalStateException.class)
    public void visitDrawOfferTwice() {
        // Arrange
        final DrawOfferAction drawOffer = mock(DrawOfferAction.class);
        final PlayerAction secondDrawOffer = mock(DrawOfferAction.class);
        when(this.player.getOpponent()).thenReturn(this.opponent);
        when(this.opponent.acceptDraw()).thenReturn(false);
        when(drawOffer.rejected()).thenReturn(secondDrawOffer);

        // Act
        this.visitor.visit(drawOffer);
    }

}
