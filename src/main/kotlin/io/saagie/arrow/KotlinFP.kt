package io.saagie.arrow

fun printAll() {
    println("5 x 1 = 5")
    println("5 x 2 = 10")
    println("5 x 3 = 15")
    println("5 x 4 = 20")
    println("5 x 5 = 25")
    println("5 x 6 = 30")
    println("5 x 7 = 35")
    println("5 x 8 = 40")
    println("5 x 9 = 45")
    println("5 x 10 = 50")
}

fun printForEach() =
    (1..10).forEach {
        println("5 x $it = ${5 * it}")
    }

fun printFunc() =
    (1..10)
        .map { "5 x $it = ${5 * it}" }
        .forEach(::println)

fun printFunc2() =
    (1..10)
        .map { Pair<Int, Int>(it, it * 5) }
        .map { (i, v) -> "5 x $i = $v" }
        .forEach(::println)

fun printFunc3() =
    (1..10)
        .map { calc(5, it) }
        .map { format(it) }
        .forEach(::println)

fun calc(number: Int, index: Int): Pair<Int, Int> =
    Pair<Int, Int>(index, number * index)

fun format(pair: Pair<Int, Int>): String =
    "5 x ${pair.first} = ${pair.second}"


fun calcWithError(number: Int, index: Int): Pair<Int, Int> =
    if (index == 6) {
        throw IllegalStateException("ERROR")
    } else {
        Pair<Int, Int>(index, number * index)
    }

fun main() {
    printAll()
    printForEach()
    printFunc()
    printFunc2()
    printFunc3()
}

