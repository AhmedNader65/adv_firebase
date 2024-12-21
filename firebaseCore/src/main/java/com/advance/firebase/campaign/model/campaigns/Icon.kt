package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = IconSerializer::class)
enum class Icon {

  Tick,
  UNKNOWN
}

object IconSerializer : KSerializer<Icon> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Icon", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Icon) {
    encoder.encodeString(value.name)
  }

  override fun deserialize(decoder: Decoder): Icon {
    val name = decoder.decodeString()
    return Icon.entries.find { it.name.equals(name, ignoreCase = true) } ?: Icon.UNKNOWN
  }
}
