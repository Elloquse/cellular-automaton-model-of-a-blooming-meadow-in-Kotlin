package com.cellularautomaton.meadow
import com.cellularautomaton.bee.AllHives
import com.cellularautomaton.plant.AllFlowers

class Meadow (private val xSize: Int, private val ySize: Int) {

    private val spotsObject = AllSpots(xSize, ySize)

    fun initialize(hives: List<List<Int?>>, flowers: Map<List<Int?>, String?>) {
        spotsObject.addFlowers(flowers)
        spotsObject.addHives(hives)
    }

    fun runNewIteration() {
        AllFlowers.tick()
        AllHives.tick()
    }

    fun showStatistics(time: Int) {
        println("Day: $time")
        AllFlowers.getStatistics()
        AllHives.getStatistics()
    }

    fun getMeadowSize(): List<Int> {
        return listOf<Int>(xSize, ySize)
    }


}