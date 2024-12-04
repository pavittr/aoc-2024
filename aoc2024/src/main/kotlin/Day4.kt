package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val lines = BufferedReader(FileReader(File("data/day4.real"))).lines().toList()
    val board = lines
        .mapIndexed { y, s ->
            s.mapIndexed { x, letter -> Location(x,y, letter.toString())}.toList() }
        .flatten()

    println(board.filter { it.letter == "X" }.sumOf { wordCount(it, board) })
    println(board.filter { it.letter == "A" }.sumOf {xmasCount(it, board)})
}

data class Location(val x: Int, val y: Int, val letter: String) {}

fun xmasCount(rootLocation: Location,board: List<Location> ) : Int {
    val topM = listOf(
        Location(rootLocation.x-1, rootLocation.y - 1, "M"),
        Location(rootLocation.x+1, rootLocation.y - 1, "M"),
        Location(rootLocation.x-1, rootLocation.y + 1, "S"),
        Location(rootLocation.x+1, rootLocation.y + 1, "S"),
    )

    val leftM = listOf(
        Location(rootLocation.x-1, rootLocation.y - 1, "M"),
        Location(rootLocation.x+1, rootLocation.y - 1, "S"),
        Location(rootLocation.x-1, rootLocation.y + 1, "M"),
        Location(rootLocation.x+1, rootLocation.y + 1, "S"),
    )

    val rightM = listOf(
        Location(rootLocation.x-1, rootLocation.y - 1, "S"),
        Location(rootLocation.x+1, rootLocation.y - 1, "M"),
        Location(rootLocation.x-1, rootLocation.y + 1, "S"),
        Location(rootLocation.x+1, rootLocation.y + 1, "M"),
    )

    val bottomM = listOf(
        Location(rootLocation.x-1, rootLocation.y - 1, "S"),
        Location(rootLocation.x+1, rootLocation.y - 1, "S"),
        Location(rootLocation.x-1, rootLocation.y + 1, "M"),
        Location(rootLocation.x+1, rootLocation.y + 1, "M"),
    )

    val vals = listOf(
        topM,
        leftM,
        rightM,
        bottomM,
    )
    return boardMatches(board, vals)
}

fun wordCount(rootLocation: Location,board: List<Location> ) : Int {
    // find top route
    val NVals = listOf(
        Location(rootLocation.x, rootLocation.y - 1, "M"),
        Location(rootLocation.x, rootLocation.y - 2, "A"),
        Location(rootLocation.x, rootLocation.y - 3, "S"),
    )
    val NEVals = listOf(
        Location(rootLocation.x+1, rootLocation.y - 1, "M"),
        Location(rootLocation.x+2, rootLocation.y - 2, "A"),
        Location(rootLocation.x+3, rootLocation.y - 3, "S"),
    )
    val EVals = listOf(
        Location(rootLocation.x+1, rootLocation.y, "M"),
        Location(rootLocation.x+2, rootLocation.y, "A"),
        Location(rootLocation.x+3, rootLocation.y, "S"),
    )
    val SEVals = listOf(
        Location(rootLocation.x+1, rootLocation.y+1, "M"),
        Location(rootLocation.x+2, rootLocation.y+2, "A"),
        Location(rootLocation.x+3, rootLocation.y+3, "S"),
    )
    val SVals = listOf(
        Location(rootLocation.x, rootLocation.y+1, "M"),
        Location(rootLocation.x, rootLocation.y+2, "A"),
        Location(rootLocation.x, rootLocation.y+3, "S"),
    )
    val SWVals = listOf(
        Location(rootLocation.x-1, rootLocation.y+1, "M"),
        Location(rootLocation.x-2, rootLocation.y+2, "A"),
        Location(rootLocation.x-3, rootLocation.y+3, "S"),
    )
    val WVals = listOf(
        Location(rootLocation.x-1, rootLocation.y, "M"),
        Location(rootLocation.x-2, rootLocation.y, "A"),
        Location(rootLocation.x-3, rootLocation.y, "S"),
    )
    val NWVals = listOf(
        Location(rootLocation.x-1, rootLocation.y-1, "M"),
        Location(rootLocation.x-2, rootLocation.y-2, "A"),
        Location(rootLocation.x-3, rootLocation.y-3, "S"),
    )
    val vals = listOf(NVals, NEVals, EVals, SEVals, SVals, SWVals, WVals, NWVals)
    return boardMatches(board, vals)
}

fun boardMatches(board: List<Location>, possibleMatches: List<List<Location>>) :Int {
    return possibleMatches.map { if (board.containsAll(it)) {1} else {0} }.sum()
}