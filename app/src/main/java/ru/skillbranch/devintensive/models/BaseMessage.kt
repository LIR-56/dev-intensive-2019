package ru.skillbranch.devintensive.models

import android.service.voice.AlwaysOnHotwordDetector
import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage(): String

    companion object AbstractFactory {

        var lastId = -1
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            payload: Any?,
            type: String,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            return when (type) {
                "text" -> TextMessage(lastId.toString(), from, chat, isIncoming, date, payload as String?)
                "image" -> ImageMessage(lastId.toString(), from, chat, isIncoming, date, payload as String?)
                else -> throw NotImplementedError()
            }

        }
    }

}