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

import java.util.Arrays;
import java.util.List;

/**
 * @author Carlos Gomez
 */
public class Square {

    private static final List<Character> FILE_LABELS = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

    private final int identifier;

    /**
     * Creates an square based on Standard Algebraic Notation.
     *
     * @param recordedMove
     */
    public Square(final String san) {
        final int file = FILE_LABELS.indexOf(san.charAt(0));
        final String identifier = file + san.substring(1);
        this.identifier = Integer.parseInt(identifier);
    }

    /**
     * @return
     */
    public int getIdentifier() {
        return this.identifier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object instanceof Square) {
            final Square other = (Square) object;
            return this.identifier == other.identifier;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.identifier;
    }

}
