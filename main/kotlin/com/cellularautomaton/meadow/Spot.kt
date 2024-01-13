package com.cellularautomaton.meadow

import com.cellularautomaton.bee.Bee
import com.cellularautomaton.bee.Hive
import com.cellularautomaton.plant.AllFlowers
import com.cellularautomaton.plant.Chamomile
import com.cellularautomaton.plant.Dandelion
import com.cellularautomaton.plant.Flower

class Spot (private val x: Int, private val y: Int) {

    private val maxSpotCapacity: Int = 10
    var spotOccupancy: Any? = null
    var spotSeedsOn: MutableList<Flower> = mutableListOf()
    var spotBeesOn: MutableList<Bee> = mutableListOf()

    fun willSeedGerminate(flowerType: Flower): Boolean? {
        val probOfGerm: Double = (spotSeedsOn.count {
            it::class.simpleName == flowerType::class.simpleName
        } / spotSeedsOn.size.toDouble())
        return (probOfGerm > (1.0 - probOfGerm)) && (spotOccupancy == null)

    }

    fun checkSpotAvailability(): Boolean {
        return (spotSeedsOn.size <= maxSpotCapacity) && (spotOccupancy == null)
    }

    fun getPosition(): String {
        return "x=$x y=$y"
    }

    fun getXCoordinate(): Int {
        return x
    }

    fun getYCoordinate(): Int {
        return y
    }

    fun getSpotObject(): String {
        return if (spotOccupancy is Flower) {
            "Flower"
        }  else {
            return ""
        }
    }
}