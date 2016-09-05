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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A {@link SquareSpecification} that matches the squares that have a piece of the specified type in the specified file
 * and rank (if any of them both are provided) and than is able to reach the given target square.
 *
 * @author Carlos Gomez
 */
public class WithPieceTypeThatReachesSquareSpecification implements SquareSpecification {

    private final PieceType pieceType;
    private final Square targetSquare;
    private final File file;
    private final Rank rank;

    private WithPieceTypeThatReachesSquareSpecification(final Builder builder) {
        this.pieceType = builder.pieceType;
        this.file = builder.file;
        this.targetSquare = builder.targetSquare;
        this.rank = builder.rank;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.SquareSpecification#isSatisfiedBy(org.krloxz.chess.Square)
     */
    @Override
    public boolean isSatisfiedBy(final Square square) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object instanceof WithPieceTypeThatReachesSquareSpecification) {
            final WithPieceTypeThatReachesSquareSpecification other = (WithPieceTypeThatReachesSquareSpecification) object;
            return new EqualsBuilder()
                    .append(this.pieceType, other.pieceType)
                    .append(this.targetSquare, other.targetSquare)
                    .append(this.file, other.file)
                    .append(this.rank, other.rank)
                    .isEquals();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.pieceType)
                .append(this.targetSquare)
                .append(this.file)
                .append(this.rank)
                .toHashCode();
    }

    /**
     * A builder (pattern) to create instances of this class.
     *
     * @author Carlos Gomez
     */
    public static class Builder {

        private PieceType pieceType;
        private Square targetSquare;
        private File file;
        private Rank rank;

        private Builder() {
            // Empty
        }

        public Builder piece(final PieceType pieceType) {
            this.pieceType = pieceType;
            return this;
        }

        public Builder targetSquare(final Square targetSquare) {
            this.targetSquare = targetSquare;
            return this;
        }

        public Builder file(final File file) {
            this.file = file;
            return this;
        }

        public Builder rank(final Rank rank) {
            this.rank = rank;
            return this;
        }

        public WithPieceTypeThatReachesSquareSpecification build() {
            // TODO: validate required properties
            return new WithPieceTypeThatReachesSquareSpecification(this);
        }

    }

}
