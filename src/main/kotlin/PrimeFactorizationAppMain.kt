/**
 * Run to have fun :))
 *
 * Task description:
 *      Priprav jednoduchou aplikaci v Kotlinu ktera bude resit rozklad libovolneho celeho cisla [1..100] na prvocisla.
 *      Reseni vsupu a vystupu je nechan na resiteli. Duraz by mel byt kladen na srozumitelnost a udrzitelnost kodu.
 *
 * @author  Petr Binčík
 * */
fun main() {
    // Don't be rude and say hi
    printWelcomeMessage()

    // Main infinite loop to retrieve numbers and calculate it / or print errors
    while (true) {
        val inputLine = readLineText()
        if (inputLine == "q") {
            println("Already quiting? Okay, noted...")
            break
        }

        val num = checkAndMapNumber(inputLine)
        if (num.isSuccess) num
            .map { calculatePrimeFactorization(it) }
            .getOrThrow()
            .joinToString(
                separator = " * ",
                prefix = "Prime factorization of \"${num.getOrNull()}\" is [ ",
                postfix = " ]"
            )
            .let { println(it) }
        else num
            .exceptionOrNull()
            ?.let { println("${it.message}\n") }
            ?: println("Some magick happened ...")
    }

    println("Thank you for using my dummy function. See you later :)")
}

/**
 * Simple welcome message. It is printed right into console.
 * */
private fun printWelcomeMessage() = StringBuilder()
    .appendLn("******************************************************************************")
    .appendLn("Welcome to my simple program to calculate prime factorization of given number.")
    .appendLn("Rules are simple:")
    .appendLn("   - write down a number")
    .appendLn("   - number must be grater then 0")
    .appendLn("   - number must not be grater then 100")
    .appendLn("   - if you wanna quit, press \"q\" as quit")
    .appendLn("******************************************************************************")
    .let { println(it) }

/**
 * Helping method to write text to builder with newline appended after it
 * */
private fun StringBuilder.appendLn(text: String) = this
    .append(text)
    .append("\n")

private fun readLineText(): String? {
    println("_____________________________")
    println("Enter number or press \"q\": ")
    return readLine()?.trim()
}

/**
 * Checks user input. Function returns any "error" during the check.
 * Function checks for Integers. Number has the following preconditions:
 *  - input must not be null
 *  - input must be Integer
 *  - input must not be 1
 *  - input must be grater then 0
 *  - input must not be grater then 100
 *
 * @param   inputString input retrieved from the user to be parsed as integer and check against preconditions
 *
 * @return  Integer in a range from 1 to 100, otherwise null
 * */
private fun checkAndMapNumber(inputString: String?): Result<Int> = runCatching {
    check(inputString != null) { "No input received!" }

    val number = inputString.toIntOrNull()
    check(number != null) { "No number received!" }
    check(number != 1) { "Number \"1\" is not a prime number!" }
    check(number > 0) { "Number has to be grater then 0!" }
    check(number <= 100) { "Number can not be greater then 100!" }

    number
}

/**
 * Calculate prime factorization of given number. Function uses two while loop, one is nested.
 *
 * @param   numberToCalculateFrom   Integer from range 2 to 100, inclusive
 *
 * @return  list of numbers as prime factorization of given number
 * */
private fun calculatePrimeFactorization(numberToCalculateFrom: Int): List<Int> {
    val primeFactors = mutableListOf<Int>()
    var remainder = numberToCalculateFrom
    var i = 2

    while (i <= remainder / i) {
        while (remainder % i == 0) {
            primeFactors.add(i)
            remainder /= i
        }

        i++
    }

    if (remainder > 1) primeFactors.add(remainder)

    return primeFactors
}
