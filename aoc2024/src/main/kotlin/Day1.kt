package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println
import kotlin.math.abs

fun main() {
    val pairs = BufferedReader(FileReader(File("data/day1.test"))).lines()
        .map { val parts = it.split("   "); Pair(parts[0].toInt(), parts[1].toInt()) }.toList()

    val left = pairs.map { it.first }.sorted()
    val right = pairs.map { it.second }.sorted()

    println(left.zip(right).sumOf { abs(it.second - it.first) })

    val rightOccurrences = right.groupBy { it }.map { (k,v) -> Pair(k, v.count()) }.toMap()
    println(left.sumOf { it * rightOccurrences.getOrDefault(it, 0) })
}