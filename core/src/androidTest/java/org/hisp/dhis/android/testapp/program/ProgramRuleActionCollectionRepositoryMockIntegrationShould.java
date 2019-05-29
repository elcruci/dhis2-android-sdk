/*
 * Copyright (c) 2004-2019, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.android.testapp.program;

import androidx.test.runner.AndroidJUnit4;

import org.hisp.dhis.android.core.utils.integration.mock.BaseMockIntegrationTestFullDispatcher;
import org.hisp.dhis.android.core.program.ProgramRuleAction;
import org.hisp.dhis.android.core.program.ProgramRuleActionType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class ProgramRuleActionCollectionRepositoryMockIntegrationShould extends BaseMockIntegrationTestFullDispatcher {

    @Test
    public void find_all() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .get();

        assertThat(programRuleActions.size(), is(3));
    }

    @Test
    public void filter_by_data() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byData()
                        .eq("data")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_content() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byContent()
                        .eq("The hemoglobin value cannot be above 99")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_tracked_entity_attribute() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byTrackedEntityAttributeUid()
                        .eq("cejWyOfXge6")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_program_indicator() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byProgramIndicatorUid()
                        .eq("GSae40Fyppf")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_program_stage_section() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byProgramStageSectionUid()
                        .eq("bbjzL5gp0NZ")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_program_rule_action_type() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byProgramRuleActionType()
                        .eq(ProgramRuleActionType.SHOWWARNING)
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_program_stage() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byProgramStageUid()
                        .eq("dBwrot7S420")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_data_element() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byDataElementUid()
                        .eq("Ok9OQpitjQr")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_program_rule() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byProgramRuleUid()
                        .eq("GC4gpdoSD4r")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_option() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byOptionUid()
                        .eq("egT1YqFWsVk")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

    @Test
    public void filter_by_option_group() {
        List<ProgramRuleAction> programRuleActions =
                d2.programModule().programRuleActions
                        .byOptionGroupUid()
                        .eq("j3JYGVCIEdz")
                        .get();

        assertThat(programRuleActions.size(), is(1));
    }

}