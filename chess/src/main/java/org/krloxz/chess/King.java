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
public class King {

    protected static final Square INITIAL_POSITION = new Square("e1");

    private final KingMovingStyle movingStyle;
    private Square position;

    /**
     * @param light
     * @param kingMovingStyle
     */
    public King(final KingMovingStyle kingMovingStyle) {
        this.movingStyle = kingMovingStyle;
        this.position = INITIAL_POSITION;
    }

    /**
     * @param target
     * @param board
     * @return
     */
    public boolean move(final Square target, final Board board) {
        if (this.movingStyle.isLegalMove(this.position, target, board)) {
            final InSquareSpecification specification = new InSquareSpecification();
            final Optional<Piece> optional = board.findPiece(specification);
            if (optional.isPresent()) {
                optional.get().captured();
            }
            this.position = target;
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public Square getPosition() {
        return this.position;
    }

}
