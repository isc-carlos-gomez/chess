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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.notNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link King}.
 *
 * @author Carlos Gomez
 */
public class KingTest {

    private King lightKing;
    private King darkKing;
    private KingMovingStyle movingStyle;
    private Board board;

    @Before
    public void setUp() {
        this.movingStyle = mock(KingMovingStyle.class);
        this.lightKing = new King(Color.LIGHT, this.movingStyle);
        this.darkKing = new King(Color.DARK, this.movingStyle);
        this.board = mock(Board.class);
    }

    @Test
    public void getPositionOnNewLightKing() {
        assertEquals(King.INITIAL_POSITION_ON_LIGHT, this.lightKing.getPosition());
    }

    @Test
    public void getPositionOnNewDarkKing() {
        assertEquals(King.INITIAL_POSITION_ON_DARK, this.darkKing.getPosition());
    }

    @Test
    public void move() {
        // Arrange
        final Square target = mock(Square.class);
        when(this.movingStyle.isLegalMove(notNull(Square.class), same(target), same(this.board)))
                .thenReturn(true);
        when(this.board.findPiece(notNull(InSquareSpecification.class))).thenReturn(Optional.empty());

        // Act
        final boolean moved = this.lightKing.move(target, this.board);

        // Assert
        assertTrue("King should have moved", moved);
        assertEquals(target, this.lightKing.getPosition());
    }

    @Test
    public void moveOnIllegalMove() {
        // Arrange
        final Square target = mock(Square.class);
        when(this.movingStyle.isLegalMove(notNull(Square.class), same(target), same(this.board)))
                .thenReturn(false);

        // Act
        final boolean moved = this.lightKing.move(target, this.board);

        // Assert
        assertFalse("King shouldn't have moved", moved);
        assertEquals(King.INITIAL_POSITION_ON_LIGHT, this.lightKing.getPosition());
    }

    @Test
    public void moveOnCapture() {
        // Arrange
        final Square target = mock(Square.class);
        final Piece piece = mock(Piece.class);
        when(this.movingStyle.isLegalMove(notNull(Square.class), same(target), same(this.board)))
                .thenReturn(true);
        when(this.board.findPiece(notNull(InSquareSpecification.class))).thenReturn(Optional.of(piece));

        // Act
        this.lightKing.move(target, this.board);

        // Assert
        verify(piece).captured();
    }

}