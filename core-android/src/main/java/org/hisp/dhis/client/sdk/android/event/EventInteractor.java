/*
 * Copyright (c) 2016, University of Oslo
 *
 * All rights reserved.
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

package org.hisp.dhis.client.sdk.android.event;

import org.hisp.dhis.client.sdk.core.common.controllers.SyncStrategy;
import org.hisp.dhis.client.sdk.models.common.importsummary.ImportSummary;
import org.hisp.dhis.client.sdk.models.common.state.Action;
import org.hisp.dhis.client.sdk.models.common.state.State;
import org.hisp.dhis.client.sdk.models.event.Event;
import org.hisp.dhis.client.sdk.models.organisationunit.OrganisationUnit;
import org.hisp.dhis.client.sdk.models.program.Program;
import org.hisp.dhis.client.sdk.models.program.ProgramStage;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;

public interface EventInteractor {

    Event create(OrganisationUnit organisationUnit, Program program, ProgramStage programStage,
                 Event.EventStatus status);

    Observable<Boolean> save(Event event);

    Observable<Boolean> remove(Event event);

    Observable<Event> get(long id);

    Observable<Event> get(String uid);

    Observable<State> get(Event event);

    Observable<Map<Long, State>> map(List<Event> events);

    Observable<List<Event>> list();

    Observable<List<Event>> list(OrganisationUnit organisationUnit, Program program);

    Observable<List<Event>> listByActions(Set<Action> actionSet);

    Observable<List<Event>> pull(Set<String> uids);

    Observable<List<Event>> pull(SyncStrategy strategy, Set<String> uids);

    Observable<List<Event>> pull(String organisationUnit, String program);

    Observable<List<Event>> pull(String organisationUnit,
            String program, String startDate, int maxEvents);

    Observable<List<Event>> pull(String organisationUnit,
            String program, String startDate, String endDate,
            int maxEvents);

    Observable<List<Event>> pull(OrganisationUnit organisationUnit, Program program);

    Observable<List<Event>> pull(String organisationUnit, String program, int maxEvents);

    Observable<List<Event>> pull(OrganisationUnit organisationUnit, Program program, int maxEvents);

    Observable<List<Event>> pull(String organisationUnit, String program, Date startDate, int maxEvents);

    Observable<List<Event>> pull(OrganisationUnit organisationUnit, Program program, Date startDate, int maxEvents);

    Observable<List<Event>> pull(String organisationUnit, String program, Date startDate, Date endDate, int maxEvents);

    Observable<List<Event>> pull(OrganisationUnit organisationUnit, Program program, Date startDate, Date endDate, int maxEvents);

    Observable<Map<Event,ImportSummary>> push(Set<String> uids);

    Observable<List<Event>> sync(Set<String> uids);

    Observable<List<Event>> sync(SyncStrategy strategy, Set<String> uids);
}
