package com.cellularautomaton.bee
import com.cellularautomaton.Statistics
import com.cellularautomaton.meadow.Spot


object AllHives : Statistics {

    private val hivesList: MutableList<Hive> = mutableListOf()

    fun placeHive(spot: Spot?) {
        val newHive = Hive(spot)
        newHive.hiveSpot?.spotOccupancy = newHive
        hivesList.add(newHive)
    }

    fun tick() {
        for (hive in hivesList) {
            hive.tick()
        }
    }

    fun getTotalBees(): Int {
        var totalBees: Int = 0
        for (hive in hivesList) {
            totalBees += hive.getBees().size
        }
        return totalBees
    }

    override fun getStatistics() {
        println("-----Hives statistics-----")
        for (hive in hivesList) {
            println("Hive position: ${hive.hiveSpot?.getPosition()}")
            println("    Bees alive: ${hive.beesList.size}")
            println("    Bees dead: ${hive.beesDead}")
            println("    Generations have past: ${hive.beesGeneration}")
            println("    Total nectar collected: ${hive.nectarTotal}")
            println("    Total nectar spent: ${hive.nectarSpent}")
            println("    Current nectar: ${hive.nectarCurrent}")
            println()
        }
        println()
    }
}