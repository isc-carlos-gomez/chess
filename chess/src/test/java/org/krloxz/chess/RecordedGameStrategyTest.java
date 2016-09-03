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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests {@link RecordedGameStrategy}.
 *
 * @author Carlos Gomez
 */
public class RecordedGameStrategyTest {

    private RecordedGameStrategy strategy;
    private Iterator<String> moveIterator;
    private Board board;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        final List<String> moves = mock(List.class);
        this.moveIterator = mock(Iterator.class);
        when(moves.iterator()).thenReturn(this.moveIterator);
        this.strategy = new RecordedGameStrategy(moves);
        this.board = mock(Board.class);
    }

    @Test
    public void nextAction() {
        // Arrange
        final String recordedMove = "c3";
        final Square targetSquare = new Square("c3");
        final SquareSpecification squareSpecification = new WithPieceTypeThatReachesSquareSpecification(PieceType.PAWN,
                targetSquare);
        final Square sourceSquare = mock(Square.class);
        when(this.moveIterator.next()).thenReturn(recordedMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
        final Move move = (Move) result;
        assertEquals(sourceSquare, move.getFrom());
        assertEquals(targetSquare, move.getTo());
    }

    @Ignore
    @Test
    public void nextActionOnFileSpecified() {

    }

    @Ignore
    @Test
    public void nextActionOnRankSpecified() {

    }

    @Ignore
    @Test
    public void nextActionOnFileAndRankSpecified() {

    }

    @Ignore
    @Test
    public void nextActionOnCapture() {

    }

    @Ignore
    @Test
    public void nextActionOnCaptureAndFileSpecified() {

    }

    @Ignore
    @Test
    public void nextActionOnCaptureAndRankSpecified() {

    }

    @Ignore
    @Test
    public void nextActionOnCaptureAndFileAndRankSpecified() {

    }

    @Test
    public void nextActionOnPawn() {
        // Arrange
        final String recordedMove = "e4";
        final Square targetSquare = new Square(recordedMove);
        final SquareSpecification squareSpecification = new WithPieceTypeThatReachesSquareSpecification(PieceType.PAWN,
                targetSquare);
        final Square sourceSquare = mock(Square.class);
        when(this.moveIterator.next()).thenReturn(recordedMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
        final Move move = (Move) result;
        assertEquals(sourceSquare, move.getFrom());
        assertEquals(targetSquare, move.getTo());
    }

    @Ignore
    @Test
    public void nextActionOnPawnPromotion() {

    }

    @Ignore
    @Test
    public void nextActionOnKingsideCastling() {

    }

    @Ignore
    @Test
    public void nextActionOnQueensideCastling() {

    }

    @Ignore
    @Test
    public void nextActionOnCheck() {

    }

    @Ignore
    @Test
    public void nextActionOnCheckMate() {

    }

    @Ignore
    @Test
    public void nextActionOnEndOfGame() {

    }

    @Ignore
    @Test
    public void nextActionOnAmbiguousMove() {

    }

    @Ignore
    @Test
    public void nextActionOnInvalidPiece() {

    }

    @Ignore
    @Test
    public void nextActionOnTargetNotReachable() {

    }

    @Ignore
    @Test
    public void nextActionOnTargetNonExistent() {

    }

}
