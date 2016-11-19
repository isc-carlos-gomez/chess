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

import java.util.NoSuchElementException;

/**
 * Encapsulates a chess move generated in a player turn. Please note that, for a more convenient processing, a move is
 * also a visitable player action.
 *
 * @author Carlos Gomez
 */
public class BasicMovement implements PlayerAction {

    private final Square source;
    private final Square target;
    private final PieceType piecePromotedTo;

    /**
     * @param source
     * @param target
     * @param piecePromotedTo
     */
    public BasicMovement(final Square source, final Square target, final PieceType piecePromotedTo) {
        this.source = source;
        this.target = target;
        this.piecePromotedTo = piecePromotedTo;
    }

    /**
     * @param sourceSquare
     * @param targetSquare
     */
    public BasicMovement(final Square from, final Square to) {
        this(from, to, null);
    }

    /**
     * @param sourceSan
     * @param targetSan
     */
    public BasicMovement(final String sourceSan, final String targetSan) {
        this(new Square(sourceSan), new Square(targetSan));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.player.TurnAction#accept(org.krloxz.chess.player.TurnActionVisitor)
     */
    @Override
    public <R> R accept(final ActionVisitor<R> visitor) {
        return visitor.visit(this);
    }

    /**
     * @return
     */
    public Square getSource() {
        return this.source;
    }

    /**
     * @return
     */
    public Square getTarget() {
        return this.target;
    }

    /**
     * @return the piecePromotedTo
     */
    public PieceType getPiecePromotedTo() {
        if (this.piecePromotedTo == null) {
            throw new NoSuchElementException("Move is not a promotion");
        }
        return this.piecePromotedTo;
    }

    /**
     * @return
     */
    public boolean isPromotion() {
        return this.piecePromotedTo != null;
    }

}
