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
public class Move implements PlayerAction {

    private final Square from;
    private final Square to;
    private final PieceType piecePromotedTo;

    /**
     * @param from
     * @param to
     * @param piecePromotedTo
     */
    public Move(final Square from, final Square to, final PieceType piecePromotedTo) {
        this.from = from;
        this.to = to;
        this.piecePromotedTo = piecePromotedTo;
    }

    /**
     * @param sourceSquare
     * @param targetSquare
     */
    public Move(final Square from, final Square to) {
        this(from, to, null);
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
        return this.from;
    }

    /**
     * @return
     */
    public Square getTarget() {
        return this.to;
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
