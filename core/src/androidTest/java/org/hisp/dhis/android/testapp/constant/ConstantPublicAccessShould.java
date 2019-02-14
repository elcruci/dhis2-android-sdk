package org.hisp.dhis.android.testapp.constant;

import android.support.test.runner.AndroidJUnit4;

import org.hisp.dhis.android.core.constant.Constant;
import org.hisp.dhis.android.testapp.arch.BasePublicAccessShould;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(AndroidJUnit4.class)
public class ConstantPublicAccessShould extends BasePublicAccessShould<Constant> {

    @Mock
    private Constant object;

    @Override
    public Constant object() {
        return object;
    }

    @Override
    public void has_public_create_method() {
        Constant.create(null);
    }

    @Override
    public void has_public_builder_method() {
        Constant.builder();
    }

    @Override
    public void has_public_to_builder_method() {
        object().toBuilder();
    }
}