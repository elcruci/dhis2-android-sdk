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

package org.hisp.dhis.android.testapp.datavalue;

import org.hisp.dhis.android.core.common.State;
import org.hisp.dhis.android.core.datavalue.DataValueObjectRepository;
import org.hisp.dhis.android.core.maintenance.D2Error;
import org.hisp.dhis.android.core.utils.integration.mock.BaseMockIntegrationTestFullDispatcher;
import org.hisp.dhis.android.core.utils.runner.D2JunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(D2JunitRunner.class)
public class DataValueObjectRepositoryMockIntegrationShould extends BaseMockIntegrationTestFullDispatcher {

    @Test
    public void update_value() throws D2Error {
        String value = "new_value";

        DataValueObjectRepository repository = objectRepository();

        repository.set(value);
        assertThat(repository.get().value(), is(value));

        repository.delete();
    }

    @Test
    public void update_follow_up() throws D2Error {
        Boolean followUp = Boolean.TRUE;

        DataValueObjectRepository repository = objectRepository();

        repository.setFollowUp(followUp);
        assertThat(repository.get().followUp(), is(followUp));

        repository.delete();
    }

    @Test
    public void update_comment() throws D2Error {
        String comment = "comment";

        DataValueObjectRepository repository = objectRepository();

        repository.setComment(comment);
        assertThat(repository.get().comment(), is(comment));

        repository.delete();
    }

    @Test
    public void delete_value_if_state_to_post() throws D2Error {
        DataValueObjectRepository repository = objectRepository();

        repository.set("value");
        assertThat(repository.exists(), is(Boolean.TRUE));
        assertThat(repository.get().state(), is(State.TO_POST));
        repository.delete();
        assertThat(repository.exists(), is(Boolean.FALSE));
    }

    @Test
    public void set_state_to_delete_if_state_is_not_to_post() throws D2Error {
        DataValueObjectRepository repository = objectRepository();

        repository.set("value");
        repository.set("other_value");
        assertThat(repository.exists(), is(Boolean.TRUE));
        assertThat(repository.get().state(), is(State.TO_UPDATE));
        repository.delete();
        assertThat(repository.exists(), is(Boolean.TRUE));
        assertThat(repository.get().state(), is(State.TO_DELETE));
    }

    @Test
    public void return_that_a_value_exists_only_if_it_has_been_created() {
        assertThat(d2.dataValueModule().dataValues
                .value("no_period", "no_org_unit", "no_data_element",
                        "no_category", "no_attribute").exists(), is(Boolean.FALSE));
        assertThat(d2.dataValueModule().dataValues
                .value("201809", "DiszpKrYNg8", "g9eOBujte1U",
                        "Gmbgme7z9BF", "bRowv6yZOF2").exists(), is(Boolean.TRUE));
    }

    private DataValueObjectRepository objectRepository() {
        return d2.dataValueModule().dataValues
                .value("201905", "DiszpKrYNg8", "g9eOBujte1U",
                        "Gmbgme7z9BF", "bRowv6yZOF2");
    }
}