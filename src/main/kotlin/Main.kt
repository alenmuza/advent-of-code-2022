import solutions.*
import java.io.File

fun main(args: Array<String>) {
    Day1.solve()
    Day2.solve()
    Day3.solve()
    Day4.solve()
    Day5.solve1()
    Day5.solve2()
    Day6.solve()
    Day7.solve()
    Day8.solve()

}

class Main {
    companion object {
        fun loadFile(pathToFile: String): List<String> {
            val classLoader: ClassLoader = Main::class.java.getClassLoader()
            val file = File(classLoader.getResource(pathToFile).getFile())
            return file.readLines()
        }
    }
}