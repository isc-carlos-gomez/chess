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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test {@link Pawn}.
 *
 * @author Carlos Gomez
 */
public class PawnTest {

    private Pawn lightPawn;
    private Pawn darkPawn;
    private Board board;

    @Before
    public void setUp() {
        this.lightPawn = new Pawn(Color.LIGHT);
        this.darkPawn = new Pawn(Color.DARK);
        this.board = mock(Board.class);
    }

    @Test
    public void isLegalMove() {
        // Arrange
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Move("a2", "a3"), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnInitialTwoSquare() {
        // Arrange
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Move("a2", "a4"), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnNoInitialTwoSquare() {
        // Arrange
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Move("a3", "a5"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnCapture() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        final Piece piece = mock(Piece.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);
        when(foundSquare.getPiece()).thenReturn(piece);
        when(piece.getColor()).thenReturn(Color.DARK);

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Move("a2", "b3"), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    // TODO: Move to Piece
    @Test
    public void isLegalMoveOnTargetOccupied() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        final Piece piece = mock(Piece.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);
        when(foundSquare.getPiece()).thenReturn(piece);
        when(piece.getColor()).thenReturn(Color.LIGHT);

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Move("a2", "a3"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnPromotion() {
        // Arrange
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Promotion("a7", "a8", PieceType.QUEEN), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnPromotionWithIllegalTarget() {
        // Arrange
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.lightPawn.isLegalMove(new Promotion("a6", "a7", PieceType.QUEEN), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

}
