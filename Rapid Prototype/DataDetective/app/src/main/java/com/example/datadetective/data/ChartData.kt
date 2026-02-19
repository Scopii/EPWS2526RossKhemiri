package com.example.datadetective.data


//Klasse für Unverändertern Datensatz als Grundlage
data class ChartData(
    val id: Int,
    val title: String,
    val yValues: List<Float>,
    val xData: List<String>,
    val unit: String,
    val description: String,
    val supportedChartTypes: Set<ChartType>,
    val explanation: String? = null,
)


//Beispielhafte unmanipulierte Datensätze (für Bar-, HorizontalBar- und Linechart)
val sampleDataSet = listOf(
    ChartData(
        id = 1,
        title = "Durchschnittsgehalt 2020-2024",
        yValues = listOf(38000f, 39000f, 40000f, 41000f, 42000f),
        xData = listOf("2020", "2021", "2022", "2023", "2024"),
        unit = "€",
        description = "Jährliche Durchschnittsgehälter in Deutschland",
        supportedChartTypes = setOf(ChartType.BAR,ChartType.HORIZONTAL_BAR)),
    ChartData(
        id = 2,
        title = "Kino-Umsatz 2019-2024",
        yValues = listOf(1024f, 318f, 373f, 722f, 929f, 868f),
        xData = listOf("2019","2020", "2021", "2022", "2023", "2024"),
        unit = "Mio.€",
        description = "Jährlicher Kino-Umsatz in Deutschland",
        supportedChartTypes = setOf(ChartType.BAR,ChartType.HORIZONTAL_BAR)),
    ChartData(
        id = 3,
        title = "Arbeitslosenquote 2015–2024",
        yValues = listOf(6.4f, 6.1f, 5.7f, 5.2f, 4.9f, 6.0f, 5.8f, 5.5f, 5.3f, 5.1f),
        xData = listOf("2015","2016","2017","2018","2019","2020","2021","2022","2023","2024"),
        unit = "%",
        description = "Entwicklung der Arbeitslosenquote in Deutschland",
        supportedChartTypes = setOf(ChartType.LINE)),
    ChartData(
        id = 4,
        title = "Durschnittstemperatur 2015–2024",
        yValues = listOf(9.1f,9.3f,9.4f,9.6f,9.7f,9.8f,10.0f,10.1f,10.3f,10.4f),
        xData = listOf("2015","2016","2017","2018","2019","2020","2021","2022","2023","2024"),
        unit = "°C",
        description = "Jährliche durchschnittliche Temperatur in Deutschland",
        supportedChartTypes = setOf(ChartType.LINE))
)



