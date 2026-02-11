import com.example.datadetective.data.ChartData
import com.example.datadetective.data.ChartType
import com.example.datadetective.data.ManipulationType

val infoData = listOf(
    ChartData( //https://rstudio-pubs-static.s3.amazonaws.com/896731_e3db3297e40140b3b1e0cd56cad43485.html
        id = 101,
        title = "Percentage of Americans that identify as Christians (2009/2019)",
        yValues = listOf(77f,65f),
        xData = listOf("2009", "2019"),
        unit = "%",
        description = "Quelle: Fox News Diagramm",
        supportedChartTypes = setOf(ChartType.BAR),
        supportedManipulations = setOf(ManipulationType.TRUNCATED_VALUE_AXIS),
        fixedMinValue = 58f,
        fixedMaxValue = 78f,
        explanation = "Die Y-Achse beginnt nicht bei null, dadurch wirkt der Unterschied deutlich größer"
    ),
    ChartData( //https://mediakompetent.de/manipulation-mit-daten-erkennen/
        id = 102,
        title = "Unemployment Rate under presdident Obama",
        yValues = listOf(9.0f, 8.9f, 8.8f, 9.0f, 9.1f,9.2f,9.1f,9.1f,9.1f,9.0f,8.6f,),
        xData = listOf("JAN", "FEB", "MAR", "APR", "MAY","JUN", "JUL", "AUG", "SEP", "OCT","NOV"),
        unit = "%",
        description = "Quelle: Fox News Diagramm",
        supportedChartTypes = setOf(ChartType.LINE),
        supportedManipulations = setOf(ManipulationType.TRUNCATED_VALUE_AXIS),
        fixedMinValue = 7.5f,
        fixedMaxValue = 10f,
        explanation = "Die Y-Achse beginnt nicht bei null, dadurch wirkt es als würde es signifikante veränderungen bei der Arbeitslosenquote geben, obwohl in Wahrheit der Wert ziemlich stabil bei 9% liegt "
    ),
    ChartData( //https://callingbullshit.org/tools/tools_misleading_axes.html
        id = 103,
        title = "Average number of actual weekly hours of work in main job, full-time employees, 2013",
        yValues = listOf(37.4f, 38.0f, 38.1f, 38.6f, 39.2f,39.5f,39.5f,39.7f,39.8f,39.8f,40.4f,40.8f,41.2f),
        xData = listOf("France", "Italy", "Belgium", "Sweden", "Hungary","Spain","EU-28 average","Czech Republic","Solvak Republiv","Netherlands","Poland","Germany","UK","Romania"),
        unit = "h",
        description = "Quelle: Eurofound2014",
        supportedChartTypes = setOf(ChartType.HORIZONTAL_BAR),
        supportedManipulations = setOf(ManipulationType.TRUNCATED_VALUE_AXIS),
        fixedMinValue = 36f,
        fixedMaxValue = 42f,
        explanation = "Die Y-Achse beginnt nicht bei null, wodurch die Unterschiede größer wirken"
    ),
    ChartData(
        id = 201,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("A", "B", "C"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.BAR),
        supportedManipulations = setOf(ManipulationType.TRUNCATED_CATEGORY_AXIS),
        fixedCategoryRange = 1..3,
        explanation = "TODO"
    )
    ,
    ChartData(
        id = 202,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.HORIZONTAL_BAR),
        supportedManipulations = setOf(ManipulationType.TRUNCATED_CATEGORY_AXIS),
        fixedCategoryRange = 1..3,
        explanation = "TODO"
    ),
    ChartData(
        id = 301,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.BAR),
        supportedManipulations = setOf(ManipulationType.DISTORTED_BAR_LENGTH),
        fixedIntensity = 1.8f,
        explanation = "TODO"
    ),
    ChartData(
        id = 302,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.HORIZONTAL_BAR),
        supportedManipulations = setOf(ManipulationType.DISTORTED_BAR_LENGTH),
        fixedIntensity = 1.8f,
        explanation = "TODO"
    ),
    ChartData(
        id = 401,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.BAR),
        supportedManipulations = setOf(ManipulationType.COLOR_HIGHLIGHTING),
        fixedCategoryRange = 1..1,
        explanation = "TODO"
    ),
    ChartData(
        id = 402,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.LINE),
        supportedManipulations = setOf(ManipulationType.COLOR_HIGHLIGHTING),
        fixedCategoryRange = 1..1,
        explanation = "TODO"
    ),
    ChartData(
        id = 403,
        title = "TODO",
        yValues = listOf(1f,3f,7f),
        xData = listOf("TODO", "TODO", "TODO"),
        unit = "TODO",
        description = "TODO",
        supportedChartTypes = setOf(ChartType.HORIZONTAL_BAR),
        supportedManipulations = setOf(ManipulationType.COLOR_HIGHLIGHTING),
        fixedCategoryRange = 1..1,
        explanation = "TODO"
    )
)