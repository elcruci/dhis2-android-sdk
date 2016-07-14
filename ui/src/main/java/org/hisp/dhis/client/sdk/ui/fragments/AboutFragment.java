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

package org.hisp.dhis.client.sdk.ui.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hisp.dhis.client.sdk.android.api.D2;
import org.hisp.dhis.client.sdk.ui.R;

import java.util.Locale;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        super.onCreateView(inflater, container, state);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView aboutSessionText = (TextView) getActivity().findViewById(R.id.about_session);
        TextView aboutAppText = (TextView) getActivity().findViewById(R.id.about_app);

        // inside about_session:
        aboutSessionText.setText(String.format(Locale.getDefault(), "%s: %s\n",
                getString(R.string.username),
                D2.me().userCredentials()
                        .toBlocking().first()
                        .getUsername()));
        aboutSessionText.append(String.format(Locale.getDefault(), "%s: %s\n",
                getString(R.string.server_url),
                "<server-url>"
        ));
        aboutSessionText.append(String.format(Locale.getDefault(), "%s: %s\n",
                "Server version",
                "<server-version>"
        ));
        //TODO: fill in the text in <></>he textViews:

        // inside about_app:
        PackageManager packageManager = getContext().getPackageManager();
        ApplicationInfo applicationInfo = null;
        String appName = "";
        try {
            applicationInfo = packageManager.getApplicationInfo(getContext().getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }

        if (applicationInfo != null) {
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
        }

        aboutAppText.setText(String.format(Locale.getDefault(), "%s: %s\n",
                "App Name",
                appName
        ));
        aboutAppText.append(String.format(Locale.getDefault(), "%s: %s\n",
                getString(R.string.app_version),
                "<version>"));
    }
}
