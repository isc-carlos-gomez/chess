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

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link Board}.
 *
 * @author Carlos Gomez
 */
public class BoardTest {

    private Board board;
    private SquareRepository repository;

    @Before
    public void setUp() {
        this.repository = mock(SquareRepository.class);
        this.board = new Board(this.repository);
    }

    @Test
    public void update() {
        // Arrange
        final Square source = mock(Square.class);
        final Square target = mock(Square.class);
        final Move move = mock(Move.class);
        final Piece piece = mock(Piece.class);
        final SquareIsEqualTo specification = new SquareIsEqualTo(source);
        when(this.repository.findSquares(specification)).thenReturn(Arrays.asList(source));
        when(source.getPiece()).thenReturn(Optional.of(piece));
        when(piece.move(target)).thenReturn(true);

        // Act
        final boolean result = this.board.update(move);

        // Assert
        assertTrue("Board should be updated on valid moves", result);
        verify(this.repository).refreshSquares((List<Piece>) argThat(contains(piece)));
    }

}
