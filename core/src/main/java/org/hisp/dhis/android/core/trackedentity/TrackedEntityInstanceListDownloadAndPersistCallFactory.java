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

package org.hisp.dhis.android.core.trackedentity;

import org.hisp.dhis.android.core.arch.api.executors.APICallExecutor;
import org.hisp.dhis.android.core.common.D2CallExecutor;
import org.hisp.dhis.android.core.common.Payload;
import org.hisp.dhis.android.core.maintenance.D2Error;
import org.hisp.dhis.android.core.maintenance.ForeignKeyCleaner;
import org.hisp.dhis.android.core.systeminfo.DHISVersionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.Reusable;
import retrofit2.Call;

@Reusable
public final class TrackedEntityInstanceListDownloadAndPersistCallFactory {

    private final ForeignKeyCleaner foreignKeyCleaner;
    private final TrackedEntityInstanceRelationshipDownloadAndPersistCallFactory
            relationshipsCallFactory;
    private final TrackedEntityInstancePersistenceCallFactory persistenceCallFactory;
    private final D2CallExecutor d2CallExecutor;
    private final APICallExecutor apiCallExecutor;
    private final DHISVersionManager versionManager;
    private final TrackedEntityInstanceService trackedEntityInstanceService;


    @Inject
    TrackedEntityInstanceListDownloadAndPersistCallFactory(
            ForeignKeyCleaner foreignKeyCleaner,
            TrackedEntityInstanceRelationshipDownloadAndPersistCallFactory relationshipsCallFactory,
            TrackedEntityInstancePersistenceCallFactory persistenceCallFactory,
            D2CallExecutor d2CallExecutor,
            APICallExecutor apiCallExecutor,
            DHISVersionManager versionManager,
            TrackedEntityInstanceService trackedEntityInstanceService) {
        this.foreignKeyCleaner = foreignKeyCleaner;
        this.relationshipsCallFactory = relationshipsCallFactory;
        this.persistenceCallFactory = persistenceCallFactory;
        this.d2CallExecutor = d2CallExecutor;
        this.apiCallExecutor = apiCallExecutor;
        this.versionManager = versionManager;
        this.trackedEntityInstanceService = trackedEntityInstanceService;
    }

    public Callable<List<TrackedEntityInstance>> getCall(final Collection<String> trackedEntityInstanceUids) {
        return new Callable<List<TrackedEntityInstance>>() {
            @Override
            public List<TrackedEntityInstance> call() throws Exception {
                return downloadAndPersist(trackedEntityInstanceUids);
            }
        };
    }

    private List<TrackedEntityInstance> downloadAndPersist(final Collection<String> trackedEntityInstanceUids)
            throws D2Error {

        return d2CallExecutor.executeD2CallTransactionally(new Callable<List<TrackedEntityInstance>>() {
            @Override
            public List<TrackedEntityInstance> call() throws Exception {
                if (trackedEntityInstanceUids == null) {
                    throw new IllegalArgumentException("UID list is null");
                }

                List<TrackedEntityInstance> teis = new ArrayList<>();

                for (String uid : trackedEntityInstanceUids) {
                    Call<Payload<TrackedEntityInstance>> teiCall =
                            trackedEntityInstanceService.getTrackedEntityInstance(uid,
                                    TrackedEntityInstanceFields.allFields, true);

                    teis.addAll(apiCallExecutor.executePayloadCall(teiCall));
                }

                d2CallExecutor.executeD2Call(persistenceCallFactory.getCall(teis));

                if (!versionManager.is2_29()) {
                    d2CallExecutor.executeD2Call(relationshipsCallFactory.getCall());
                }

                foreignKeyCleaner.cleanForeignKeyErrors();

                return teis;
            }
        });
    }
}