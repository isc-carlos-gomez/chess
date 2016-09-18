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
 * Encapsulates a board square. The square is internally represented as a coordinate pair (x, y).
 *
 * @author Carlos Gomez
 */
public class Square {

    private static final String FILES = "abcdefgh";
    private static final String RANKS = "12345678";

    private final int x;
    private final int y;

    /**
     * @param file
     * @param rank
     */
    public Square(final File file, final Rank rank) {
        this.x = 0;
        this.y = 0;
    }

    /**
     * @param sanLabel
     */
    public Square(final String sanLabel) {
        if (sanLabel.length() != 2) {
            throw new IllegalArgumentException("Illegal SAN label: " + sanLabel);
        }
        this.x = FILES.indexOf(sanLabel.charAt(0));
        this.y = RANKS.indexOf(sanLabel.charAt(1));
        if (this.x < 0 || this.y < 0) {
            throw new IllegalArgumentException("Illegal SAN label: " + sanLabel);
        }
    }

    /**
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return
     */
    public Piece getPiece() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public boolean isOccupied() {
        // TODO Auto-generated method stub
        return false;
    }

}
