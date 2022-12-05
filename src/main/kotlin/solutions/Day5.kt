package solutions

class Day5 {

    companion object {
        fun solve1() {
            val moveRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
            val (moves, stacksWithItems) = readStacksAndMoves()

            moves.forEach {
                val (quantity, from, to) = moveRegex.matchEntire(it)!!.destructured
                for (i in 1..quantity.toInt()) {
                    val item = stacksWithItems[from.toInt()]!!.last()
                    stacksWithItems[to.toInt()]!!.add(item)
                    stacksWithItems[from.toInt()]!!.remove(item)
                }
            }
            val result = stacksWithItems.values.map { it.last().second }.joinToString(separator = "")
            println("Day5 1st star = $result")

        }

        fun solve2() {
            val moveRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
            val (moves, stacksWithItems) = readStacksAndMoves()

            moves.forEach {
                val (quantity, from, to) = moveRegex.matchEntire(it)!!.destructured
                val items = stacksWithItems[from.toInt()]!!.takeLast(quantity.toInt())
                stacksWithItems[to.toInt()]!!.addAll(items)
                stacksWithItems[from.toInt()]!!.removeAll(items)
            }
            val result = stacksWithItems.values.map { it.last().second }.joinToString(separator = "")
            println("Day5 2nd star = $result")

        }

        private fun readStacksAndMoves(): Pair<List<String>, LinkedHashMap<Int, MutableList<Pair<Int, Char>>>> {
            val inputLines = Main.loadFile("./day5/input.txt")

            val header = inputLines.takeWhile { it != "" }
            val moves = inputLines.takeLastWhile { it != "" }

            val stacksWithItems = header
                .takeWhile { !it.startsWith(" 1") }
                .reversed()
                .flatMap {
                    it.mapIndexed { i, c -> (i + 3) to c }
                        .filter { it.second.isLetter() }
                        .map { (it.first + 3) / 4 to it.second }
                }.groupByTo(LinkedHashMap()) { it.first }
            return Pair(moves, stacksWithItems)
        }
    }
}