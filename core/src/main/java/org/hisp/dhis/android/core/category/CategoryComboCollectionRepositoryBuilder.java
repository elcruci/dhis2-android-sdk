/*
 * Copyright (c) 2004-2018, University of Oslo
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

package org.hisp.dhis.android.core.category;

import org.hisp.dhis.android.core.arch.db.scope.RepositoryScopeItem;

import java.util.ArrayList;
import java.util.List;

final class CategoryComboCollectionRepositoryBuilder {

    private final CategoryComboCollectionRepository collectionRepository;
    private final List<RepositoryScopeItem> scope;
    private final String key;

    CategoryComboCollectionRepositoryBuilder(CategoryComboCollectionRepository collectionRepository,
                                             List<RepositoryScopeItem> scope,
                                             String key) {
        this.collectionRepository = collectionRepository;
        this.scope = scope;
        this.key = key;
    }

    CategoryComboCollectionRepository isEqualTo(String value) {
        return newWithScope("eq", value);
    }

    CategoryComboCollectionRepository like(String value) {
        return newWithScope("like", value);
    }


    private List<RepositoryScopeItem> updatedScope(String operator, String value) {
        List<RepositoryScopeItem> copiedScope = new ArrayList<>();
        for (RepositoryScopeItem si: scope) {
            copiedScope.add(si);
        }
        copiedScope.add(RepositoryScopeItem.builder().key(key).operator(operator).value(value).build());
        return copiedScope;
    }

    private CategoryComboCollectionRepository newWithScope(String operator, String value) {
        return collectionRepository.newWithUpdatedScope(updatedScope(operator, value));
    }
}
