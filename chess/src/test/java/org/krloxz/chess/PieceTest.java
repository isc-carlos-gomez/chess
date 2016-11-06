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
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link Piece}.
 *
 * @author Carlos Gomez
 */
public class PieceTest {

    private Piece piece;
    private PieceBehavior behavior;
    private Square initialPosition;
    private SquareAnalizer squareAnalizer;
    private Board board;

    @Before
    public void setUp() {
        this.behavior = mock(PieceBehavior.class);
        this.initialPosition = mock(Square.class);
        this.piece = new Piece(this.behavior, this.initialPosition);
        this.board = mock(Board.class);
    }

    @Test
    public void move() {
        final Square target = mock(Square.class);
        when(this.behavior.isLegalMove(this.initialPosition, target, this.board))
                .thenReturn(true);
        assertTrue("Piece should be able to move", this.piece.move(target, this.board));
    }

    @Test
    public void moveOnIllegalMove() {
        final Square target = mock(Square.class);
        when(this.behavior.isLegalMove(this.initialPosition, target, this.board))
                .thenReturn(false);
        assertFalse("Piece shouldn't be able to move", this.piece.move(target, this.board));
    }

    @Test
    public void moveOnOccupiedSquare() {
        final Square target = mock(Square.class);
        when(this.behavior.isLegalMove(this.initialPosition, target, this.board))
                .thenReturn(true);
        when(this.squareAnalizer.isSquareOccupied(target, this.board))
                .thenReturn(true);
        assertFalse("Piece shouldn't be able to move", this.piece.move(target, this.board));
    }

}
