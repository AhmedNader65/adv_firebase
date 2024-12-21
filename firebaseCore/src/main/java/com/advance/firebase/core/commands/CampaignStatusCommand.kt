package com.advance.firebase.core.commands

import android.content.Context
import com.advance.firebase.Constant
import com.advance.firebase.Constant.REMOTE_CONFIG_MAIN_NODE
import com.advance.firebase.Constant.TRIAL_CAMPAIGNS
import com.advance.firebase.Constant.TRIAL_CAMPAIGNS_COMPLETE
import com.advance.firebase.Constant.TRIAL_COMPLETE_STATUS
import com.advance.firebase.Constant.TRIAL_INIT_STATUS
import com.advance.domain.firebase.prefs.Prefs
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class CampaignStatusCommand(
  private val context: Context,
  private val prefs: Prefs
) {

  fun execute(value: Long = TRIAL_INIT_STATUS) {
    val reference =
      Firebase.database.reference.child(REMOTE_CONFIG_MAIN_NODE)
        .child(Constant.deviceId(context))
        .child(TRIAL_CAMPAIGNS)
        .child(prefs.activePromotionId)
        .child(TRIAL_CAMPAIGNS_COMPLETE)

    reference.get().addOnSuccessListener {
      reference.setValue(value)

      if (value == TRIAL_COMPLETE_STATUS) {
        prefs.activePromotionId = ""
        prefs.activePromotionDate = ""
      }
    }.addOnFailureListener {
      Timber.e("CampaignStatusCommand: Error getting data $it")
    }
  }
}
