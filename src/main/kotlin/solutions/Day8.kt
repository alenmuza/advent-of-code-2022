package solutions

class Day8 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day8/input.txt")
            val matrix = inputLines.map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()

            // outer trees
            val rowlenght = matrix[0].size
            val numberOfRows = matrix.size
            var treeCount = 2 * (numberOfRows + rowlenght) - 4
            var maxScenicScore = 0

            for (i in 1 until numberOfRows - 1) {
                for (j in 1 until rowlenght - 1) {
                    val element = matrix[i][j]
                    val rowLeft = matrix[i].take(j)
                    val rowRight = matrix[i].takeLast(rowlenght - j - 1)
                    val columnUp = matrix.map { it[j] }.take(i)
                    val columnDown = matrix.map { it[j] }.takeLast(numberOfRows - i - 1)

                    val scenicScore = countTrees(rowLeft.reversed(), element) *
                            countTrees(rowRight, element) *
                            countTrees(columnUp.reversed(), element) *
                            countTrees(columnDown, element)

                    if (scenicScore > maxScenicScore) maxScenicScore = scenicScore

                    if (element > rowLeft.max()) {
                        treeCount++
                        continue
                    }
                    if (element > rowRight.max()) {
                        treeCount++
                        continue
                    }
                    if (element > columnUp.max()) {
                        treeCount++
                        continue
                    }
                    if (element > columnDown.max()) {
                        treeCount++
                        continue
                    }
                }
            }

            println("Day8 1st star = $treeCount")
            println("Day8 2st star = $maxScenicScore")
        }

        private fun countTrees(list: List<Int>, element: Int): Int {
            val length = list.takeWhile { it < element }.count()
            if (length != list.size)
                return length + 1
            return length
        }
    }
}