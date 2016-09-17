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
 * A visitor that executes and realize player actions generated in a single turn. The result of visiting actions is a
 * boolean highlighting whether the action was processed and accepted (true) or processed and rejected (false).<br>
 * <br>
 * Please note that it's mandatory to create new instances to process different turns.
 *
 * @author Carlos Gomez
 */
public class TurnProcessingActionVisitor implements ActionVisitor<Boolean> {

    private boolean drawAlreadyOffered;
    private final Board board;
    private final PlayerStrategy strategy;
    private final Player opponent;

    /**
     * Creates a new instance.
     *
     * @param board
     *        the board where the game is being played.
     * @param strategy
     *        the strategy of the player in turn, which should be using this visitor.
     * @param opponent
     *        the opponent of the player in turn, which should be using this visitor.
     */
    public TurnProcessingActionVisitor(final Board board, final PlayerStrategy strategy, final Player opponent) {
        this.board = board;
        this.drawAlreadyOffered = false;
        this.strategy = strategy;
        this.opponent = opponent;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerActionVisitor#visit(org.krloxz.chess.Move)
     */
    @Override
    public Boolean visit(final Move move) {
        if (this.board.update(move)) {
            return true;
        } else {
            this.strategy.actionRejected(move);
            return false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerActionVisitor#visit(org.krloxz.chess.DrawOfferAction)
     */
    @Override
    public Boolean visit(final DrawOffer action) {
        if (this.drawAlreadyOffered) {
            throw new IllegalStateException("Draw can't be offered twice in a turn");
        }
        this.drawAlreadyOffered = true;
        if (this.opponent.acceptDraw()) {
            return true;
        } else {
            this.strategy.actionRejected(action);
            return false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.PlayerActionVisitor#visit(org.krloxz.chess.Resignation)
     */
    @Override
    public Boolean visit(final Resignation resignation) {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.ActionVisitor#visit(org.krloxz.chess.GameEnding)
     */
    @Override
    public Boolean visit(final GameEnding gameEnding) {
        return true;
    }

}
