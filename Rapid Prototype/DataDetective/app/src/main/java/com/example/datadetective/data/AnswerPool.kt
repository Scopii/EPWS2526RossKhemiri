package com.example.datadetective.data

object AnswerPool {
    val allAnswers = listOf("Werteachse wurde verkürzt/Keine Null Baseline",
        "Median statt Mittelwert verwendet",
        "Zu kleine Stichprobe",
        "Irreführende Farbwahl",
        "Fehlender Kontext",
        "Keine Manipulation vorhanden",
        "Unvollständige Datenbasis",
        "Korrelation wird als Kausalität dargestellt",
        "Hier könnte ihre Werbung stehen!",
        "Kategorieachse wurde verkürzt"
        )

    fun correctAnswerFor(type: ManipulationType): String =
        when(type){
            ManipulationType.TRUNCATED_VALUE_AXIS -> "Werteachse wurde verkürzt/Keine Null Baseline"
            ManipulationType.TRUNCATED_CATEGORY_AXIS -> "Kategorieachse wurde verkürzt"

            else -> {"keine"}
        }

}
