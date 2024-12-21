package com.advance.firebase.core

import com.advance.command.processor.OrdersCommandProcessor
import com.advance.firebase.core.commands.FirebaseDatabaseCommand
import com.advance.firebase.core.commands.FirebaseRemoteConfigCommand

class FirebaseRepository(
  private val firebaseDatabaseCommand: FirebaseDatabaseCommand,
  private val firebaseRemoteConfigCommand: FirebaseRemoteConfigCommand,
) {

  fun start() {
    OrdersCommandProcessor()
      .addToQueue(firebaseRemoteConfigCommand)
      .addToQueue(firebaseDatabaseCommand)
      .processCommand()
  }
}
