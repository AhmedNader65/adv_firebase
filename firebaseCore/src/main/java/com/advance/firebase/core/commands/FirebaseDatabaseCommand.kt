package com.advance.firebase.core.commands

import android.content.Context
import com.advance.command.processor.Command
import com.advance.firebase.Constant
import com.advance.firebase.Constant.REMOTE_CONFIG_MAIN_NODE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDatabaseCommand(
  private val context: Context,
  private val dateOfFirstInstallCommand: DateOfFirstInstallCommand,
  private val firstInstallCommand: FirstInstallCommand,
  private val firebaseMobileIdCommand: FirebaseMobileIdCommand

) : Command {

  override fun execute() {

    val reference = Firebase.database.reference
      .child(REMOTE_CONFIG_MAIN_NODE)
      .child(Constant.deviceId(context))

    dateOfFirstInstallCommand.execute(reference)
    firstInstallCommand.execute(reference)
    firebaseMobileIdCommand.execute()
  }
}
