/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.simple_decision_with_user_function;

import com.gs.dmn.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        AnnotationSet annotationSet = new AnnotationSet();
        assertEquals(true, decision.apply(decision.number("-1"), annotationSet));
        assertEquals(true, decision.apply(decision.number("0"), annotationSet));
        assertEquals(true, decision.apply(decision.number("1"), annotationSet));
        assertEquals(true, decision.apply(decision.number("20"), annotationSet));
        assertEquals(false, decision.apply(decision.number("21"), annotationSet));
        assertEquals(false, decision.apply(decision.number("22"), annotationSet));
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply((String)null, annotationSet);
    }
}
