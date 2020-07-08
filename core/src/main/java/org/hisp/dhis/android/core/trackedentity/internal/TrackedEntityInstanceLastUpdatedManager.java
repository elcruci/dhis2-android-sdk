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

package org.hisp.dhis.android.core.trackedentity.internal;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.time.DateUtils;
import org.hisp.dhis.android.core.arch.db.stores.internal.ObjectStore;
import org.hisp.dhis.android.core.program.internal.ProgramDataDownloadParams;
import org.hisp.dhis.android.core.settings.DownloadPeriod;
import org.hisp.dhis.android.core.settings.ProgramSetting;
import org.hisp.dhis.android.core.settings.ProgramSettings;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
class TrackedEntityInstanceLastUpdatedManager {

    private final ObjectStore<TrackedEntityInstanceSync> store;

    private Map<String, TrackedEntityInstanceSync> byProgram;
    private ProgramSettings programSettings;
    private ProgramDataDownloadParams params;

    @Inject
    TrackedEntityInstanceLastUpdatedManager(ObjectStore<TrackedEntityInstanceSync> store) {
        this.store = store;
    }

    void refresh(ProgramSettings programSettings, ProgramDataDownloadParams params) {
        this.programSettings = programSettings;
        this.params = params;

        List<TrackedEntityInstanceSync> elements = store.selectAll();
        byProgram = new HashMap<>(elements.size());
        for (TrackedEntityInstanceSync sync : elements) {
            byProgram.put(sync.program(), sync);
        }
    }

    Date getLastUpdated(@Nullable String programId) {
        if (params.uids().isEmpty()) {
            TrackedEntityInstanceSync sync = byProgram.get(programId);
            return sync == null ? getInitialLastUpdated(programId) : sync.lastUpdated();
        } else {
            return null;
        }
    }

    Date getInitialLastUpdated(String programUid) {
        DownloadPeriod period = null;
        if (programSettings != null) {
            ProgramSetting specificSetting = programSettings.specificSettings().get(programUid);
            ProgramSetting globalSetting = programSettings.globalSettings();

            if (hasUpdateDownload(specificSetting)) {
                period = specificSetting.updateDownload();
            } else if (hasUpdateDownload(globalSetting)) {
                period = globalSetting.updateDownload();
            }
        }

        if (period == null || period == DownloadPeriod.ANY) {
            return null;
        } else {
            return DateUtils.addMonths(new Date(), -period.getMonths());
        }
    }


    private boolean hasUpdateDownload(ProgramSetting programSetting) {
        return programSetting != null && programSetting.updateDownload() != null;
    }
}