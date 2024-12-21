package com.advance.firebase.core.commands

import android.content.Context
import com.advance.firebase.Constant
import com.advance.domain.firebase.prefs.Prefs
import com.google.android.gms.common.data.DataBufferSafeParcelable.addValue
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import javax.inject.Inject

class FirebaseMobileIdCommand @Inject constructor(private val context: Context, private val prefs: Prefs) {

  fun execute() {
    val reference = Firebase.database.reference.child(Constant.REMOTE_CONFIG_MAIN_NODE)
      .child(Constant.deviceId(context))
      .child(Constant.REMOTE_MUID)

    reference.get().addOnSuccessListener {
      if (!prefs.firebaseMuid.isNullOrEmpty()) {
        addValue(reference, prefs.firebaseMuid!!)
      } else {
        prefs.firebaseMuid = it.value as String?
      }
    }.addOnFailureListener {
      Timber.e("FirebaseMobileIdCommand: Error getting data $it")
    }
  }

  private fun addValue(firstVersionInstalledNode: DatabaseReference, value: String) {
    firstVersionInstalledNode.setValue(
      value
    )
      .addOnSuccessListener {
        prefs.firebaseMuid = value
      }
      .addOnFailureListener {
        Timber.e("Failure ${it.localizedMessage}")
      }
  }
}
