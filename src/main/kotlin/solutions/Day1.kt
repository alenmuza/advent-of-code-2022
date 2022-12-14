package solutions

class Day1 {
    companion object{
        fun solve() {
            val inputLines = Main.loadFile("./day1/input.txt")

            var elf = 1
            var elfCalories = 0
            val elfToCalories = mutableMapOf <Int, Int>()

            for (inputLine in inputLines) {
                if (inputLine.isNotBlank()) {
                    elfCalories += inputLine.toInt()
                } else {
                    elfToCalories[elf] = elfCalories
                    elf++
                    elfCalories = 0
                }
            }

            println("Day1 1st star = ${elfToCalories.values.max()}")
            println("Day1 2nd star = ${elfToCalories.values.sortedDescending().take(3).sum()}")
        }
    }

}