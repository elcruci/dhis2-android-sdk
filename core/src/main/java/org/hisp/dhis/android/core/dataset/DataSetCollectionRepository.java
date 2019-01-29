/*
 * Copyright (c) 2017, University of Oslo
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
package org.hisp.dhis.android.core.dataset;

import org.hisp.dhis.android.core.arch.repositories.children.ChildrenAppender;
import org.hisp.dhis.android.core.arch.repositories.collection.CollectionRepositoryFactory;
import org.hisp.dhis.android.core.arch.repositories.collection.ReadOnlyIdentifiableCollectionRepositoryImpl;
import org.hisp.dhis.android.core.arch.repositories.filters.FilterConnectorFactory;
import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScopeItem;
import org.hisp.dhis.android.core.common.IdentifiableObjectStore;
import org.hisp.dhis.android.core.common.ObjectStyleChildrenAppender;
import org.hisp.dhis.android.core.common.ObjectStyleStoreImpl;
import org.hisp.dhis.android.core.data.database.DatabaseAdapter;
import org.hisp.dhis.android.core.indicator.DataSetIndicatorChildrenAppender;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class DataSetCollectionRepository
        extends ReadOnlyIdentifiableCollectionRepositoryImpl<DataSet, DataSetCollectionRepository> {

    private DataSetCollectionRepository(final IdentifiableObjectStore<DataSet> store,
                                         final Collection<ChildrenAppender<DataSet>> childrenAppenders,
                                         List<RepositoryScopeItem> scope) {
        super(store, childrenAppenders, scope, new FilterConnectorFactory<>(scope,
                new CollectionRepositoryFactory<DataSetCollectionRepository>() {

                    @Override
                    public DataSetCollectionRepository newWithScope(List<RepositoryScopeItem> updatedScope) {
                        return new DataSetCollectionRepository(store, childrenAppenders, updatedScope);
                    }
                }));
    }
    static DataSetCollectionRepository create(DatabaseAdapter databaseAdapter) {
        ChildrenAppender<DataSet> objectStyleChildrenAppender =
                new ObjectStyleChildrenAppender<DataSet, DataSet.Builder>(
                        ObjectStyleStoreImpl.create(databaseAdapter),
                        DataSetTableInfo.TABLE_INFO
                );

        return new DataSetCollectionRepository(
                DataSetStore.create(databaseAdapter),
                Arrays.asList(
                        objectStyleChildrenAppender,
                        SectionChildrenAppender.create(databaseAdapter),
                        DataSetCompulsoryDataElementOperandChildrenAppender.create(databaseAdapter),
                        DataInputPeriodChildrenAppender.create(databaseAdapter),
                        DataSetElementChildrenAppender.create(databaseAdapter),
                        DataSetIndicatorChildrenAppender.create(databaseAdapter)
                ),
                Collections.<RepositoryScopeItem>emptyList()
        );
    }
}
