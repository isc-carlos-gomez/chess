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

import java.util.Optional;

/**
 * @author Carlos Gomez
 */
public class PlayingAgent {

    private final RulesBook book;

    /**
     * @param book
     */
    public PlayingAgent(final RulesBook book) {
        this.book = book;
    }

    /**
     * @param move
     * @param board
     * @return
     */
    public boolean playMove(final Move move, final Board board) {
        Optional<Piece> optional = board.getPieceAt(move.getSource());
        if (!optional.isPresent()
                || !this.book.isLegalMove(optional.get().getType(), move.getSource(), move.getTarget())
                || !board.isPathClear(move.getSource(), move.getTarget())) {
            return false;
        }
        final Piece pieceAtSource = optional.get();
        Piece pieceAtTarget = null;
        PieceMemento targetMemento = null;
        optional = board.getPieceAt(move.getTarget());
        if (optional.isPresent()) {
            pieceAtTarget = optional.get();
            if (pieceAtSource.isFellow(pieceAtTarget)) {
                return false;
            } else {
                targetMemento = pieceAtTarget.captured();
            }
        }
        final PieceMemento sourceMemento = pieceAtSource.move(move.getTarget());
        if (board.isKingInCheck()) {
            pieceAtSource.restoreTo(sourceMemento);
            if (targetMemento != null) {
                pieceAtTarget.restoreTo(targetMemento);
            }
            return false;
        }
        return true;
    }

}
