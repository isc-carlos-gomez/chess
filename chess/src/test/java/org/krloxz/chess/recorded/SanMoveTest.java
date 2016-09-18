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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests {@link RecordedGameStrategy}.
 *
 * @author Carlos Gomez
 */
public class SanMoveTest {

    private SanMove move;

    @Before
    public void setUp() {
    }

    @Ignore
    @Test
    public void creationOnBasicMove() {
        // Arrange

        // Act
        final SanMove move = new SanMove("Na1");

        // Assert
        assertNotNull(move.getPiece());
        assertNotNull(move.getSquare());
        assertNull(move.getFile());
        assertNull(move.getRank());
        try {
            move.getGameEnding();
            fail("IllegalStateException was expected");
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage());
        }
        assertNull(move.getPiecePromotedToOrNull());
        assertFalse(move.isFullySpecified());
        assertFalse(move.isGameEnding());
    }

    /*
     * creationOnNoPiece creationOnFile creationOnRank
     * 
     * 
     * getPiece OnNoPiece OnCapture getPieceOnPromotion getPieceOnKingsideCastling getPieceOnQueensideCastling
     * getPieceOnCheck getPieceOnCheckmate getPieceOnGameEnding
     * 
     * getSquare OnCapture OnPromotion OnKingsideCastling OnQueensideCastling OnCheck OnCheckmate OnGameEnding
     * 
     * getFile OnNoFile OnCapture OnPromotion OnKingsideCastling OnQueensideCastling OnCheck OnCheckmate OnGameEnding
     * 
     * getRank OnNoRank OnCapture OnPromotion OnKingsideCastling OnQueensideCastling OnCheck OnCheckmate OnGameEnding
     * 
     * getGameEnding OnNoEnding
     * 
     * getPiecePromotedToOrNull getPiecePromotedToOrNullOnNoPromotion isFullySpecified isFullySpecifiedOnNoFile
     * isFullySpecifiedOnNoRank isGameEnding isGameEndingOnNoEnding
     */

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

    @Ignore
    @Test
    public void nextActionOnPawn() {
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
