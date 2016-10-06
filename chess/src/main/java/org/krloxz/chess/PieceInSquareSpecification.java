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
 * @author Carlos Gomez
 */
public class PieceInSquareSpecification implements PieceSpecification {

    private final Class<? extends Piece> pieceType;
    private final Square square;

    /**
     * @param pieceType
     * @param kingsideRookPosition
     */
    public PieceInSquareSpecification(final Class<? extends Piece> pieceType, final Square square) {
        this.pieceType = pieceType;
        this.square = square;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object instanceof PieceInSquareSpecification) {
            final PieceInSquareSpecification other = (PieceInSquareSpecification) object;
            return new EqualsBuilder()
                    .append(this.pieceType, other.pieceType)
                    .append(this.square, other.square)
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
                .append(this.square)
                .toHashCode();
    }

}
