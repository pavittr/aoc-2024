package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
// Block IDs will rapidly exhuast single digits,
    // The total data length is the sum of each individual number, so calculate that first, that gives us the dat asize
    val dataAsLongs = BufferedReader(FileReader(File("data/day9.real"))).readText().map { it.toString().toLong() }
 println(dataAsLongs.sum())

    // Now build a mutable list of Longs. Each Long contains the ID of a file or -1
    val dataBuffer = dataAsLongs.windowed(2, 2, true)
        .mapIndexed { fileID, lengths ->
            // Build a list of length lengths[0] and populate it with fileID
            val fileData = List(lengths[0].toInt(), {fileID.toLong()})
            if (lengths.size > 1 ) {
                listOf(fileData, List(lengths[1].toInt(), {-1L})).flatten()
            } else {
                fileData
            }
        }.flatten().toMutableList()

    // look for the first -1 ad grab its index. Then find the last non--1, and grab its index. If the non--1 is after the -1, then swap
    var minusOneIndex = dataBuffer.indexOf(-1L)
    var nonMinusOneIndex = dataBuffer.indexOfLast { it != -1L }
    while (minusOneIndex < nonMinusOneIndex) {
        dataBuffer[minusOneIndex] = dataBuffer[nonMinusOneIndex]
        dataBuffer[nonMinusOneIndex] = -1
        minusOneIndex = dataBuffer.indexOf(-1)
        nonMinusOneIndex = dataBuffer.indexOfLast { it != -1L }
    }

    println(dataBuffer.filter { it > -1 }.mapIndexed{slot, id -> slot * id }.sum())

    // Store the data as contiguous blocks of lists of int. If a block contains -1s, then its eleigible.
    // Find the right most fle, and then search to its right for the lowest available slot.
    // If none exists, leave it where it is and go tot he next file.
    // If one does exist, if its the same size swap it
    // If the file is smaller, pad it to the same size, then swap it (actually pad first then swap to be efficient
    val p2Buffer = dataAsLongs.windowed(2, 2, true)
        .mapIndexed { fileID, lengths ->
            // Build a list of length lengths[0] and populate it with fileID
            val fileData = List(lengths[0].toInt(), {fileID.toLong()})
            if (lengths.size > 1 && lengths[1] > 0) {
                listOf(fileData, List(lengths[1].toInt(), {-1L}))
            } else {
                listOf(fileData)
            }
        }.flatten().toMutableList()


    var fileID = p2Buffer.last().first()

    while (fileID > 0) {
        val moveIndex = p2Buffer.indexOfLast { it.contains(fileID) }
        val moveFile = p2Buffer[moveIndex]
        val firstSlotData = p2Buffer
            .mapIndexed { index, longs -> Pair(index, longs) }
            .filter {
                it.second.all{ it == -1L}
                        && it.first < moveIndex
            }
            .firstOrNull { it.second.size >= moveFile.size }

        if (firstSlotData != null) {
            if (moveFile.size < firstSlotData.second.size) {
                // Need to break the slot into its two compoenents
                p2Buffer.add(firstSlotData.first + 1, List(firstSlotData.second.size - moveFile.size, { -1L }))
                p2Buffer[moveIndex+1] = List(moveFile.size, { -1L })
            } else {
                p2Buffer[moveIndex] = List(moveFile.size, { -1L })
            }
            p2Buffer[firstSlotData.first] = moveFile

            // See if there are any contiguous  -1 blocks and, if there are, combine them
            var indexesWithMinus1ToTheLeft = p2Buffer.mapIndexed { index, longs -> Pair(index, longs) }
                .filter { it.second.contains(-1L) }
                .firstOrNull { p2Buffer[it.first -1].contains(-1L) }
            while (indexesWithMinus1ToTheLeft != null) {

                p2Buffer[indexesWithMinus1ToTheLeft.first-1] = List(p2Buffer[indexesWithMinus1ToTheLeft.first -1].size + indexesWithMinus1ToTheLeft.second.size, {-1L})
                p2Buffer.removeAt(indexesWithMinus1ToTheLeft.first)
                indexesWithMinus1ToTheLeft = p2Buffer.mapIndexed { index, longs -> Pair(index, longs) }
                    .filter { it.second.contains(-1L) }
                    .firstOrNull { p2Buffer[it.first -1].contains(-1L) }
            }
        }
        fileID--
    }
    println(p2Buffer.flatten().mapIndexed{slot, id -> slot * id }.filter { it > -1 }.sum())
}