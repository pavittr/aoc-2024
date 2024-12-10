package code

import pavitt.ross.aoc.Direction
import pavitt.ross.aoc.Grid
import pavitt.ross.aoc.Point
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day10.real"))).lines()
        .toList().mapIndexed { y, line ->
            line.mapIndexed { x, location ->
                Pair(Point(x,y), location.toString())
            }.toList()
        }.flatten()
        .toMap()

    val grid = Grid(gridSpots)

    println(grid.toList().filter { it.second == "0" }.map { nextNumber(grid, 1, listOf(it.first)) }.flatten().size)
    println(grid.toList().filter { it.second == "0" }.map { rating(grid, 1, listOf(listOf(it.first))) }.flatten().size)
}

fun nextNumber(grid: Grid, nextNumber: Int, startingPositions: List<Point>): List<Point> {
    if (nextNumber == 10 ) {
        return startingPositions.filter { grid.get(it) == "9" }.toList()
    }
    return nextNumber(grid, nextNumber + 1, startingPositions.map {
        val potentiasl = listOf(
            Direction.NORTH.nextPoint(it),
            Direction.SOUTH.nextPoint(it),
            Direction.EAST.nextPoint(it),
            Direction.WEST.nextPoint(it),
        )
            potentiasl.filter {
            potential ->
            grid.get(potential) != "E"
        }
            .filter {
                potential ->
                grid.get(potential).toInt() == nextNumber
            }




    }.flatten().distinct())
}


// to calculate the distinct paths, need to keep all the paths until the end, not throw away the history
fun rating(grid: Grid, nextNumber: Int, startingPositions: List<List<Point>>): List<List<Point>> {
    if (nextNumber == 10 ) {
        return startingPositions.map{ it.sortedWith( compareBy( {it.x}, {it.y}) ) }.distinct().toList()
    }
    return rating(grid, nextNumber + 1, startingPositions.map {
        startingPosition ->
        // Find the four directions for this point
        // This is a list of points, only the last one should be nextNumber - 1
        // best find it
        val previousNumber = startingPosition.first { candidate -> grid.get(candidate) == ""+ (nextNumber -1)  }
        val potentiasl = listOf(
            Direction.NORTH.nextPoint(previousNumber),
            Direction.SOUTH.nextPoint(previousNumber),
            Direction.EAST.nextPoint(previousNumber),
            Direction.WEST.nextPoint(previousNumber),
        )
        potentiasl.filter {
                potential ->
            grid.get(potential) != "E"
        }
            .filter {
                    potential ->
                grid.get(potential).toInt() == nextNumber
            }.map { startingPosition.plusElement(it) }




    }.flatten().distinct())
}
