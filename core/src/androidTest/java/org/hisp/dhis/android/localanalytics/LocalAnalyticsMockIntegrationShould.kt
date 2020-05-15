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
package org.hisp.dhis.android.localanalytics

import com.google.common.truth.Truth.assertThat
import org.hisp.dhis.android.core.utils.integration.mock.BaseMockIntegrationTestLocalAnalyticsDispatcher
import org.hisp.dhis.android.core.utils.runner.D2JunitRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(D2JunitRunner::class)
class LocalAnalyticsMockIntegrationShould : BaseMockIntegrationTestLocalAnalyticsDispatcher() {

    @Test
    fun check_user() {
        val userExists = d2.userModule().user().blockingExists()
        assertThat(userExists).isTrue()
    }

    @Test
    fun check_organisation_units() {
        val count = d2.organisationUnitModule().organisationUnits().blockingCount()
        assertThat(count).isEqualTo(13)
    }

    @Test
    fun check_category_combos() {
        val count = d2.categoryModule().categoryCombos().blockingCount()
        assertThat(count).isEqualTo(3)
    }

    @Test
    fun check_category_option_combos() {
        val count = d2.categoryModule().categoryOptionCombos().blockingCount()
        assertThat(count).isEqualTo(8)
    }

    @Test
    fun check_data_elements() {
        val count = d2.dataElementModule().dataElements().blockingCount()
        assertThat(count).isEqualTo(30)
    }

    @Test
    fun check_periods() {
        val count = d2.periodModule().periods().blockingCount()
        assertThat(count).isAtLeast(100)
    }

    @Test
    fun check_programs() {
        val count = d2.programModule().programs().blockingCount()
        assertThat(count).isEqualTo(2)
    }

    @Test
    fun check_program_stages() {
        val count = d2.programModule().programStages().blockingCount()
        assertThat(count).isEqualTo(4)
    }

    @Test
    fun check_tracked_entity_attributes() {
        val count = d2.trackedEntityModule().trackedEntityAttributes().blockingCount()
        assertThat(count).isEqualTo(10)
    }
}