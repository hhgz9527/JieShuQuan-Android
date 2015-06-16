package com.thoughtworks.jieshuquan_android.activity.main.mine;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.thoughtworks.jieshuquan_android.BuildConfig;
import com.thoughtworks.jieshuquan_android.R;

public class SettingsFragment extends PreferenceFragment {

    private static final String PREF_VERSION = "version";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        findPreference(PREF_VERSION).setSummary(BuildConfig.VERSION_NAME);
    }
}
