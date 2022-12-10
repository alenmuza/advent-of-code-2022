package solutions

import java.lang.IllegalStateException
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.math.sqrt

class Day9 {
    companion object {
        // Everything after this is in red
        const val redColor = "\u001b[31m"

        // Resets previous color codes
        const val resetColor = "\u001b[0m"

        fun solve() {
            val inputLines = Main.loadFile("./day9/input.txt")

            val commands = inputLines.map { it.first() to it.takeLastWhile { it != ' ' }.toInt() }
            println("Day9 1st star: " + calculateNumberOfTailVisitedPoints(2, commands))
            println("Day9 2st star: " + calculateNumberOfTailVisitedPoints(10, commands))

        }

        private fun calculateNumberOfTailVisitedPoints(
            numberOfKnots: Int,
            commands: List<Pair<Char, Int>>
        ): Int {
            val allKnots: List<Knot> = MutableList(numberOfKnots) { Knot(Point(0, 0)) }

            commands.forEach { command ->
                (1..command.second).forEach {
                    pullKnot(allKnots, 0, command.first)
                }
            }

            return allKnots.last().visitedPoints.size
        }

        private fun pullKnot(knots: List<Knot>, indexOfItemToPull: Int, direction: Char) {
            // Break at the end
            if (indexOfItemToPull == knots.size) return

            val knot = knots[indexOfItemToPull]

            //HEAD
            if (indexOfItemToPull == 0) {
                knot.move(direction)
                pullKnot(knots, indexOfItemToPull + 1, direction)
            } else {
                val previousKnot = knots[indexOfItemToPull - 1]
                val currentKnotDirection = knot.follow(previousKnot)
                pullKnot(knots, indexOfItemToPull + 1, currentKnotDirection)
            }
        }
    }
}

data class Point(
    val x: Int,
    val y: Int,
)

fun Point.move(direction: Char) = when (direction) {
    'R' -> Point(x + 1, y)
    'L' -> Point(x - 1, y)
    'U' -> Point(x, y + 1)
    'D' -> Point(x, y - 1)
    else -> throw IllegalStateException("Wrong direction: $direction")
}


data class Knot(
    var currentPoint: Point,
    val visitedPoints: MutableSet<Point> = mutableSetOf(Point(0, 0))
) {
    fun follow(previousKnot: Knot): Char {
        val xDiff = currentPoint.x - previousKnot.currentPoint.x
        val yDiff = currentPoint.y - previousKnot.currentPoint.y
        val diff = sqrt(pow(xDiff.toDouble(), 2.0) + pow(yDiff.toDouble(), 2.0))

        if (diff <= sqrt(2.0)) return ' '

        val newPoint = Point(
            if (xDiff == 0) currentPoint.x else if (abs( xDiff) == 1) previousKnot.currentPoint.x else midCoordinate(currentPoint.x, previousKnot.currentPoint.x),
            if (yDiff == 0) currentPoint.y else if (abs( yDiff) == 1) previousKnot.currentPoint.y else midCoordinate(currentPoint.y, previousKnot.currentPoint.y)
        )

        this.currentPoint = newPoint
        this.visitedPoints.add(newPoint)
        return ' '
    }
}

private fun midCoordinate(first: Int, second: Int) = if (abs(first - second) > 1) (first + second) / 2 else first

fun Knot.move(direction: Char): Unit {
    val newPoint = this.currentPoint.move(direction)
    visitedPoints.add(newPoint)
    currentPoint = newPoint
}
