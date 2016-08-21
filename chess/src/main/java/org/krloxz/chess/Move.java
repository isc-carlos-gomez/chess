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
 * Encapsulates a chess move generated in a player turn. Please note that, for a more convenient processing, a move is
 * also a visitable player action.
 *
 * @author Carlos Gomez
 */
public class Move implements PlayerAction {

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
    public Position getFrom() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public Position getTo() {
        // TODO Auto-generated method stub
        return null;
    }

}
