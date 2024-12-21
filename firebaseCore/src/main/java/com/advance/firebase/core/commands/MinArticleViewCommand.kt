package com.advance.firebase.core.commands

import android.content.Context
import com.advance.command.processor.Command
import com.advance.firebase.Constant
import com.advance.firebase.Constant.REMOTE_CONFIG_MAIN_NODE
import com.advance.firebase.Constant.REMOTE_CONFIG_MIN_ARTICLE_VIEW
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import timber.log.Timber

class MinArticleViewCommand(
  private val context: Context,
  private val prefs: Prefs
) : Command {

  override fun execute() {

    val reference = Firebase.database.reference
      .child(REMOTE_CONFIG_MAIN_NODE)
      .child(Constant.deviceId(context))
      .child(REMOTE_CONFIG_MIN_ARTICLE_VIEW)

    reference.get().addOnSuccessListener {
      Timber.d("Got value ${it.value}")
      if (it.exists().not()) {
        addValue(reference)
      } else {
        addValue(reference, (it.value as Long) + 1)
      }
    }.addOnFailureListener {
      Timber.e("MinArticleViewCommand: Error getting data $it")
    }
  }

  private fun addValue(firstVersionInstalledNode: DatabaseReference, value: Long = 0) {
    firstVersionInstalledNode.setValue(
      value
    )
      .addOnSuccessListener {
        prefs.minArticleViews = value
        Timber.d("SAVED Success here ")
      }
      .addOnFailureListener {
        Timber.e("Failure ${it.localizedMessage}")
      }
  }
}
