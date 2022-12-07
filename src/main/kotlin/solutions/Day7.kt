package solutions

class Day7 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day7/input.txt")

            var currentPath = "root"
            val files = mutableListOf<File>()
            val uniqueFolders = mutableSetOf("root")

            for (inputLine in inputLines) {
                val cdRegex = "\\\$ cd (.+)".toRegex()
                val fileRegex = "(\\d+) (.+)".toRegex()
                when {
                    inputLine.matches(cdRegex) -> {
                        val (cdArgument) = cdRegex.matchEntire(inputLine)!!.destructured
                        when (cdArgument) {
                            "/" -> currentPath = "root"
                            ".." -> {
                                currentPath = currentPath.substringBeforeLast("/")
                                uniqueFolders.add(currentPath)
                            }
                            else -> {
                                currentPath = currentPath + "/" + cdArgument
                                uniqueFolders.add(currentPath)
                            }
                        }
                    }
                    inputLine.matches(fileRegex) -> {
                        val (fileSize, fileName) = fileRegex.matchEntire(inputLine)!!.destructured
                        files.add(File(fileName, currentPath, fileSize.toInt()))
                    }
                }
            }

            val foldersWithSize = uniqueFolders.map { f -> f to files.filter { it.dirPath.startsWith(f) }.map { it.size }.sum() }
            println("Day7 1st star = ${foldersWithSize.filter { it.second < 100000 }.map { it.second }.sum()}")

            val missingSpace = 30000000 - (70000000 - foldersWithSize.first() { it.first == "root" }.second)
            println("Day7 2nd star = ${foldersWithSize.sortedBy { it.second }.first { it.second > missingSpace }.second}")
        }
    }
}

data class File(
    val name: String,
    val dirPath: String,
    val size: Int
)