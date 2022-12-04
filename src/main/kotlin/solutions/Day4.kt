package solutions

import java.lang.Integer.max
import java.lang.Math.min

class Day4 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day4/input.txt")

            val pairOfRanges = inputLines
                .map { it.takeWhile { it != ',' } to it.takeLastWhile { it != ',' } }
                .map {
                    it.first.takeWhile { it != '-' }.toInt()..it.first.takeLastWhile { it != '-' }
                        .toInt() to it.second.takeWhile { it != '-' }.toInt()..it.second.takeLastWhile { it != '-' }
                        .toInt()
                }

            val result1 = pairOfRanges.count {
                    doesOneContainAnother(it)
                }

            println("Day4 result 1st star = $result1")

            val result2 = pairOfRanges.count {
                    doesOneOverlapAnother(it)
                }

            println("Day4 result 2st star = $result2")
        }

        private fun doesOneOverlapAnother(it: Pair<IntRange, IntRange>) =
            max(it.first.first, it.second.first) <= min(it.second.last, it.first.last)

        private fun doesOneContainAnother(it: Pair<IntRange, IntRange>) =
            (it.first.first <= it.second.start && it.first.last >= it.second.last) ||
                    (it.first.first >= it.second.first && it.first.last <= it.second.last)
    }
}