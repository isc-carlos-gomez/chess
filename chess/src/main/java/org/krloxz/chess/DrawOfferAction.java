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

import java.util.function.Supplier;

/**
 * @author Carlos Gomez
 */
public final class DrawOfferAction implements PlayerAction {

    private final Supplier<PlayerAction> rejectFunction;

    /**
     * @param rejectFunction
     */
    public DrawOfferAction(final Supplier<PlayerAction> rejectFunction) {
        this.rejectFunction = rejectFunction;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.player.TurnAction#accept(org.krloxz.chess.player.TurnActionVisitor)
     */
    @Override
    public <R> R accept(final PlayerActionVisitor<R> visitor) {
        return visitor.visit(this);
    }

    /**
     * @return
     */
    public PlayerAction rejected() {
        return this.rejectFunction.get();
    }

}
