package org.hisp.dhis.android.core.relationship;

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

public class RelationshipTypeEndPointCallShould {

    @Mock
    private DatabaseAdapter databaseAdapter;

    @Mock
    private RelationshipTypeStore relationshipTypeStore;

    @Mock
    private ResourceHandler resourceHandler;

    @Mock
    private RelationshipTypeHandler relationshipTypeHandler;

    Dhis2MockServer dhis2MockServer;
    Retrofit retrofit;

    @Test(expected = IllegalArgumentException.class)
    public void throws_illegal_argument_exception_if_uids_size_exceeds_the_limit() {
        givenARelationShipTypeEndPointCall(MAX_UIDS + 1);
    }

    @Test
    public void create_relationship_type_call_if_uids_size_does_not_exceeds_the_limit() {
        RelationshipTypeEndPointCall relationshipTypeEndPointCall =
                givenARelationShipTypeEndPointCall(MAX_UIDS);
        assertThat(relationshipTypeEndPointCall, is(notNullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void throws_illegal_argument_exception_calling_it_two_times() throws Exception {
        RelationshipTypeEndPointCall relationshipTypeEndPointCall =
                givenARelationShipTypeEndPointCall(MAX_UIDS);
        dhis2MockServer.enqueueMockResponse();
        relationshipTypeEndPointCall.call();
        relationshipTypeEndPointCall.call();
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

    private RelationshipTypeEndPointCall givenARelationShipTypeEndPointCall(int uidsSize) {
        Set<String> uIds = new HashSet<>();
        RelationshipTypeService relationshipTypeService = retrofit.create(
                RelationshipTypeService.class);

        for (int i = 0; i < uidsSize; i++) {
            uIds.add("uid" + i);
        }
        RelationshipTypeFactory relationshipTypeFactory = new RelationshipTypeFactory(
                databaseAdapter, retrofit, resourceHandler, relationshipTypeStore);

        return relationshipTypeFactory.newEndPointCall(uIds, new Date());
    }
}
