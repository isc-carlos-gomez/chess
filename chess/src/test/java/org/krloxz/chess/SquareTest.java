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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test {@link Square}.
 *
 * @author Carlos Gomez
 */
public class SquareTest {

    @Test
    public void create() {
        final char[] files = "abcdefgh".toCharArray();
        final char[] ranks = "12345678".toCharArray();
        for (int i = 0; i < files.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                final Square square = new Square("" + files[i] + ranks[j]);
                assertEquals(i, square.getX());
                assertEquals(j, square.getY());
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOnShortSanLabel() {
        new Square("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOnLongSanLabel() {
        new Square("a1a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOnIllegalSanLabel() {
        new Square("a9");
    }

}
