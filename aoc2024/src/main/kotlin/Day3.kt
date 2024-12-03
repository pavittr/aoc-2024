package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val data = BufferedReader(FileReader(File("data/day3.test"))).readText()

    val r = Regex("""mul\((\d+),(\d+)\)""")
    println(r.findAll(data).map { multiply(it) }.sum())

    val splitByDonts = data.split("don't()")
    val first = splitByDonts.first()
    val cleanedString = first + splitByDonts
        .drop(1)
        .joinToString("") {
            it.split("do()").drop(1).joinToString("")
        }
    println(r.findAll(cleanedString).map { multiply(it) }.sum())
}

fun multiply(m: MatchResult) : Int {
    val first = m.groupValues[1].toInt()
    val second = m.groupValues[2].toInt()
    return first * second
}