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

/**
 * @author Carlos Gomez
 */
public final class TurnActionGameStatusVisitor implements PlayerActionVisitor<GameState> {

    private final PlayerStrategy strategy;
    private final Player opponent;
    private boolean drawAlreadyOffered;

    /**
     * @param strategy
     * @param opponent
     */
    public TurnActionGameStatusVisitor(final PlayerStrategy strategy, final Player opponent) {
        this.strategy = strategy;
        this.opponent = opponent;
        this.drawAlreadyOffered = false;
    }

    /**
     * @param action
     * @return
     */
    @Override
    public GameState visit(final DrawOfferAction action) {
        if (this.drawAlreadyOffered) {
            throw new IllegalStateException("A draw offer can not be made twice in the same turn");
        }
        if (this.opponent.acceptDraw()) {
            return GameState.DRAW_BY_AGREEMENT;
        } else {
            this.drawAlreadyOffered = true;
            return null;
            // return action.rejected().accept(this);
        }
    }

}
