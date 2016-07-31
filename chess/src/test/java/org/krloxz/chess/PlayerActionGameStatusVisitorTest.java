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
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests {@link PlayerActionGameStatusVisitor}.
 *
 * @author Carlos Gomez
 */
public class PlayerActionGameStatusVisitorTest {

    private PlayerActionGameStatusVisitor visitor;
    private Player player;
    private Player opponent;

    @Before
    public void setUp() {
        this.player = mock(Player.class);
        this.opponent = mock(Player.class);
        this.visitor = new PlayerActionGameStatusVisitor(this.player, this.opponent);
    }

    @Ignore
    @Test
    public void visitDrawOfferOnMove() {

    }

    @Test
    public void visitDrawOffer() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(true);

        // Act
        // final GameState result = this.visitor.visitDrawOffer();

        // Assert
        // assertEquals(GameState.DRAW_BY_AGREEMENT, result);
    }

    @Test
    public void visitDrawOfferOnRejection() {
        // Arrange
        when(this.opponent.acceptDraw()).thenReturn(false);
        // when(this.player.yourTurn()).thenReturn(GameState.RUNNING);

        // Act
        // final GameState result = this.visitor.visitDrawOffer();

        // Assert
        // verify(this.player).actionRejected(PlayerAction.DRAW_OFFER);
        // assertEquals(GameState.RUNNING, result);
    }

    @Ignore
    @Test
    public void visitDrawOfferOnDoubleRejection() {

    }

}
