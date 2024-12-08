package pavitt.ross.aoc

data class Point(val x: Int, val y: Int) {

    operator fun plus(delta: Point) : Point {
        return Point(this.x+delta.x, this.y+ delta.y)
    }

    operator fun times(multiple: Int) : Point {
        return Point(this.x* multiple, this.y*multiple)
    }

    operator fun minus(delta: Point): Point {
        return Point(this.x-delta.x, this.y- delta.y)
    }
}
