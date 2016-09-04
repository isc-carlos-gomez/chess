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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link RecordedGameStrategy}.
 *
 * @author Carlos Gomez
 */
public class RecordedGameStrategyTest {

    private RecordedGameStrategy strategy;
    private Iterator<String> moves;
    private SanParser moveParser;
    private Board board;
    private String move;
    private PieceType pieceType;
    private Square sourceSquare;
    private Square targetSquare;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        this.moves = mock(Iterator.class);
        this.moveParser = mock(SanParser.class);
        this.strategy = new RecordedGameStrategy(this.moves, this.moveParser);
        this.board = mock(Board.class);
        this.move = "xxxxx";
        this.pieceType = PieceType.KNIGHT;
        this.sourceSquare = mock(Square.class);
        this.targetSquare = mock(Square.class);
    }

    @Test
    public void nextAction() {
        // Arrange
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));
        when(this.moveParser.getPiecePromotedTo(this.move)).thenReturn(Optional.empty());

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertMoveAction(result);
    }

    @Test
    public void nextActionOnFileSpecified() {
        // Arrange
        final String file = "a";
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .file(file)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getFile(this.move)).thenReturn(file);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));
        when(this.moveParser.getPiecePromotedTo(this.move)).thenReturn(Optional.empty());

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertMoveAction(result);
    }

    @Test
    public void nextActionOnRankSpecified() {
        // Arrange
        final String rank = "1";
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .rank(rank)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getRank(this.move)).thenReturn(rank);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));
        when(this.moveParser.getPiecePromotedTo(this.move)).thenReturn(Optional.empty());

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertMoveAction(result);
    }

    @Test
    public void nextActionOnFileAndRankSpecified() {
        // Arrange
        final Optional<Square> optionalSquare = Optional.of(this.sourceSquare);
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(optionalSquare);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.moveParser.getPiecePromotedTo(this.move)).thenReturn(Optional.empty());

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertMoveAction(result);
    }

    @Test
    public void nextActionOnEndOfGame() {
        // Arrange
        final Optional<GameEnding> optionalGameEnding = Optional.of(mock(GameEnding.class));
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(optionalGameEnding);

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a game ending", result instanceof GameEnding);
    }

    @Test
    public void nextActionOnPawnPromotion() {
        // Arrange
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));
        when(this.moveParser.getPiecePromotedTo(this.move)).thenReturn(Optional.of(this.pieceType));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertMoveAction(result, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnIllegalPiece() {
        // Arrange
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenThrow(new IllegalArgumentException());

        // Act
        this.strategy.nextAction(this.board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnIllegalSquare() {
        // Arrange
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getTargetSquare(this.move)).thenThrow(new IllegalArgumentException());

        // Act
        this.strategy.nextAction(this.board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnAmbiguousMove() {
        // Arrange
        final String rank = "1";
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .rank(rank)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getRank(this.move)).thenReturn(rank);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification))
                .thenReturn(Arrays.asList(this.sourceSquare, this.sourceSquare));

        // Act
        this.strategy.nextAction(this.board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnTargetNotReachable() {
        // Arrange
        final String rank = "1";
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .pieceType(this.pieceType)
                .targetSquare(this.targetSquare)
                .rank(rank)
                .build();
        when(this.moves.next()).thenReturn(this.move);
        when(this.moveParser.getGameEnding(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getSourceSquare(this.move)).thenReturn(Optional.empty());
        when(this.moveParser.getPieceType(this.move)).thenReturn(this.pieceType);
        when(this.moveParser.getRank(this.move)).thenReturn(rank);
        when(this.moveParser.getTargetSquare(this.move)).thenReturn(this.targetSquare);
        when(this.board.findSquares(squareSpecification)).thenReturn(Collections.emptyList());

        // Act
        this.strategy.nextAction(this.board);
    }

    private void assertMoveAction(final PlayerAction result, final boolean isPromotion) {
        assertTrue("Next action should be a move", result instanceof Move);
        final Move resultMove = (Move) result;
        assertEquals(this.sourceSquare, resultMove.getFrom());
        assertEquals(this.targetSquare, resultMove.getTo());
        if (isPromotion) {
            assertTrue("Move must be a promotion", resultMove.isPromotion());
        } else {
            assertFalse("Move must be not a promotion", resultMove.isPromotion());
        }
    }

    private void assertMoveAction(final PlayerAction result) {
        assertMoveAction(result, false);
    }

}
