package com.advance.firebase.campaign.model.campaigns

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = TextSerializer::class)
enum class Text {
  AndMore,

  BreakingNews,

  Traffic,

  Weather,

  UNKNOWN
}

object TextSerializer : KSerializer<Text> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Text", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Text) {
    encoder.encodeString(value.name)
  }

  override fun deserialize(decoder: Decoder): Text {
    val name = decoder.decodeString()
    return Text.entries.find { it.name.equals(name, ignoreCase = true) } ?: Text.UNKNOWN
  }
}
