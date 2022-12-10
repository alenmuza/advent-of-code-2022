package solutions

class Day10 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day10/input.txt")

            val commands = inputLines.map {
                it.take(4) to (if (it.take(4) == "addx") it.takeLastWhile { it != ' ' } else null)
            }

            val numberOfCycles = commands.map { if (it.first == "addx") 2 else 1 }.sum()

            val samples = (1..numberOfCycles)
                .filter { it == 20 || (it - 20) % 40 == 0 }

            var currentCycle = 1
            var currentRegisterValue = 1
            val valuesAtCycle = mutableListOf<Pair<Int, Int>>()
            for (command in commands) {
                if (command.first == "addx") {
                    currentCycle += 2
                    currentRegisterValue += command.second!!.toInt()
                    valuesAtCycle.add(currentCycle to currentRegisterValue)
                } else currentCycle++
            }

            println("Day10 1st star:" + samples.map { s ->
                s * valuesAtCycle.reversed().first { it.first <= s }.second
            }.sum())

            println("Day10 2nd star:")
            (1..numberOfCycles)
                .forEach { cycle ->
                    val currentSprout = valuesAtCycle.reversed().firstOrNull() { it.first <= cycle }?.second ?: 1
                    if (cycle % 40 in currentSprout..currentSprout + 2) print("#") else print(" ")
                    if (cycle % 40 == 0) print("\n")
                }
        }
    }
}