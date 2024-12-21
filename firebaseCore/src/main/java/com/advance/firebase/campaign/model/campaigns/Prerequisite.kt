package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = PrerequisiteSerializer::class)
enum class Prerequisite {

  NotificationsNotEnabled,
  UserNotSubscribed,
  UNKNOWN
}

object PrerequisiteSerializer : KSerializer<Prerequisite> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Prerequisite", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Prerequisite) {
    encoder.encodeString(value.name)
  }

  override fun deserialize(decoder: Decoder): Prerequisite {
    val name = decoder.decodeString()
    return Prerequisite.entries.find { it.name.equals(name, ignoreCase = true) } ?: Prerequisite.UNKNOWN
  }
}
