package com.cellularautomaton.plant

import com.cellularautomaton.Statistics
import com.cellularautomaton.meadow.AllSpots
import com.cellularautomaton.meadow.Spot

object AllFlowers : Statistics {

    private var flowersList: MutableList<Flower> = mutableListOf()

    fun placeFlower(spot: Spot?, flower: String?): Flower {
        val newFlower: Flower = when (flower) {
            "Chamomile" -> Chamomile()
            "Dandelion" -> Dandelion()
            else -> throw Exception("Unidentified flowers, check your initial values")
        }
        newFlower.plantSpot = spot
        newFlower.plantSpot?.spotSeedsOn?.add(newFlower)
        if (!flowersList.contains(newFlower)) {
            flowersList.add(newFlower)
        }
        return newFlower
    }

    fun tick() {
        val flowerListToIterate = flowersList.toList()
        for (flower in flowerListToIterate) {
            flower.tick()
        }
    }

    fun removeSeeds(listOfSeeds: MutableList<Flower>) {
        flowersList.removeAll(listOfSeeds)
    }

    fun addPlant(flower: Flower) {
        flowersList.add(flower)
    }

    override fun getStatistics() {
        val plantMapStats = AllSpots.getFlowersStatistics(flowersList.count { it.plantState.toString() == "DEAD" })
        println("-----Plants statistics-----")
        for (elem in plantMapStats) {
            println("${elem.key} ${elem.value}")
        }
        println()
    }
}