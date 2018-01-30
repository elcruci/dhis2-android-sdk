package org.hisp.dhis.android.core.trackedentity;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hisp.dhis.android.core.calls.Call.MAX_UIDS;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hisp.dhis.android.core.data.api.FieldsConverterFactory;
import org.hisp.dhis.android.core.data.api.FilterConverterFactory;
import org.hisp.dhis.android.core.data.database.DatabaseAdapter;
import org.hisp.dhis.android.core.data.file.ResourcesFileReader;
import org.hisp.dhis.android.core.data.server.Dhis2MockServer;
import org.hisp.dhis.android.core.resource.ResourceHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TrackedEntityAttributeEndPointCallShould {

    @Mock
    private DatabaseAdapter databaseAdapter;

    @Mock
    private Date serverDate;

    @Mock
    private TrackedEntityAttributeHandler trackedEntityAttributeHandler;

    @Mock
    private ResourceHandler resourceHandler;

    Dhis2MockServer dhis2MockServer;
    Retrofit retrofit;

    @Test(expected = IllegalArgumentException.class)
    public void throws_illegal_argument_exception_if_uids_size_exceeds_the_limit() {
        TrackedEntityAttributeEndPointCall trackedEntityAttributeEndPointCall =
                givenATrackedEntityAttributeEndPointCallByUIds(MAX_UIDS + 1);
    }

    @Test
    public void create_tracked_entity_attribute_call_if_uids_size_does_not_exceeds_the_limit() {
        TrackedEntityAttributeEndPointCall trackedEntityAttributeEndPointCall =
                givenATrackedEntityAttributeEndPointCallByUIds(MAX_UIDS);
        assertThat(trackedEntityAttributeEndPointCall, is(notNullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void throws_illegal_argument_exception_calling_it_two_times() throws Exception {
        TrackedEntityAttributeEndPointCall trackedEntityAttributeEndPointCall =
                givenATrackedEntityAttributeEndPointCallByUIds(MAX_UIDS);
        dhis2MockServer.enqueueMockResponse();
        trackedEntityAttributeEndPointCall.call();
        trackedEntityAttributeEndPointCall.call();
    }


    @Before
    public void setUp() throws IOException {
        dhis2MockServer = new Dhis2MockServer(new ResourcesFileReader());

        retrofit = new Retrofit.Builder()
                .baseUrl(dhis2MockServer.getBaseEndpoint())
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .addConverterFactory(FilterConverterFactory.create())
                .addConverterFactory(FieldsConverterFactory.create())
                .build();

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws IOException {
        dhis2MockServer.shutdown();
    }

    private TrackedEntityAttributeEndPointCall givenATrackedEntityAttributeEndPointCallByUIds(
            int uidsSize) {
        TrackedEntityAttributeService trackedEntityAttributeService=retrofit.create(TrackedEntityAttributeService.class);
        Set<String> uIds = new HashSet<>();

        for (int i = 0; i < uidsSize; i++) {
            uIds.add("uid" + i);
        }
        TrackedEntityAttributeQuery trackedEntityAttributeQuery =
                TrackedEntityAttributeQuery.Builder
                        .create()
                        .withUIds(uIds)
                        .build();

        TrackedEntityAttributeEndPointCall trackedEntityAttributeEndPointCall =
                new TrackedEntityAttributeEndPointCall(trackedEntityAttributeService,
                        databaseAdapter, trackedEntityAttributeQuery, new Date(),
                        trackedEntityAttributeHandler, resourceHandler);

        return trackedEntityAttributeEndPointCall;
    }

}
