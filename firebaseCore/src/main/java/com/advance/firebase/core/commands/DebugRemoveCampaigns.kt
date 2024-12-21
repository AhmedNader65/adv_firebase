package com.advance.firebase.core.commands

import android.content.Context
import com.advance.firebase.Constant
import com.advance.firebase.Constant.REMOTE_CONFIG_MAIN_NODE
import com.advance.firebase.Constant.TRIAL_CAMPAIGNS
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DebugRemoveCampaigns(
  private val context: Context
) {

  fun execute() {
    Firebase.database.reference.child(REMOTE_CONFIG_MAIN_NODE)
      .child(Constant.deviceId(context))
      .child(TRIAL_CAMPAIGNS).setValue(null)
  }
}
