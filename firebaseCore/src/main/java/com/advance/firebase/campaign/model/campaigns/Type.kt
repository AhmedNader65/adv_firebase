package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = TypeSerializer::class)
enum class Type {
  Dialog4,

  FreeTrialAccess,

  Subscription,

  Survey,

  UNKNOWN
}

object TypeSerializer : KSerializer<Type> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Type", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Type) {
    encoder.encodeString(value.name)
  }

  override fun deserialize(decoder: Decoder): Type {
    val name = decoder.decodeString()
    return Type.entries.find { it.name.equals(name, ignoreCase = true) } ?: Type.UNKNOWN
  }
}
