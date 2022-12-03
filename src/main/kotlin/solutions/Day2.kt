package solutions

class Day2 {
    companion object {

        // A  A  A  B  B  B  C  C  C
        // X  Y  Z  X  Y  Z  X  Y  Z
        // 0 -1 -2  1  0 -1  2  1  0
        // 0  W  L  L  0  W  W  L  0

        //  1  2 0 1 2
        //  1  2 3 4 5
        // -2 -1 0 1 2
        //  L  W X L W
        // A for Rock, B for Paper, and C for Scissors
        val opponentOptions = listOf("A", "B", "C")

        // X for Rock, Y for Paper, and Z for Scissors
        val myOptions = listOf("X", "Y", "Z")

        fun solve() {
            val inputLines = Main.loadFile("./day2/input.txt")


            print("Day2: myScore= ${inputLines.map { scoreSingleRound(it) }.sum()}")
        }

        fun scoreSingleRound(round: String): Int {
            val split = round.split(" ")
            val opponentLetter = split.get(0)
            val opponentLetterIndex = opponentOptions.indexOf(opponentLetter)
            val myScoreLetter = split.get(1)

            val myLetter = if (myScoreLetter == "X") {
                myOptions.get((opponentLetterIndex - 1 + 3) % 3)
            } else if (myScoreLetter == "Y") {
                myOptions.get(opponentLetterIndex)
            } else {
                myOptions.get((opponentLetterIndex + 1) % 3)
            }


            val myLetterIndex = myOptions.indexOf(myLetter)
            val indexDiff = (opponentLetterIndex - myLetterIndex + 3) % 3

            val myLetterPoints = myLetterIndex + 1
            return if (indexDiff == 0) {
                3 + myLetterPoints
            } else if (indexDiff == 1) {
                myLetterPoints
            } else {
                6 + myLetterPoints
            }
        }
    }


}