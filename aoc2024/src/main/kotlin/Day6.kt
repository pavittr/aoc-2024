package code

import pavitt.ross.aoc.Grid
import pavitt.ross.aoc.Direction
import pavitt.ross.aoc.Point
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day6.real"))).lines()
    .toList().mapIndexed { y, line ->
        line.mapIndexed { x, location ->
            Pair(Point(x,y), location.toString())
        }.toList()
    }.flatten()
        .toMap()

    val grid = Grid(gridSpots)
    println(runP1(grid))
    println(runP2(grid))
}

fun runP1(grid : Grid) : Int {
    val guard = Guard(grid.find("^"),Direction.NORTH)

    val visited = mutableSetOf(guard.point)
    while(grid.get(guard.next()) != "E") {
        guard.move(grid.get(guard.next()))
        visited.add(guard.point)
    }
return visited.size
}

fun runP2(grid: Grid): Int {
    val potentialObstacles = mutableSetOf<Point>()
    val guard = Guard( grid.find("^"),Direction.NORTH)
    while(grid.get(guard.next()) != "E") {
        if (grid.get(guard.next()) == "." && placeBarrierAtNextAndWalk(grid,guard.next())) {
                potentialObstacles.add(guard.next())
        }
        guard.move(grid.get(guard.next()))
    }
    return potentialObstacles.size
}

fun placeBarrierAtNextAndWalk(currentGrid: Grid, nextPointOfCurrentGrid: Point ): Boolean {
    val grid = currentGrid.adapt(mapOf(nextPointOfCurrentGrid to "#"))
    val guard = Guard(grid.find("^"), Direction.NORTH)
    val visitedSpots = mutableSetOf<Pair<Direction, Point>>()

    while(grid.get(guard.next()) != "E") {
        if (visitedSpots.contains(Pair(guard.direction, guard.next())) ) {
           return true
        }
        guard.move(grid.get(guard.next()))
        visitedSpots.add(Pair(guard.direction, guard.point))
    }
    return false
}

class Guard(var point: Point, var direction: Direction) {
    fun next(): Point {
        return direction.nextPoint(point)
    }
    fun move(nextVal: String) {
        if ( nextVal == "#") {
            direction = direction.turnRight()
        } else {
            point = direction.nextPoint(point)
        }
    }
}