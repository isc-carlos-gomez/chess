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
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * Unit tests {@link PlayerActionExecutor}.
 *
 * @author Carlos Gomez
 */
public class PlayerActionExecutorTest {

    @Test
    public void execute() {
        final PlayerActionExecutor executor = new PlayerActionExecutor();
        final PlayerAction action = mock(PlayerAction.class);
        when(action.accept(notNull(PlayerActionExecutorVisitor.class))).thenReturn(action);
        final PlayerAction result = executor.execute(action);
        assertEquals(action, result);
    }

}
