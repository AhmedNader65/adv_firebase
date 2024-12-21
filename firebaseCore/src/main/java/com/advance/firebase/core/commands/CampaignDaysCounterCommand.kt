package com.advance.firebase.core.commands

import android.content.Context
import com.advance.command.processor.Command
import com.advance.firebase.Constant
import com.advance.firebase.Constant.REMOTE_CONFIG_MAIN_NODE
import com.advance.firebase.Constant.TRIAL_CAMPAIGNS
import com.advance.firebase.Constant.TRIAL_CAMPAIGNS_DATE_OF_FIRST_ACTIVATE
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

import com.google.firebase.database.ktx.database
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import timber.log.Timber

class CampaignDaysCounterCommand(
  private val context: Context,
  private val prefs: Prefs
) : Command {

  override fun execute() {

    val reference =
      Firebase.database.reference.child(REMOTE_CONFIG_MAIN_NODE)
        .child(Constant.deviceId(context))
        .child(TRIAL_CAMPAIGNS)
        .child(prefs.activePromotionId)
        .child(TRIAL_CAMPAIGNS_DATE_OF_FIRST_ACTIVATE)

    reference.get().addOnSuccessListener {
      if (it.exists().not()) {
        addValue(reference)
      } else {
        prefs.activePromotionDate = it.value as String
      }
    }.addOnFailureListener {
      Timber.e("CampaignDaysCounterCommand: Error getting data $it")
    }
  }

  private fun addValue(firstVersionInstalledNode: DatabaseReference) {
    val date = ISODateTimeFormat.date().print(DateTime())
    firstVersionInstalledNode.setValue(
      date
    )
      .addOnSuccessListener {
        prefs.activePromotionDate = date
      }
      .addOnFailureListener {
        Timber.e("Failure ${it.localizedMessage}")
      }
  }
}
