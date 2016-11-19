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
public class BoardBroker {

    private final Board board;
    private final MovementPolicy movementPolicy;

    /**
     * @param movementPolicy
     */
    public BoardBroker(final Board board, final MovementPolicy movementPolicy) {
        this.board = board;
        this.movementPolicy = movementPolicy;
    }

    /**
     * @param movement
     * @return
     */
    public boolean updateBoard(final Movement movement) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param movement
     * @param board
     * @return
     */
    public boolean updateBoard(final BasicMovement movement) {
        Optional<Piece> optional = this.board.getPieceAt(movement.getSource());
        if (!optional.isPresent()
                || !this.movementPolicy.isLegalMove(optional.get().getType(), movement.getSource(),
                        movement.getTarget())
                || !this.board.isPathClear(movement.getSource(), movement.getTarget())) {
            return false;
        }
        final Piece pieceAtSource = optional.get();
        Piece pieceAtTarget = null;
        PieceMemento targetMemento = null;
        optional = this.board.getPieceAt(movement.getTarget());
        if (optional.isPresent()) {
            pieceAtTarget = optional.get();
            if (pieceAtSource.isFellow(pieceAtTarget)) {
                return false;
            } else {
                targetMemento = pieceAtTarget.captured();
            }
        }
        final PieceMemento sourceMemento = pieceAtSource.move(movement.getTarget());
        if (this.board.isKingInCheck()) {
            pieceAtSource.restoreTo(sourceMemento);
            if (targetMemento != null) {
                pieceAtTarget.restoreTo(targetMemento);
            }
            return false;
        }
        return true;
    }

}
