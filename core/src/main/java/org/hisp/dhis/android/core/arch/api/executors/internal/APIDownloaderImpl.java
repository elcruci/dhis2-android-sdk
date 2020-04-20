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

package org.hisp.dhis.android.core.arch.api.executors.internal;

import org.hisp.dhis.android.core.arch.api.payload.internal.Payload;
import org.hisp.dhis.android.core.arch.handlers.internal.Handler;
import org.hisp.dhis.android.core.arch.helpers.CollectionsHelper;
import org.hisp.dhis.android.core.arch.helpers.internal.FunctionalCollectionHelper;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

final class APIDownloaderImpl implements APIDownloader {

    @Override
    public <P> Maybe<List<P>> downloadPartitioned(Set<String> uids, int pageSize, Handler<P> handler,
                                                  Function<Set<String>, Single<Payload<P>>> pageDownloader) {
        return downloadPartitioned(uids, pageSize, handler, pageDownloader, null);
    }


    @Override
    public <P> Maybe<List<P>> downloadPartitioned(Set<String> uids, int pageSize, Handler<P> handler,
                                                  Function<Set<String>, Single<Payload<P>>> pageDownloader,
                                                  @Nullable Function<P, P> transform) {
        List<Set<String>> partitions = CollectionsHelper.setPartition(uids, pageSize);
        return Observable.fromIterable(partitions)
                .flatMapSingle(pageDownloader)
                .map(Payload::items)
                .reduce((items, items2) -> {
                    items.addAll(items2);
                    return items;
                })
                .map(items -> {
                    if (transform == null) {
                        return items;
                    } else {
                        return FunctionalCollectionHelper.map(items, transform);
                    }
                })
                .doOnSuccess(handler::handleMany);

    }
}