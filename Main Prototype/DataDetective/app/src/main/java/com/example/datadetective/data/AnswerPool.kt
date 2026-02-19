package com.example.datadetective.data

object AnswerPool {
    val allAnswers = listOf("Werteachse wurde verkürzt/Keine Null Baseline",
        "Median statt Mittelwert verwendet",
        "Zu kleine Stichprobe",
        "Irreführende Farbwahl/Farb-Highlighting",
        "Fehlender Kontext",
        "Keine Manipulation vorhanden",
        "Unvollständige Datenbasis",
        "Korrelation wird als Kausalität dargestellt",
        "Kategorieachse wurde verkürzt",
        "Balkenproportionen sind falsch"
    )

    fun correctAnswerFor(type: ManipulationType): String =
        when(type){
            ManipulationType.TRUNCATED_VALUE_AXIS -> "Werteachse wurde verkürzt/Keine Null Baseline"
            ManipulationType.TRUNCATED_CATEGORY_AXIS -> "Kategorieachse wurde verkürzt"
            ManipulationType.DISTORTED_BAR_LENGTH ->  "Balkenproportionen sind falsch"
            ManipulationType.COLOR_HIGHLIGHTING ->  "Irreführende Farbwahl/Farb-Highlighting"
            else -> {"keine"}
        }
    fun correctAnswersFor(types: List<ManipulationType>): List<String> { //richtig(e) antworte(n) als liste
        return types.map { correctAnswerFor(it) }
    }


}
