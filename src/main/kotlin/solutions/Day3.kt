package solutions

class Day3 {


    companion object {
        val letters = ('a'..'z').union('A'..'Z')


        fun solve() {
            val inputLines = Main.loadFile("./day3/input.txt")

            val result = inputLines.map { Pair(it.take(it.length / 2), it.takeLast(it.length / 2)) }
                .map { p -> p.first.first { p.second.contains(it) } }
                .map { getPriorityScore(it) }
                .sum()

            println("Day3 1st star = $result")

            val result2 = inputLines.chunked(3)
                .map {threeRucksacs ->
                    threeRucksacs.get(0)
                        .first { threeRucksacs.get(1).contains(it) && threeRucksacs.get(2).contains(it) }

                }
                .map { getPriorityScore(it) }
                .sum()

            println("Day3 2nd star = $result2")
        }

        private fun getPriorityScore(it: Char) = letters.indexOf(it) + 1
    }
}