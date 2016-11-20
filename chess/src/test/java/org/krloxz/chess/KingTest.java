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
import static org.mockito.Matchers.isA;
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
    private Board board;

    @Before
    public void setUp() {
        this.lightKing = new King(Color.WHITE);
        this.darkKing = new King(Color.BLACK);
        this.board = mock(Board.class);
    }

    @Test
    public void move() {
        // Arrange
        final Square targetSquare = new Square("f1");
        final InSquareSpecification specification = new InSquareSpecification(targetSquare);
        when(this.board.findPiece(specification)).thenReturn(Optional.empty());

        // Act
        final boolean moved = this.lightKing.move(targetSquare, this.board);

        // Assert
        assertTrue("King should have moved", moved);
        assertEquals(targetSquare, this.lightKing.getPosition());
    }

    @Test
    public void moveOnSquareOutOfScope() {
        // Arrange
        final Square targetSquare = new Square("e3");
        final InSquareSpecification specification = new InSquareSpecification(targetSquare);
        when(this.board.findPiece(specification)).thenReturn(Optional.empty());

        // Act
        final boolean moved = this.lightKing.move(targetSquare, this.board);

        // Assert
        assertFalse("King shouldn't have moved", moved);
        assertEquals(King.INITIAL_POSITION, this.lightKing.getPosition());
    }

    @Test
    public void moveOnOccupiedSquare() {
        // Arrange
        final Square targetSquare = new Square("f1");
        final InSquareSpecification specification = new InSquareSpecification(targetSquare);
        final Piece foundPiece = mock(Piece.class);
        when(this.board.findPiece(specification)).thenReturn(Optional.of(foundPiece));
        when(foundPiece.getColor()).thenReturn(Color.WHITE);

        // Act
        final boolean moved = this.lightKing.move(targetSquare, this.board);

        // Assert
        assertFalse("King shouldn't have moved", moved);
        assertEquals(King.INITIAL_POSITION, this.lightKing.getPosition());
    }

    @Test
    public void moveOnCapture() {
        // Arrange
        final Square targetSquare = new Square("f1");
        final InSquareSpecification specification = new InSquareSpecification(targetSquare);
        final Piece foundPiece = mock(Piece.class);
        when(this.board.findPiece(specification)).thenReturn(Optional.of(foundPiece));
        when(foundPiece.getColor()).thenReturn(Color.BLACK);

        // Act
        final boolean moved = this.lightKing.move(targetSquare, this.board);

        // Assert
        assertTrue("King should have moved", moved);
        assertEquals(targetSquare, this.lightKing.getPosition());
        verify(foundPiece).captured();
    }

    @Test
    public void moveOnCastling() {
        // Arrange
        final Square targetSquare = new Square("g1");
        final Rook rook = mock(Rook.class);
        final PieceInSquareSpecification specification = new PieceInSquareSpecification(
                Rook.class, King.KINGSIDE_ROOK_POSITION);
        when(this.board.findPiece(specification)).thenReturn(Optional.of(rook));

        // Act
        final boolean moved = this.lightKing.move(targetSquare, this.board);

        // Assert
        assertTrue("King should have moved", moved);
        assertEquals(targetSquare, this.lightKing.getPosition());
    }

    @Test
    public void moveOnCastlingAndKingPreviouslyMoved() {
        // Arrange
        when(this.board.findPiece(isA(InSquareSpecification.class))).thenReturn(Optional.empty());
        this.lightKing.move(new Square("f1"), this.board);
        this.lightKing.move(new Square("e1"), this.board);

        // Act
        final boolean moved = this.lightKing.move(new Square("g1"), this.board);

        // Assert
        assertFalse("King shouldn't have moved", moved);
        assertEquals(King.INITIAL_POSITION, this.lightKing.getPosition());
    }

    @Test
    public void moveOnLightKingsideCastlingAndRookNotFound() {
        // Arrange
        final PieceInSquareSpecification specification = new PieceInSquareSpecification(
                Rook.class, King.KINGSIDE_ROOK_POSITION);
        when(this.board.findPiece(specification)).thenReturn(Optional.empty());

        // Act
        final boolean moved = this.lightKing.move(new Square("g1"), this.board);

        // Assert
        assertFalse("King shouldn't have moved", moved);

    }

}