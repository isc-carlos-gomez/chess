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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.krloxz.chess.File;
import org.krloxz.chess.PieceType;
import org.krloxz.chess.Square;

/**
 * Unit tests {@link RecordedGameStrategy}.
 *
 * @author Carlos Gomez
 */
public class SanMoveTest {

    @Test
    public void getSquare() {
        // Arrange
        final SanMove move = new SanMove("Na1");

        // Act
        final Square result = move.getSquare();

        // Assert
        assertNotNull("Square shouldn't be null", result);
    }

    @Test
    public void getSquareOnNoPiece() {
        // Arrange
        final SanMove move = new SanMove("a1");

        // Act
        final Square result = move.getSquare();

        // Assert
        assertNotNull("Square shouldn't be null", result);
    }

    @Test
    public void getPiece() {
        // Arrange
        final SanMove move = new SanMove("Na1");

        // Act
        final PieceType result = move.getPiece();

        // Assert
        assertNotNull("Piece shouldn't be null", result);
    }

    @Test
    public void getPieceOnNoPiece() {
        // Arrange
        final SanMove move = new SanMove("a1");

        // Act
        final PieceType result = move.getPiece();

        // Assert
        assertNotNull("Piece shouldn't be null", result);
    }

    @Test
    public void getFileOrNull() {
        // Arrange
        final SanMove move = new SanMove("Nba1");

        // Act
        final File result = move.getFileOrNull();

        // Assert
        assertNotNull("File shouldn't be null", result);
    }

}
