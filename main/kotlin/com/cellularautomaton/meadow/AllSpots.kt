package com.cellularautomaton.meadow

import com.cellularautomaton.bee.AllHives
import com.cellularautomaton.plant.AllFlowers
import com.cellularautomaton.plant.Chamomile
import com.cellularautomaton.plant.Dandelion
import com.cellularautomaton.plant.Flower
import java.util.concurrent.Flow

class AllSpots (meadowSizeX: Int, meadowSizeY: Int){

    init {
        for (line in 1..meadowSizeY) {
            for (column in 1..meadowSizeX) {
                spotsMap[listOf(line, column)] = Spot(column, line)
            }
        }
    }

    companion object {
        val spotsMap: MutableMap<List<Int?>, Spot> = mutableMapOf()

        fun getSpotObject(x: Int?, y: Int?): Spot? {
            return spotsMap[listOf(x, y)]
        }

        fun getFlowersStatistics(deadPlants: Int): MutableMap<String, Int> {
            val flowersStatMap = mutableMapOf<String, Int>()
            flowersStatMap["Total flowers: "] = spotsMap.count { it.value.getSpotObject() == "Flower" }
            var seedNumber: Int = 0
            for (spot in spotsMap) {
                seedNumber += spot.value.spotSeedsOn.size
            }
            flowersStatMap["Total SEED: "] = seedNumber
            flowersStatMap["Total SEEDLING: "] = spotsMap.count { it.value.spotOccupancy is Flower && (it.value.spotOccupancy as Flower).plantState.toString() == "SEEDLING" }
            flowersStatMap["Total ADULT: "] = spotsMap.count { it.value.spotOccupancy is Flower && (it.value.spotOccupancy as Flower).plantState.toString() == "ADULT" }
            flowersStatMap["Total BLOOMING: "] = spotsMap.count { it.value.spotOccupancy is Flower && (it.value.spotOccupancy as Flower).plantState.toString() == "BLOOMING" }
            flowersStatMap["Total FRUITION: "] = spotsMap.count { it.value.spotOccupancy is Flower && (it.value.spotOccupancy as Flower).plantState.toString() == "FRUITION" }
            flowersStatMap["Total DEAD: "] = deadPlants
            flowersStatMap["Total Dandelions: "] = spotsMap.count { it.value.spotOccupancy is Dandelion }
            flowersStatMap["Total Chamomiles: "] = spotsMap.count { it.value.spotOccupancy is Chamomile }
            return flowersStatMap
        }
    }

    fun addHives(hives: List<List<Int?>>) {
        hives.forEach {
            AllHives.placeHive(spotsMap[it])
        }
    }

    fun addFlowers(flowers: Map<List<Int?>, String?>) {
        flowers.forEach { (key, value) ->
            AllFlowers.placeFlower(spotsMap[key], value)
        }
    }
}