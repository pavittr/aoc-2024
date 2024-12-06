package pavitt.ross.aoc

class Grid(private val gridSpots: Map<Point,String>) {
    fun get(point: Point) : String {
        return gridSpots[point] ?: "E"
    }
    fun adapt(newValues : Map<Point, String>) : Grid {
        val newGridspots = gridSpots.toMutableMap()
        newGridspots.putAll(newValues)
        return Grid(newGridspots)
    }
    fun find(elem: String) : Point {
        return gridSpots.toList().first { it.second == elem }.first
    }
    @Suppress("unused")
    fun printable(overrides: Map<Point, String>) : String {
        val lowestx = gridSpots.minBy { it.key.x }.key.x - 1
        val highestx = gridSpots.maxBy { it.key.x }.key.x + 1
        val lowesty = gridSpots.minBy { it.key.y }.key.y - 1
        val highesty = gridSpots.maxBy { it.key.y}.key.y + 1
        val emptypoints = IntRange(lowesty, highesty).map { y-> IntRange(lowestx, highestx).map { x-> Pair(Point(x,y), "E") }.toList() }.flatten().toMap().toMutableMap()
        emptypoints.putAll(gridSpots)
        emptypoints.putAll(overrides)

        return emptypoints.toList().groupBy { it.first.y }.map { (k, v) ->
            k to v.sortedBy { it.first.x }
                .joinToString("") { it.second }
        }.toList().sortedBy { it.first }.joinToString("\n") { it.second }
    }
}
