import solutions.Day1
import solutions.Day2
import solutions.Day3
import solutions.Day4
import java.io.File

fun main(args: Array<String>) {
    Day1.solve()
    Day2.solve()
    Day3.solve()
    Day4.solve()
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