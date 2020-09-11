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
package org.hisp.dhis.android.core.arch.db.access.internal

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.hisp.dhis.android.core.D2Factory
import org.hisp.dhis.android.core.maintenance.D2Error
import org.hisp.dhis.android.core.mockwebserver.Dhis2MockServer
import org.hisp.dhis.android.core.utils.runner.D2JunitRunner
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(D2JunitRunner::class)
class DatabaseImportExportFromDatabaseAssetsMockIntegrationShould {

    companion object LocalAnalyticsAggregatedLargeDataMockIntegrationShould {

        val context = InstrumentationRegistry.getInstrumentation().context
        val server = Dhis2MockServer(60809)
        val importer = TestDatabaseImporter()

        const val expectedDatabaseName = "localhost-60809_android_unencrypted.db"

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            server.setRequestDispatcher()
        }

        @AfterClass
        @JvmStatic
        fun temarDownClass() {
            server.shutdown()
        }
    }

    @After
    fun tearDown() {
        context.deleteDatabase(expectedDatabaseName)
    }

    @Test
    fun import_database_when_not_logged() {
        importer.copyDatabaseFromAssets()

        val d2 = D2Factory.forNewDatabase()

        d2.maintenanceModule().databaseImportExport().importDatabase(importer.databaseFile(context))

        d2.userModule().blockingLogIn("android", "Android123", "http://localhost:60809/")

        assertThat(d2.programModule().programs().blockingCount()).isEqualTo(2)
    }

    @Test(expected = D2Error::class)
    fun fail_when_logged_in() {
        importer.copyDatabaseFromAssets()

        val d2 = D2Factory.forNewDatabase()

        d2.userModule().blockingLogIn("other", "Pw1010", "http://localhost:60809/")

        d2.maintenanceModule().databaseImportExport().importDatabase(importer.databaseFile(context))
    }

    @Test(expected = D2Error::class)
    fun fail_when_database_exists() {
        importer.copyDatabaseFromAssets(expectedDatabaseName)

        val d2 = D2Factory.forNewDatabase()

        d2.maintenanceModule().databaseImportExport().importDatabase(importer.databaseFile(context, expectedDatabaseName))
    }
}