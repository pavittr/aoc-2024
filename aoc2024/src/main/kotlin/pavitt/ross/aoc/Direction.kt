package pavitt.ross.aoc

enum class Direction(val xchange: Int, val yChange: Int) {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);
    fun nextPoint(point: Point) : Point {
        return Point(point.x+this.xchange, point.y+ this.yChange)
    }
    fun turnRight() : Direction {
        return when(this) {
            NORTH -> EAST
            SOUTH -> WEST
            EAST -> SOUTH
            WEST -> NORTH
        }
    }
}