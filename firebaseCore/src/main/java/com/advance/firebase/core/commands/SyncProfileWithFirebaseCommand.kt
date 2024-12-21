package com.advance.firebase.core.commands

import android.os.Build
import com.advance.domain.affiliateinfo.AffiliateInfo
import com.advance.firebase.Constant.REMOTE_CONFIG_BC_MAPPING
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SyncProfileWithFirebaseCommand(
  private val prefs: Prefs,
  private val affiliateInfo: AffiliateInfo
) {

  val TAG = "SyncProfileWithFirebaseCommand"

  fun execute(notificationPermission: String, blueConicId: String) {
    Firebase.database.reference
      .child(REMOTE_CONFIG_BC_MAPPING)
      .child(blueConicId).setValue(fillUpValues(notificationPermission, blueConicId))
  }

  private fun fillUpValues(
    notificationPermission: String,
    blueConicId: String
  ): Map<String, String> {
    return mapOf(
      "BlueConicID" to blueConicId,
      "appFirstInstallDate" to prefs.dateOfFirstInstall,
      "appFirstInstallVersion" to prefs.firstVersionInstalled,
      "appVersion" to affiliateInfo.buildNumber,
      "deviceModel" to Build.MODEL,
      "deviceOSVersion" to getAndroidVersion(),
      "notificationPermission" to notificationPermission,
      "subscriptionActive" to prefs.subscriptionStatus.toString()
    )
  }

  private fun getAndroidVersion(): String {
    val release: String = Build.VERSION.RELEASE
    val sdkVersion: Int = Build.VERSION.SDK_INT
    return "Android SDK: $sdkVersion ($release)"
  }
}
