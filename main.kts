package cinema

fun main() {
    val (rows, seats, tickets) = getCinema()

    var entry: Int = -1
    while (entry != 0) {
        println()
        println(
            """
            1. Show the seats
            2. Buy a ticket
            0. Exit
        """.trimIndent()
        )
        entry = readLine()!!.toInt()
        if (entry == 1) {
            displayCinema(rows, seats, tickets)
        } else if (entry == 2) {
            val ticketPrice: Int = buyTicket(rows, seats, tickets)
            println("Ticket price: $$ticketPrice")
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

fun getTicketPrice(rows: Int, seats: Int, buyRow: Int): Int {
    val prices = listOf(10, 8)
    return when {
        rows * seats <= 60 -> prices[0]
        else -> {
            if (buyRow in 1..seats / 2) prices[0] else prices[1]
        }
    }
}

fun buyTicket(rows: Int, seats: Int, tickets: MutableList<MutableList<Char>>): Int {
    println("\nEnter a row number:")
    val buyRow: Int = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val buySeat: Int = readLine()!!.toInt()
    tickets[buyRow - 1][buySeat - 1] = 'B'
    return getTicketPrice(rows, seats, buyRow)
}
