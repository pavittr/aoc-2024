package code

import pavitt.ross.aoc.Grid
import pavitt.ross.aoc.Point
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day8.real"))).lines()
        .toList().mapIndexed { y, line ->
            line.mapIndexed { x, location ->
                Pair(Point(x,y), location.toString())
            }.toList()
        }.flatten()
        .toMap()

    val grid = Grid(gridSpots)

    val p1AntiNodes = process(grid, ::p1AntiNodeFinder)
    val p2AntiNodes = process(grid, ::p2AntiNodeFinder)

    println(p1AntiNodes.size)
    println(p2AntiNodes.size)
}

fun process (grid: Grid, antiNodeFinder: (Pair<Point, Point>, Grid) -> List<Point>) : Map<Point, String> {
   return grid.toList()
       .filter { it.second != "." }
       .toList()
       .map { currentSpot ->
           grid.toList().filter { it.second == currentSpot.second }
               .map { joinSpot -> Pair(currentSpot, joinSpot) }
       }.flatten().filter { it.first != it.second }
       .map {
           antiNodeFinder(Pair(it.first.first, it.second.first), grid)

       }.flatten()
       .filter { it.x >= grid.lowx() && it.x <= grid.highx() && it.y <= grid.highy() && it.y >= grid.lowy() }
       .toSet().associateWith { "#" }
}

fun p1AntiNodeFinder(inputPoints: Pair<Point,Point>, @Suppress("UNUSED_PARAMETER") grid: Grid) :List<Point> {
    val lowerx = inputPoints.toList().minBy { it.x }
    val upperx = inputPoints.toList().maxBy { it.x }

    val delta = Point (upperx.x - lowerx.x, upperx.y - lowerx.y)
    return listOf(
        lowerx - delta,
        upperx + delta
    )

}

fun p2AntiNodeFinder(inputPoints: Pair<Point,Point>, grid: Grid) :List<Point> {
    val lowerx = inputPoints.toList().minBy { it.x }
    val upperx = inputPoints.toList().maxBy { it.x }
    val delta = Point (upperx.x - lowerx.x, upperx.y - lowerx.y)
    val points = inputPoints.toList().toMutableList()
    var nextPoint = lowerx + delta
    while (grid.contains(nextPoint)) {
        points.add(nextPoint)
        nextPoint += delta
    }
    nextPoint = lowerx - delta
    while (grid.contains(nextPoint)) {
        points.add(nextPoint)
        nextPoint -= delta
    }
    return points
}
