package pavitt.ross.aoc

enum class Direction(val xchange: Int, val yChange: Int) {
    NORTH(0, -1),
    NORTHEAST(1,-1),
    EAST(1, 0),
    SOUTHEAST(1,1),
    SOUTH(0, 1),
    SOUTHWEST(-1,1),
    WEST(-1, 0),
    NORTHWEST(-1,-1);
    fun nextPoint(point: Point, stepSize: Int = 1) : Point {

        return Point(point.x+(stepSize*this.xchange), point.y+ (stepSize*this.yChange))
    }
    fun turnRight() : Direction {
        return when(this) {
            NORTH -> NORTHEAST
            NORTHEAST -> EAST
            EAST -> SOUTHEAST
            SOUTHEAST -> SOUTH
            SOUTH -> SOUTHWEST
            SOUTHWEST -> WEST
            WEST -> NORTHWEST
            NORTHWEST -> NORTH
        }
    }
}