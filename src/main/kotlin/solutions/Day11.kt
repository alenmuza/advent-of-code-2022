package solutions

import java.math.BigInteger

class Day11 {
    companion object {
        fun solve() {
            val inputLines = Main.loadFile("./day11/input.txt")
            solveFirstStar(inputLines)
            solveSecondStar(inputLines)
        }

        private fun solveFirstStar(inputLines: List<String>) {
            val monkeys = getMonkiesFromInput(inputLines)

            for (i in (1..20)) {
                for (monkey in monkeys) {
                    if (monkey.items.size > 0) {
                        for (item in monkey.items) {
                            monkey.inspectionCount++
                            item.performMonkeyInspection(monkey.operation, 0, true)
                            if (item.test(monkey.divisibleBy)) {
                                monkeys[monkey.nextMonkeyIfTrue].items.add(item)
                            } else {
                                monkeys[monkey.nextMonkeyIfFalse].items.add(item)
                            }
                        }
                        monkey.items = mutableListOf()
                    }
                }
            }

            println("Day11 1st star:${monkeys.sortedByDescending { it.inspectionCount }.take(2).map { it.inspectionCount }.reduce { acc, i ->  acc * i }}" )
        }

        private fun solveSecondStar(inputLines: List<String>) {
            val monkeys = getMonkiesFromInput(inputLines)
            val commonDivisor = monkeys.map { it.divisibleBy }.reduce { acc, i -> acc * i }

            for (i in (1..10000)) {
                for (monkey in monkeys) {
                    if (monkey.items.size > 0) {
                        for (item in monkey.items) {
                            monkey.inspectionCount++
                            item.performMonkeyInspection(monkey.operation, commonDivisor, false)
                            if (item.test(monkey.divisibleBy)) {
                                monkeys[monkey.nextMonkeyIfTrue].items.add(item)
                            } else {
                                monkeys[monkey.nextMonkeyIfFalse].items.add(item)
                            }
                        }
                        monkey.items = mutableListOf()
                    }
                }
            }

            println(
                "Day11 2nd star:${
                    monkeys.sortedByDescending { it.inspectionCount }.take(2).map { it.inspectionCount.toBigInteger() }
                        .reduce { acc, i -> acc * i }
                }"
            )
        }

        private fun getMonkiesFromInput(inputLines: List<String>): MutableList<Monkey> {
            val monkeyLineRegex = "Monkey (.+):".toRegex()
            val startingItemsLineRegex = "  Starting items: (.*)".toRegex()
            val operationLineRegex = "  Operation: new = old (.) (.+)".toRegex()
            val testLineRegex = "  Test: divisible by (.+)".toRegex()
            val trueLineRegex = "    If true: throw to monkey (.+)".toRegex()
            val falseLineRegex = "    If false: throw to monkey (.+)".toRegex()
            val monkeys = mutableListOf<Monkey>()

            var inputItems = mutableListOf<Item>()
            var inputOperation: Operation? = null
            var divisibleByTest = 0
            var nextMonkeyIfTrue = 0
            var nextMonkeyIfFalse = 0
            for (inputLine in inputLines) {
                if (inputLine.matches(startingItemsLineRegex))
                    inputItems = startingItemsLineRegex.matchEntire(inputLine)!!.destructured.component1().split(", ")
                        .map { Item(it.toBigInteger()) }.toMutableList()
                if (inputLine.matches(operationLineRegex)) {
                    val operand = operationLineRegex.matchEntire(inputLine)!!.destructured.component2()
                    inputOperation = Operation(
                        operation = operationLineRegex.matchEntire(inputLine)!!.destructured.component1(),
                        operand = operand.toIntOrNull(),
                        selfOperand = operand.toIntOrNull() == null
                    )
                }
                if (inputLine.matches(testLineRegex))
                    divisibleByTest = testLineRegex.matchEntire(inputLine)!!.destructured.component1().toInt()
                if (inputLine.matches(trueLineRegex))
                    nextMonkeyIfTrue = trueLineRegex.matchEntire(inputLine)!!.destructured.component1().toInt()
                if (inputLine.matches(falseLineRegex)) {
                    nextMonkeyIfFalse = falseLineRegex.matchEntire(inputLine)!!.destructured.component1().toInt()
                    monkeys.add(
                        Monkey(
                            items = inputItems,
                            operation = inputOperation!!,
                            divisibleBy = divisibleByTest,
                            nextMonkeyIfTrue = nextMonkeyIfTrue,
                            nextMonkeyIfFalse = nextMonkeyIfFalse,
                            inspectionCount = 0
                        )
                    )
                }
            }
            return monkeys
        }
    }
}

data class Monkey(
    var items: MutableList<Item>,
    val operation: Operation,
    val divisibleBy: Int,
    val nextMonkeyIfTrue: Int,
    val nextMonkeyIfFalse: Int,
    var inspectionCount: Int
)

data class Operation(
    val operation: String,
    val operand: Int?,
    val selfOperand: Boolean
)

data class Item(
    var worryLevel: BigInteger,
) {
    fun performMonkeyInspection(operation: Operation, commonDivisor: Int, firstStarCalculation: Boolean) {
        if (operation.selfOperand) {
            when (operation.operation) {
                "*" -> worryLevel *= worryLevel
                "+" -> worryLevel += worryLevel
            }
        } else {
            when (operation.operation) {
                "*" -> worryLevel *= operation.operand!!.toBigInteger()
                "+" -> worryLevel += operation.operand!!.toBigInteger()
            }
        }
        if (firstStarCalculation) {
            worryLevel = Math.floorDiv(worryLevel.toInt(), 3).toBigInteger()
        } else {
            worryLevel = worryLevel % commonDivisor.toBigInteger()
        }
    }

    fun test(divisibleBy: Int): Boolean = (worryLevel % divisibleBy.toBigInteger()).toInt() == 0
}