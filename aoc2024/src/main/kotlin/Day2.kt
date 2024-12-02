package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val reports = BufferedReader(FileReader(File("data/day2.real")))
        .lines()
        .map { it.split(" ").map { a -> a.toInt() }.toList()}
        .map{Report(it)}
        .toList()

    println(reports.count { it.safe() })
    println(reports.count { it.combinatoriallySafe()})
}

class Report(val data: List<Int>) {

    fun safe(): Boolean {
        val secondOrder = data.windowed(2, 1)
            .map { (a, b) -> b - a }.sorted()
        return secondOrder.first() * secondOrder.last() > 0
                && secondOrder.first() > -4 && secondOrder.last() < 4
    }

    fun combinatoriallySafe() : Boolean {
        for (i in -1..data.count()-1) {
            if (Report (data.filterIndexed { index, _ -> index != i }.toList()).safe())
                return true
        }
        return false
    }
}