/*
 * The MIT License
 *
 * Copyright 2018 Intuit Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.core;

import com.intuit.karate.StepDefs;
import java.util.Collection;

/**
 *
 * @author pthomas3
 */
public class MandatoryTagHook implements ExecutionHook {

    @Override
    public boolean beforeScenario(Scenario scenario, StepDefs stepDefs) {
        Collection<Tag> tags = scenario.getTagsEffective();
        boolean found = false;
        for (Tag tag : tags) {
            if ("testId".equals(tag.getName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("testId tag not present at line: " + scenario.getLine());
        }
        return true;
    }

    @Override
    public void afterScenario(ScenarioResult result, StepDefs stepDefs) {
        
    }    

    @Override
    public void beforeStep(Step step, StepDefs stepDefs) {

    }

    @Override
    public void afterStep(StepResult result, StepDefs stepDefs) {

    }
    
}
