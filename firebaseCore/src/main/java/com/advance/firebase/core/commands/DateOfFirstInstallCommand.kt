package com.advance.firebase.core.commands

import com.advance.firebase.Constant.REMOTE_CONFIG_DATE_OF_FIRST_INSTALL
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.DatabaseReference
import org.joda.time.DateTime
import timber.log.Timber
import org.joda.time.format.ISODateTimeFormat

class DateOfFirstInstallCommand(private val prefs: Prefs) {

  fun execute(databaseReference: DatabaseReference) {

    val firstVersionInstalledNode = databaseReference
      .child(REMOTE_CONFIG_DATE_OF_FIRST_INSTALL)

    firstVersionInstalledNode.get().addOnSuccessListener {
      if (it.exists().not()) {
        addValue(firstVersionInstalledNode)
      } else {
        prefs.dateOfFirstInstall = it.value as String
      }
    }.addOnFailureListener {
      Timber.e("DateOfFirstInstallCommand: Error getting data $it")
    }
  }

  private fun addValue(firstVersionInstalledNode: DatabaseReference) {
    val date = ISODateTimeFormat.date().print(DateTime())
    firstVersionInstalledNode.setValue(
      date
    )
      .addOnSuccessListener {
        prefs.dateOfFirstInstall = date
      }
      .addOnFailureListener {
        Timber.e("Failure ${it.localizedMessage}")
      }
  }
}
