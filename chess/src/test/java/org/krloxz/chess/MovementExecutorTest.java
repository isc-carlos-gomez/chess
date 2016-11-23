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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link MovementExecutor}.
 *
 * @author Carlos Gomez
 */
public class MovementExecutorTest {

    private MovementExecutor broker;
    private MovementPolicy movementPolicy;
    private BasicMovement basicMovement;
    private Board board;
    private Piece pieceAtSource;
    private Piece pieceAtTarget;

    @Before
    public void setUp() {
        this.movementPolicy = mock(MovementPolicy.class);
        this.board = mock(Board.class);
        this.broker = new MovementExecutor(this.board, this.movementPolicy);
        this.basicMovement = new BasicMovement(mock(Square.class), mock(Square.class));
        this.pieceAtSource = mock(Piece.class);
        this.pieceAtTarget = mock(Piece.class);
    }

    @Test
    public void updateBoardOnBasicMove() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertTrue("Board must be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveMovesPieceAtSource() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.empty());

        // Act
        this.broker.updateBoard(this.basicMovement);

        // Assert
        verify(this.pieceAtSource).move(this.basicMovement.getTarget());
    }

    @Test
    public void updateBoardOnBasicMoveCapturesPieceAtTarget() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(false);

        // Act
        this.broker.updateBoard(this.basicMovement);

        // Assert
        verify(this.pieceAtTarget).captured();
    }

    @Test
    public void updateBoardOnBasicMoveAndPieceNotFoundAtSource() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertFalse("Board must not be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveAndIllegalMove() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(false);

        // Act & Assert
        assertFalse("Board must not be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveAndPathNotClear() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(false);

        // Act & Assert
        assertFalse("Board must not be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveAndTargetOccupiedByFellow() {
        // Arrange
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(true);

        // Act & Assert
        assertFalse("Board must not be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveAndKingInCheck() {
        // Arrange
        final PieceMemento sourceMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.empty());
        when(this.board.isKingInCheck())
                .thenReturn(true);
        when(this.pieceAtSource.move(this.basicMovement.getTarget()))
                .thenReturn(sourceMemento);

        // Act & Assert
        assertFalse("Board must not be updated", this.broker.updateBoard(this.basicMovement));
    }

    @Test
    public void updateBoardOnBasicMoveAndKingInCheckRestoresPieceAtSource() {
        // Arrange
        final PieceMemento sourceMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.empty());
        when(this.pieceAtSource.move(this.basicMovement.getTarget()))
                .thenReturn(sourceMemento);
        when(this.board.isKingInCheck())
                .thenReturn(true);

        // Act
        this.broker.updateBoard(this.basicMovement);

        // Assert
        verify(this.pieceAtSource).restoreTo(sourceMemento);
    }

    @Test
    public void updateBoardOnBasicMoveAndKingInCheckRestoresPieceAtTarget() {
        // Arrange
        final PieceMemento targetMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.basicMovement.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.movementPolicy.isLegalMove(this.pieceAtSource.getType(), this.basicMovement.getSource(),
                this.basicMovement.getTarget()))
                        .thenReturn(true);
        when(this.board.isPathClear(this.basicMovement.getSource(), this.basicMovement.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.basicMovement.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(false);
        when(this.pieceAtTarget.captured())
                .thenReturn(targetMemento);
        when(this.board.isKingInCheck())
                .thenReturn(true);

        // Act
        this.broker.updateBoard(this.basicMovement);

        // Assert
        verify(this.pieceAtTarget).restoreTo(targetMemento);
    }

}
