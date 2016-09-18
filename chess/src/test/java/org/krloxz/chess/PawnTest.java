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
    public void confirmLegalMove() {
        assertTrue("Move should be legal", this.lightPawn.confirmLegalMove(new Move("a2", "a3"), this.board));
    }

    @Test
    public void confirmLegalMoveOnDarkPawn() {
        assertTrue("Move should be legal", this.darkPawn.confirmLegalMove(new Move("a7", "a6"), this.board));
    }

    @Test
    public void confirmLegalMoveOnSideMove() {
        assertFalse("Move should be ilegal", this.lightPawn.confirmLegalMove(new Move("a2", "b2"), this.board));
    }

    @Test
    public void confirmLegalMoveOnSideMoveAndDarkPawn() {
        assertFalse("Move should be ilegal", this.darkPawn.confirmLegalMove(new Move("a2", "b2"), this.board));
    }

    @Test
    public void confirmLegalMoveOnInitialTwoSquare() {
        assertTrue("Move should be legal", this.lightPawn.confirmLegalMove(new Move("a2", "a4"), this.board));
    }

    @Test
    public void confirmLegalMoveOnInitialTwoSquareAndDarkPawn() {
        assertTrue("Move should be legal", this.darkPawn.confirmLegalMove(new Move("a7", "a5"), this.board));
    }

    @Test
    public void confirmLegalMoveOnNoInitialTwoSquare() {
        assertFalse("Move should be ilegal", this.lightPawn.confirmLegalMove(new Move("a3", "a5"), this.board));
    }

    @Test
    public void confirmLegalMoveOnNoInitialTwoSquareAndDarkPawn() {
        assertFalse("Move should be ilegal", this.darkPawn.confirmLegalMove(new Move("a6", "a4"), this.board));
    }

    @Test
    public void confirmLegalMoveOnCapture() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);

        // Act
        final boolean isLegalMove = this.lightPawn.confirmLegalMove(new Move("a2", "b3"), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    @Test
    public void confirmLegalMoveOnCaptureAndDarkPawn() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);

        // Act
        final boolean isLegalMove = this.darkPawn.confirmLegalMove(new Move("a7", "b6"), this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
    }

    @Test
    public void confirmLegalMoveOnCaptureAtVacantSquare() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);

        // Act
        final boolean isLegalMove = this.lightPawn.confirmLegalMove(new Move("a3", "b2"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void confirmLegalMoveOnCaptureAtVacantSquareAndDarkPawn() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        when(this.board.getSquare(any())).thenReturn(foundSquare);
        when(foundSquare.isOccupied()).thenReturn(true);

        // Act
        final boolean isLegalMove = this.darkPawn.confirmLegalMove(new Move("a6", "b7"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void confirmLegalMoveOnPromotion() {
        assertTrue("Move should be legal",
                this.lightPawn.confirmLegalMove(new Promotion("a7", "a8", PieceType.QUEEN), this.board));
    }

    @Test
    public void confirmLegalMoveOnPromotionAndDarkPawn() {
        assertTrue("Move should be legal",
                this.darkPawn.confirmLegalMove(new Promotion("a2", "a1", PieceType.QUEEN), this.board));
    }

    @Test
    public void confirmLegalMoveOnPromotionWithIllegalTarget() {
        assertFalse("Move should be ilegal",
                this.lightPawn.confirmLegalMove(new Promotion("a6", "a7", PieceType.QUEEN), this.board));
    }

    @Test
    public void confirmLegalMoveOnPromotionWithIllegalTargetAndDarkPawn() {
        assertFalse("Move should be ilegal",
                this.darkPawn.confirmLegalMove(new Promotion("a3", "a2", PieceType.QUEEN), this.board));
    }

}
