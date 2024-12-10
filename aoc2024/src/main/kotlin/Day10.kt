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
        potentialDirections(grid, nextNumber, it)
    }.flatten().distinct())
}

fun rating(grid: Grid, nextNumber: Int, startingPositions: List<List<Point>>): List<List<Point>> {
    if (nextNumber == 10 ) {
        return startingPositions.map{ points -> points.sortedWith( compareBy( {it.x}, {it.y}) ) }.distinct().toList()
    }
    return rating(grid, nextNumber + 1, startingPositions.map {
        startingPosition ->
        val previousPoint = startingPosition.first { candidate -> grid.get(candidate) == ""+ (nextNumber -1)  }
        potentialDirections(grid, nextNumber, previousPoint)
        .map { startingPosition.plusElement(it) }
    }.flatten().distinct())
}

fun potentialDirections(grid: Grid, searchNumber: Int, currentPoint :Point) : List<Point> {
    val numberAsString = "" + searchNumber
    return listOf(
        Direction.NORTH.nextPoint(currentPoint),
        Direction.SOUTH.nextPoint(currentPoint),
        Direction.EAST.nextPoint(currentPoint),
        Direction.WEST.nextPoint(currentPoint),
    ).filter {
            potential ->
        grid.get(potential) == numberAsString
    }
}