package com.github.dhaval2404.weatherapp.ui.setting_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.dhaval2404.weatherapp.BuildConfig
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.constant.AppConstant
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Show app settings Using Jetpack Preference
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class SettingPrefFragment : PreferenceFragmentCompat() {

    private val mViewModel: SettingViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_setting)
    }

    private fun <T : Preference> findPreference(keyRes: Int): T? {
        return findPreference<T>(getString(keyRes))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // App Version
        val prefAppVersion: Preference? = findPreference(R.string.pref_key_app_version)
        prefAppVersion?.summary = "v${BuildConfig.VERSION_NAME}"

        // Github Repository
        val prefGithubRepo: Preference? = findPreference(R.string.pref_key_github_repo)
        prefGithubRepo?.summary = AppConstant.GITHUB_REPOSITORY_URL
        prefGithubRepo?.setOnPreferenceClickListener {
            openGithubRepo()
            true
        }

        // Open source library used
        val prefOpenSource: Preference? = findPreference(R.string.pref_key_open_source)
        prefOpenSource?.setOnPreferenceClickListener {
            openOssLicenses()
            true
        }

        // Clear city bookmarks
        val prefResetCityBookmarks: Preference? =
            findPreference(R.string.pref_key_reset_city_bookmark)
        prefResetCityBookmarks?.setOnPreferenceClickListener {
            clearCityBookMarks()
            true
        }
    }

    /**
     * View github repository
     */
    private fun openGithubRepo() {
        val uri = Uri.parse(AppConstant.GITHUB_REPOSITORY_URL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * Show open source licenses
     */
    private fun openOssLicenses() {
        val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
        startActivity(intent)
    }

    /**
     * Clear City Bookmarks
     */
    private fun clearCityBookMarks() {
        mViewModel.clearCityBookmarks()

        // Show success message
        Toast.makeText(
            requireContext(),
            R.string.message_city_bookmark_cleared,
            Toast.LENGTH_SHORT
        ).show()
    }
}
