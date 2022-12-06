package solutions

class Day6 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day6/input.txt")

            val firstLine = inputLines[0]
            println("Day6 1st star = ${findIndexForDistinctCharsEnd(firstLine, 4)}")
            println("Day6 2nd star = ${findIndexForDistinctCharsEnd(firstLine, 14)}")
        }

        private fun findIndexForDistinctCharsEnd(firstLine: String, distinctChars: Int): Int {
            for (i in 0..firstLine.length - distinctChars) {
                val segment = firstLine.substring(i, i + distinctChars)
                if (!segment.any { c -> segment.count { c == it } > 1 }) {
                    return i + distinctChars
                }
            }
            throw IllegalStateException()
        }
    }
}