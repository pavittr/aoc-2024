package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val allLines = BufferedReader(FileReader(File("data/day5.test"))).readText()

    val sections = allLines
        .replace("\r", "") // stupid windows and its stupid line endings
        .split("\n\n")
    val leftToRightRules = sections[0]
        .split("\n")
        .map { rule ->
            rule.split("|")
                .map { it.toInt() }
                .zipWithNext()
                .first()
        }
        .groupBy{it.first}
        .mapValues {
            it.value.map { p ->p.second }.toSet()
        }
    val rulesComp = RulesComparator(leftToRightRules)

    val updates = sections[1]
        .split("\n")
        .map { it.split(",").mapIndexed { index, x -> Pair(index, x.toInt()) }.toList() }.toList()

    val validAndInvalid = updates.groupBy { it ==  it.sortedWith(rulesComp) }

    println(validAndInvalid.map { mapping ->
        Pair(mapping.key, mapping.value.map { update ->
            update.sortedWith(rulesComp)
                .map { it.second }
        }.sumOf { it[it.size/2] })
    })
}


class RulesComparator(private val leftBeforeRight: Map<Int, Set<Int>>) : Comparator<Pair<Int, Int>> {
    override fun compare(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        if (leftBeforeRight[a.second].orEmpty().contains(b.second)) {
            return -1
        }
        return a.first - b.first
    }
}