package parking

class Parking(size: Int) {
    private val parkingSpots = arrayOfNulls<Car>(size)
    private var firstAvailableSpot = 1

    fun parkCar(car: Car) {
        if (firstAvailableSpot != 0) {
            parkingSpots[firstAvailableSpot - 1] = car
            parkingSpots[firstAvailableSpot - 1]?.spot = firstAvailableSpot
            println("${car.color} car parked in spot $firstAvailableSpot.")
            findAvailableSpot()
        } else {
            println("Sorry, the parking lot is full.")
        }
    }

    fun leaveParking(spot: Int) {
        if (parkingSpots[spot - 1]  == null) {
            println("There is no car in spot $spot.")
        } else {
            parkingSpots[spot - 1] = null
            println("Spot $spot is free.")
            findAvailableSpot()
        }
    }

    fun getStatus() {
        if (parkingSpots.isEmpty()) {
            println("Parking lot is empty.")
        } else {
            for (car in parkingSpots) {
                if (car == null) continue
                println("${car.spot} ${car.numberplate} ${car.color}")
            }
        }
    }

    fun regByColor(color: String) {
        val matchingCars = mutableListOf<Car>()
        for (spot in parkingSpots) {
            if(spot == null) { continue }
            else if(spot.color.lowercase() == color.lowercase())
                matchingCars.add(spot)
        }
        val sb = StringBuilder()
        if (matchingCars.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            for (car in matchingCars) sb.append(car.numberplate).append(", ")
            println(sb.toString().substring(0, sb.length - 2))
        }
    }

    fun spotByColor(color: String) {
        val matchingCars = mutableListOf<Car>()
        for (spot in parkingSpots) {
            if(spot == null) { continue }
            else if(spot.color.lowercase() == color.lowercase())
                matchingCars.add(spot)
        }
        val sb = StringBuilder()
        if (matchingCars.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            for (car in matchingCars) sb.append(car.spot).append(", ")
            println(sb.toString().substring(0, sb.length - 2))
        }
    }

    fun spotByReg(reg: String) {
        val matchingCars = mutableListOf<Car>()
        for (spot in parkingSpots) {
            if(spot == null) { continue }
            else if(spot.numberplate == reg)
                matchingCars.add(spot)
        }
        val sb = StringBuilder()
        if (matchingCars.isEmpty()) {
            println("No cars with registration number $reg were found.")
        } else {
            for (car in matchingCars) sb.append(car.spot).append(", ")
            println(sb.toString().substring(0, sb.length - 2))
        }
    }

    private fun findAvailableSpot() {
        firstAvailableSpot = 0
        for (spot in parkingSpots.indices) {
            if (parkingSpots[spot] == null) {
                firstAvailableSpot = spot + 1
                break
            }
        }
    }
}

data class Car(val numberplate: String = "", val color: String = "", var spot: Int = 0)

fun main() {
    var parkingSize = 0
    var wantToCreate = false

    while (!wantToCreate) {
        val actions = readLine()!!.split(" ")
        when (actions[0]) {
            "create" -> {
                parkingSize = actions[1].toInt()
                wantToCreate = true
            }
            "exit" -> break
            else -> println("Sorry, a parking lot has not been created.")
        }
    }

    var parking = Parking(parkingSize)
    if (wantToCreate) {
        println("Created a parking lot with $parkingSize spots.")
    }

    var commands = List<String?>(3) {null}
    if (wantToCreate) while (commands[0] != "exit") {
            commands = readLine()!!.split(" ")
            when(commands[0]) {
                "park" -> {
                    val car = Car(commands[1], commands[2])
                    parking.parkCar(car)
                }
                "create" -> {
                    parking = Parking(commands[1].toInt())
                    println("Created a parking lot with ${commands[1].toInt()} spots.")
                }
                "leave" -> parking.leaveParking(commands[1].toInt())
                "status" -> parking.getStatus()
                "reg_by_color" -> parking.regByColor(commands[1])
                "spot_by_color" -> parking.spotByColor(commands[1])
                "spot_by_reg" -> parking.spotByReg(commands[1])
            }
        }
}
