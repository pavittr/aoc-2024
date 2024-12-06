package code

import pavitt.ross.aoc.Direction
import pavitt.ross.aoc.Grid
import pavitt.ross.aoc.Point
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day4.test"))).lines()
        .toList().mapIndexed { y, line ->
            line.mapIndexed { x, location ->
                Pair(Point(x,y), location.toString())
            }.toList()
        }.flatten()
        .toMap()

    val grid = Grid(gridSpots)

    println(
        grid.all("X")
            .sumOf {
                    rootPoint ->
                Direction.entries
                    .map {
                        grid.search(rootPoint, it, 4).joinToString ("")
                    }
                    .count { it == "XMAS" }
            }
    )

    val validStrings = listOf("MAS", "SAM")
    println(grid.all("A")
        .map {
            listOf(
                grid.search(Direction.NORTHWEST.nextPoint(it), Direction.SOUTHEAST, 3).joinToString(""),
                grid.search(Direction.SOUTHWEST.nextPoint(it), Direction.NORTHEAST, 3).joinToString(""),
            )
        }.count { validStrings.containsAll(it) }
    )
}