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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link DrawOfferAction}.
 *
 * @author Carlos Gomez
 */
public class DrawOfferActionTest {

    private DrawOfferAction action;
    private TurnAction anyAction;

    @Before
    public void setUp() {
        this.action = new DrawOfferAction(() -> rejectFunction());
        this.anyAction = mock(TurnAction.class);
    }

    @Test
    public void accept() {
        // Arrange
        final String visitorResult = "Result";
        @SuppressWarnings("unchecked")
        final TurnActionVisitor<String> visitor = mock(TurnActionVisitor.class);
        when(visitor.visit(this.action)).thenReturn(visitorResult);

        // Act
        final String result = this.action.accept(visitor);

        // Assert
        assertEquals(visitorResult, result);
    }

    @Test
    public void rejected() {
        // Act
        final TurnAction result = this.action.rejected();

        // Assert
        assertEquals(rejectFunction(), result);
    }

    private TurnAction rejectFunction() {
        return this.anyAction;
    }

}
