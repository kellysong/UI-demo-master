package com.sjl.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.sjl.util.PrefUtils;
import com.sjl.uidemo.R;
import com.orhanobut.logger.Logger;


public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager()
                .findPreference(getString(R.string.save_net_mode));

        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            /**
             * @param preference The changed Preference.
             * @param newValue   The new value of the Preference.
             * @return True to update the state of the Preference with the new value.
             */
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                boolean checked = Boolean.valueOf(newValue.toString());
                PrefUtils.setSaveNetMode(checked);
                Logger.d("Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;

            }
        });

        final CheckBoxPreference checkboxPref1 = (CheckBoxPreference) getPreferenceManager()
                .findPreference("test2");

        checkboxPref1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            /**
             * @param preference The changed Preference.
             * @param newValue   The new value of the Preference.
             * @return True to update the state of the Preference with the new value.
             */
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                boolean checked = Boolean.valueOf(newValue.toString());
                PrefUtils.setSaveNetMode(checked);
                Logger.d("Pref2 " + preference.getKey() + " changed to " + newValue.toString());
                return true;

            }
        });
    }
}
