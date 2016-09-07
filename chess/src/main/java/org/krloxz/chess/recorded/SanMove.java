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

import org.apache.commons.lang3.Validate;
import org.krloxz.chess.File;
import org.krloxz.chess.GameEndingType;
import org.krloxz.chess.PieceType;
import org.krloxz.chess.Rank;
import org.krloxz.chess.Square;

/**
 * Kind of DTO that encapsulates all the properties that a move specified in Standard Algebraic Notation can hold.
 *
 * @author Carlos Gomez
 */
public class SanMove {

    private final PieceType piece;
    private final Square square;
    private final File file;
    private final Rank rank;
    private final GameEndingType gameEnding;
    private final PieceType piecePromotedTo;

    private SanMove(final Builder builder) {
        this.piece = builder.piece;
        this.square = builder.square;
        this.file = builder.file;
        this.rank = builder.rank;
        this.gameEnding = builder.gameEnding;
        this.piecePromotedTo = builder.piecePromotedTo;
    }

    /**
     * @param string
     */
    public SanMove(final String string) {
        this.piece = null;
        this.square = null;
        this.file = null;
        this.rank = null;
        this.gameEnding = null;
        this.piecePromotedTo = null;
    }

    /**
     * @return the piece
     */
    public PieceType getPiece() {
        return this.piece;
    }

    /**
     * @return the square
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @return the rank
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * @return the gameEnding
     */
    public GameEndingType getGameEnding() {
        return this.gameEnding;
    }

    /**
     * @return the piecePromotedTo
     */
    public PieceType getPiecePromotedToOrNull() {
        return this.piecePromotedTo;
    }

    /**
     * @return
     */
    public boolean isFullySpecified() {
        return this.file != null && this.rank != null;
    }

    /**
     * @return
     */
    public boolean isGameEnding() {
        return this.gameEnding != null;
    }

    /**
     * @return
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * A builder to create {@link SanMove}s.
     *
     * @author Carlos Gomez
     */
    public static class Builder {

        private PieceType piece;
        private Square square;
        private File file;
        private Rank rank;
        private GameEndingType gameEnding;
        private PieceType piecePromotedTo;

        /**
         * @param piece
         * @return
         */
        public Builder piece(final PieceType piece) {
            this.piece = piece;
            return this;
        }

        /**
         * @param square
         * @return
         */
        public Builder square(final Square square) {
            this.square = square;
            return this;
        }

        /**
         * @param file
         * @return
         */
        public Builder file(final File file) {
            this.file = file;
            return this;
        }

        /**
         * @param rank
         * @return
         */
        public Builder rank(final Rank rank) {
            this.rank = rank;
            return this;
        }

        /**
         * @param gameEnding
         * @return
         */
        public Builder gameEnding(final GameEndingType gameEnding) {
            this.gameEnding = gameEnding;
            return this;
        }

        /**
         * @param piecePromotedTo
         * @return
         */
        public Builder piecePromotedTo(final PieceType piecePromotedTo) {
            this.piecePromotedTo = piecePromotedTo;
            return this;
        }

        /**
         * @return
         */
        public SanMove build() {
            if (this.gameEnding == null) {
                Validate.notNull(this.piece, "A piece is required");
                Validate.notNull(this.square, "A square is required");
            }
            return new SanMove(this);
        }

    }

}
