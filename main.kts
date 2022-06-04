package cinema

import java.util.*

class ticketAlreadySold(message: String): Exception(message)

fun main() {
    val (rows, seats, tickets) = getCinema()
    val prices = listOf(10, 8)
    var currentIncome: Int = 0
    val totalIncome: Int = when {
        rows * seats <= 60 -> rows * seats * prices[0]
        else ->  rows / 2 * seats * prices[0] + (rows * seats - rows / 2 * seats ) * prices[1]
    }
    var entry: Int = -1
    while (entry != 0) {
        println()
        println(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent()
        )
        entry = readLine()!!.toInt()
        if (entry == 1) {
            displayCinema(rows, seats, tickets)
        } else if (entry == 2) {
            val ticketPrice: Int = buyTicket(rows, seats, tickets, prices)
            currentIncome += ticketPrice
            if (ticketPrice > 0) {
                println("\nTicket price: $$ticketPrice")
            }
        } else if (entry == 3) {
            getStats(rows, seats, tickets, currentIncome, totalIncome)
        }

    }
}

fun getCinema(): Triple<Int, Int, MutableList<MutableList<Char>>> {
    println("Enter the number of rows:")
    val rows: Int = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats: Int = readLine()!!.toInt()
    val tickets = MutableList(rows) {
        "S".repeat(seats).toMutableList()
    }
    return Triple(rows, seats, tickets)
}

fun displayCinema(rows: Int, seats: Int, tickets: MutableList<MutableList<Char>>) {
    println("\nCinema:\n" + " ".repeat(2) + (1..seats).joinToString(separator = " "))
    for (i in 1 until rows + 1) {
        println(i.toString() + " " + tickets[i - 1].joinToString(separator = " "))
    }
}

fun getTicketPrice(rows: Int, seats: Int, buyRow: Int, prices: List<Int>): Int {
    return when {
        rows * seats <= 60 -> prices[0]
        else -> {
            if (buyRow in 1..rows / 2) prices[0] else prices[1]
        }
    }
}

fun buyTicket(rows: Int, seats: Int, tickets: MutableList<MutableList<Char>>, prices: List<Int>): Int {
    var validEntry: Boolean = false
    var buyRow: Int = 0
    var buySeat: Int = 0
    while (validEntry == false) {
        println("\nEnter a row number:")
        buyRow = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        buySeat = readLine()!!.toInt()
        try {
            val ticketStatus: Char = tickets[buyRow - 1][buySeat - 1]
            if (tickets[buyRow - 1][buySeat - 1] == 'B') {
                throw ticketAlreadySold("\nThat ticket has already been purchased!")
            }
            validEntry = true
        } catch (e: IndexOutOfBoundsException) {
            println("\nWrong input!")
        } catch (e: ticketAlreadySold) {
            println(e.message)
        }
    }
    tickets[buyRow - 1][buySeat - 1] = 'B'
    return getTicketPrice(rows, seats, buyRow, prices)
}

fun getStats(rows: Int,
             seats: Int,
             tickets: MutableList<MutableList<Char>>,
             currentIncome: Int,
             totalIncome: Int): Unit {
    val ticketsBought: Int = tickets.flatten().count { it == 'B' }
    val ticketsBoughtPercentage: String = String.format(
        locale = Locale.ENGLISH,
        format = "%.2f",
        (ticketsBought / (rows * seats.toDouble()) * 100)) + '%'
    println("\nNumber of purchased tickets: $ticketsBought")
    println("Percentage: $ticketsBoughtPercentage")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")

}
