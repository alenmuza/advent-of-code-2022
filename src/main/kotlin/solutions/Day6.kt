package solutions

class Day6 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day6/input.txt")

            val line = inputLines[0]
            println("Day6 1st star = ${findIndexOfDistinctCharSegmentEnd(line, 4)}")
            println("Day6 2nd star = ${findIndexOfDistinctCharSegmentEnd(line, 14)}")
        }

        private fun findIndexOfDistinctCharSegmentEnd(firstLine: String, segmentLength: Int): Int {
            for (i in 0..firstLine.length - segmentLength) {
                val segment = firstLine.substring(i, i + segmentLength)
                if (!segment.any { c -> segment.count { c == it } > 1 }) {
                    return i + segmentLength
                }
            }
            throw IllegalStateException("Segment with length $segmentLength not found!")
        }
    }
}