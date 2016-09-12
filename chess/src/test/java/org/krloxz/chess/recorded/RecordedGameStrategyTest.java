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
package org.krloxz.chess.recorded;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.krloxz.chess.Board;
import org.krloxz.chess.File;
import org.krloxz.chess.GameEnding;
import org.krloxz.chess.GameEndingType;
import org.krloxz.chess.Move;
import org.krloxz.chess.PieceType;
import org.krloxz.chess.PlayerAction;
import org.krloxz.chess.Rank;
import org.krloxz.chess.Square;
import org.krloxz.chess.SquareSpecification;
import org.krloxz.chess.WithPieceTypeThatReachesSquareSpecification;

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

        // Common behavior
        when(this.moves.next()).thenReturn(this.move);
    }

    @Test
    public void nextAction() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(this.pieceType)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
    }

    @Test
    public void nextActionOnFileSpecified() {
        // Arrange
        final File file = File.a;
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .file(file)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(this.pieceType)
                .targetSquare(this.targetSquare)
                .file(file)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
    }

    @Test
    public void nextActionOnRankSpecified() {
        // Arrange
        final Rank rank = Rank.ONE;
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .rank(rank)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(this.pieceType)
                .targetSquare(this.targetSquare)
                .rank(rank)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
    }

    @Test
    public void nextActionOnFileAndRankSpecified() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .file(File.a)
                .rank(Rank.ONE)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
    }

    @Test
    public void nextActionOnEndOfGame() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .gameEnding(GameEndingType.DRAW)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a game ending", result instanceof GameEnding);
    }

    @Test
    public void nextActionOnPawnPromotion() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .piece(PieceType.PAWN)
                .square(this.targetSquare)
                .piecePromotedTo(PieceType.KNIGHT)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(PieceType.PAWN)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Arrays.asList(this.sourceSquare));

        // Act
        final PlayerAction result = this.strategy.nextAction(this.board);

        // Assert
        assertTrue("Next action should be a move", result instanceof Move);
        assertTrue("Move should be a promotion", ((Move) result).isPromotion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnIllegalPieceOrIllegalSquare() {
        // Arrange
        when(this.moveParser.parse(this.move)).thenThrow(new IllegalArgumentException());

        // Act
        this.strategy.nextAction(this.board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnAmbiguousMove() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(this.pieceType)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification))
                .thenReturn(Arrays.asList(this.sourceSquare, this.sourceSquare));

        // Act
        this.strategy.nextAction(this.board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextActionOnTargetNotReachable() {
        // Arrange
        final SanMove sanMove = SanMove.getBuilder()
                .piece(this.pieceType)
                .square(this.targetSquare)
                .build();
        final SquareSpecification squareSpecification = WithPieceTypeThatReachesSquareSpecification.getBuilder()
                .piece(this.pieceType)
                .targetSquare(this.targetSquare)
                .build();
        when(this.moveParser.parse(this.move)).thenReturn(sanMove);
        when(this.board.findSquares(squareSpecification)).thenReturn(Collections.emptyList());

        // Act
        this.strategy.nextAction(this.board);
    }

}
