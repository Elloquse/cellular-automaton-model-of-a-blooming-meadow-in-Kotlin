package com.cellularautomaton.bee
import com.cellularautomaton.Tickable
import com.cellularautomaton.meadow.Spot

class Hive(spot: Spot?) : Tickable {
    var hiveSpot: Spot? = spot

    private var beesAlive: Int = (400..500).random()
    var beesList: MutableList<Bee> = mutableListOf()
    var beesDead: Int = 0
    var listOfCurrentDeadBees: MutableList<Bee> = mutableListOf()
    var beesGeneration: Int = 1
    var nectarCurrent: Int = 0
    var nectarTotal: Int = 0
    var nectarSpent: Int = 0
    private val nectarToNewGeneration: Int = 10
    private val maxBeesCapacity: Int = 500

    init {
        for (bee in 1..beesAlive) {
            beesList.add(Bee(this))
        }
    }

    private fun createNewGeneration() {
        for (newBee in 40..(40..70).random()) {
            if (beesList.size <= maxBeesCapacity) {
                beesList.add(Bee(this))
            }
        }
    }

    fun getBees(): MutableList<Bee> {
        return beesList
    }

    override fun tick() {
        val currentBeesList = beesList
        for (bee in currentBeesList) {
            bee.move()
            bee.spotProcessing()
        }
        if (nectarCurrent >= nectarToNewGeneration) {
            createNewGeneration()
            nectarCurrent -= nectarToNewGeneration
            nectarSpent += nectarToNewGeneration
            beesGeneration += 1
        }
        beesList.removeAll(listOfCurrentDeadBees)
    }

    fun takeNectar(nectarByBee: Int) {
        nectarCurrent += nectarByBee
        nectarTotal += nectarByBee
    }
}