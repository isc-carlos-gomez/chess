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
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test {@link Piece}.
 *
 * @author Carlos Gomez
 */
public class PieceTest {

    private Piece piece;
    private Board board;

    @Before
    public void setUp() {
        this.piece = spy(TestPiece.class);
        this.board = mock(Board.class);
    }

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
        final boolean isLegalMove = this.piece.isLegalMove(new Move("a1", "a2"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void isLegalMoveOnNoVacantSquares() {
        // Arrange
        final Square foundSquare = mock(Square.class);
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));
        when(this.board.findSquares(isA(OccupiedAndBetweenSquaresSpecification.class)))
                .thenReturn(Arrays.asList(foundSquare));

        // Act
        final boolean isLegalMove = this.piece.isLegalMove(new Move("a1", "a5"), this.board);

        // Assert
        assertFalse("Move should be ilegal", isLegalMove);
    }

    @Test
    public void isLegalMoveDelagateToSubclass() {
        // Arrange
        final Move move = new Move("a1", "a5");
        when(this.board.getSquare(any())).thenReturn(mock(Square.class));

        // Act
        final boolean isLegalMove = this.piece.isLegalMove(move, this.board);

        // Assert
        assertTrue("Move should be legal", isLegalMove);
        verify(this.piece).confirmLegalMove(move, this.board);
    }

    private static class TestPiece extends Piece {

        /**
         * @param color
         */
        public TestPiece() {
            super(Color.LIGHT);
        }

        /*
         * (non-Javadoc)
         *
         * @see org.krloxz.chess.Piece#confirmLegalMove(org.krloxz.chess.Move, org.krloxz.chess.Board)
         */
        @Override
        protected boolean confirmLegalMove(final Move move, final Board board) {
            return true;
        }

    }

}
