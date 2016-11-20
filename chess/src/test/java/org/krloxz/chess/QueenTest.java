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

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test {@link Queen}.
 *
 * @author Carlos Gomez
 */
public class QueenTest {

    private Queen queen;
    private Board board;

    @Before
    public void setUp() {
        this.queen = new Queen(Color.WHITE);
        this.board = mock(Board.class);
    }

    @Test
    public void confirmLegalMoveOnDiagonalMove() {
        assertTrue("Move should be legal", this.queen.confirmLegalMove(new BasicMovement("a1", "h8"), this.board));
    }

    @Test
    public void confirmLegalMoveOnStraightMove() {
        assertTrue("Move should be legal", this.queen.confirmLegalMove(new BasicMovement("a1", "a8"), this.board));
    }

    @Test
    public void confirmLegalMoveOnNoDiagonalAndNoStraightMove() {
        assertFalse("Move should be ilegal", this.queen.confirmLegalMove(new BasicMovement("a1", "b3"), this.board));
    }

}
