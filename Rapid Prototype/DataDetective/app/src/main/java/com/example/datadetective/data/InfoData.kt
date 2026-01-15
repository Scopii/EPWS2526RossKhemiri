import com.example.datadetective.data.ChartData
import com.example.datadetective.data.ChartType

val infoData = listOf(
    ChartData(
        id = 1,
        title = "Serienkiller by Country",
        yValues = listOf(3615f, 196f, 190f, 137f, 121f),
        xData = listOf("US", "Russia", "U.K.", "Japan", "South Africa"),
        unit = "",
        description = "Die USA haben die meisten Mörder produziert, signifikant mehr als Russland auf Platzt 2",
        supportedChartTypes = setOf(ChartType.HORIZONTAL_BAR),
        explanation = "X Y Z so und so ist das"
    ),
)