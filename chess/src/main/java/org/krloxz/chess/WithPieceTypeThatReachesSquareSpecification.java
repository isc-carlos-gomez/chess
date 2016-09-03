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
 * Find the squares that have a pawn which is able to reach the square 1.
 *
 * @author Carlos Gomez
 */
public class WithPieceTypeThatReachesSquareSpecification implements SquareSpecification {

    private final PieceType pieceType;
    private final Square targetSquare;

    /**
     * @param pieceType
     * @param recordedMove
     */
    public WithPieceTypeThatReachesSquareSpecification(final PieceType pieceType, final Square targetSquare) {
        this.pieceType = pieceType;
        this.targetSquare = targetSquare;
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
                .toHashCode();
    }

}
