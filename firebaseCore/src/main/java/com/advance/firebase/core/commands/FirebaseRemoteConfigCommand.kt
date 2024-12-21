package com.advance.firebase.core.commands

import com.advance.command.processor.Command
import com.advance.firebase.core.remoteConfig.AdvanceRemoteConfig

class FirebaseRemoteConfigCommand(
    private val advanceRemoteConfig: AdvanceRemoteConfig
) : Command {

    override fun execute() {
        advanceRemoteConfig.start()
    }
}
