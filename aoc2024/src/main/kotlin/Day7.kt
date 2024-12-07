package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day7.test"))).lines()
        .map {
            Pair(
                it.split(": ").first().toLong(),
                it.split(": ").drop(1).first()
                    .split(" ").map { op -> op.toLong() }.toList()
            )
        }.toList()

    println(
        gridSpots
            .filter { combinations(it, listOf(MathOp.MULTIPLY, MathOp.ADD)) }
            .sumOf { it.first })
    println(
        gridSpots
            .filter { combinations(it, listOf(MathOp.MULTIPLY, MathOp.ADD, MathOp.CONCATENATE)) }
            .sumOf { it.first })
}

fun combinations(gridSpot: Pair<Long, List<Long>>,mathOps : List<MathOp>) : Boolean {
    return gridSpot.second.drop(1).fold(gridSpot.second.take(1)) { acc, num ->
        acc.map{ currentElem ->
            mathOps.map { op ->
                op.operate(currentElem, num)
            }.filter { it <= gridSpot.first }.toList()

        }.flatten()
    }.any { it == gridSpot.first }
}

enum class MathOp {
    ADD,
    MULTIPLY,
    CONCATENATE;

    fun operate(a: Long, b: Long): Long {
        return when(this){
            ADD -> a + b
            MULTIPLY -> a * b
            CONCATENATE -> ("" + a + b).toLong()
        }
    }
}