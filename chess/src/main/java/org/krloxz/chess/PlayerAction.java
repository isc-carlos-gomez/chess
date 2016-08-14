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
 * Encapsulates the possible actions that a player can take along his turn. Please note that this class is visitable to
 * enable the <b>Visitor Pattern</b> and improve processing of all the different kind of actions.
 *
 * @author Carlos Gomez
 */
public interface PlayerAction {

    /**
     * Enables the <b>Visitor Pattern</b> by accepting a {@link PlayerActionVisitor}.
     * 
     * @param visitor
     *        a {@link PlayerActionVisitor} which will visit and process this action.
     * @return the result of visiting this action.
     */
    <R> R accept(PlayerActionVisitor<R> visitor);

}
