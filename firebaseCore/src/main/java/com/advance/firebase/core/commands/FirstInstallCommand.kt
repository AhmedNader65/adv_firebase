package com.advance.firebase.core.commands

import com.advance.domain.affiliateinfo.AffiliateInfo
import com.advance.firebase.Constant.REMOTE_CONFIG_VERSION_OF_FIRST_INSTALL
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.DatabaseReference
import timber.log.Timber

class FirstInstallCommand(
  private val prefs: Prefs,
  private val affiliateInfo: AffiliateInfo
) {

  fun execute(databaseReference: DatabaseReference) {

    val reference = databaseReference
      .child(REMOTE_CONFIG_VERSION_OF_FIRST_INSTALL)

    reference.get().addOnSuccessListener {
      if (it.exists().not()) {
        addValue(reference)
      } else {
        prefs.firstVersionInstalled = it.value as String
      }
    }.addOnFailureListener {
      Timber.e("FirstInstallCommand: Error getting data $it")
    }
  }

  private fun addValue(firstVersionInstalledNode: DatabaseReference) {

    firstVersionInstalledNode.setValue(
      affiliateInfo.buildNumber
    )
      .addOnSuccessListener {
        prefs.dateOfFirstInstall = affiliateInfo.buildNumber
      }
      .addOnFailureListener {
        Timber.e("Failure ${it.localizedMessage}")
      }
  }
}
