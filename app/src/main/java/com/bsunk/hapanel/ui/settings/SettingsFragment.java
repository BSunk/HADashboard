package com.bsunk.hapanel.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;


import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.utils.PreferencesUtils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityComponent mActivityComponent;

    @Inject
    DataManager dataManager;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        setPreferencesFromResource(R.xml.app_preferences, s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        EditTextPreference toolbarTitlePref = (EditTextPreference) getPreferenceManager().findPreference("pref_toolbar_title");

        //Sets summary and initial dialog message for change_title_edit_text_pref
        String titleName = dataManager.getSharedPrefHelper().getToolbarTitle();
        toolbarTitlePref.setSummary(getString(R.string.preference_edit_text_summary_change_toolbar_title, titleName));
        toolbarTitlePref.setDialogMessage(titleName);

    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(getActivity()))
                    .applicationComponent(HAApplication.get(getActivity()).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "pref_screen_on":
                boolean keepScreenOn = findPreference(key).getSharedPreferences().getBoolean("pref_screen_on", false);
                PreferencesUtils.keepScreenOn(keepScreenOn, getActivity());
                break;
            case "pref_toolbar_title":
                Preference pref = findPreference(key);
                String titleName = dataManager.getSharedPrefHelper().getToolbarTitle();
                pref.setSummary(getString(R.string.preference_edit_text_summary_change_toolbar_title, titleName));

        }
    }
}
