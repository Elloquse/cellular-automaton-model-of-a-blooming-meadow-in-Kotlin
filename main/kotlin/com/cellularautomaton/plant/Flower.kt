package com.cellularautomaton.plant

import com.cellularautomaton.Tickable
import com.cellularautomaton.meadow.AllSpots
import com.cellularautomaton.meadow.Spot

abstract class Flower : Tickable {

    var plantSpot: Spot? = null
    abstract var plantState: Enum<*>
    private var plantAge: Int = 0
    private var plantNectar: Int = 0
    private var isPollinated: Boolean = false

    open fun germinate() {
        plantSpot?.spotOccupancy = this
        plantState = PlantState.changeState(this, "SEEDLING")
        AllFlowers.removeSeeds(plantSpot?.spotSeedsOn!!)
        plantSpot?.spotSeedsOn?.removeAll { true }
        AllFlowers.addPlant(this)
    }

    private fun growAdult() {
        plantState = PlantState.changeState(this, "ADULT")
    }

    private fun bloom() {
        plantState =  PlantState.changeState(this, "BLOOMING")
    }

    private fun produceNectar() {
        plantNectar += (500..1000).random()
    }

    private fun fruit() {
        plantNectar = 0
        plantState = PlantState.changeState(this, "FRUITION")
    }

    private fun die() {
        plantSpot?.spotOccupancy = null
        plantSpot = null
        plantState = PlantState.changeState(this, "DEAD")
    }


    private fun produceSeed() {
        val x = plantSpot?.getXCoordinate()
        val y = plantSpot?.getYCoordinate()
        for (cell in 20..(20..25).random()) {
            if (x != null && y != null) {
                val spotToSeedOn: Spot? = AllSpots.getSpotObject((x-1..x+1).random(), (y-1..y+1).random())
                if (spotToSeedOn != null) {
                    if (spotToSeedOn.checkSpotAvailability()) {
                        AllFlowers.placeFlower(spotToSeedOn, this::class.simpleName)
                    }
                }
            }
        }
    }

    fun getNectar(currentBeeNectar: Int, nectarLoadCapacity: Int): Int {
        val nectarToReturn: Int = nectarLoadCapacity - currentBeeNectar
        return if (plantNectar >= nectarToReturn) {
            plantNectar -= nectarToReturn
            nectarToReturn
        } else {
            0
        }
    }

    fun pollinate() {
        isPollinated = true
    }

    override fun tick() {
        when (this.plantState.toString()) {
            "SEED" -> {
                if (plantAge > PlantState.getLifeTime(this)) {
                    val germinateOrNot: Boolean? = plantSpot?.willSeedGerminate(this)
                    if (germinateOrNot == true) {
                        plantAge = 1
                        germinate()
                    } else if (germinateOrNot == false) {
                        die()
                    }
                } else {
                    plantAge += 1
                }
            }
            "SEEDLING" -> {
                if (plantAge > PlantState.getLifeTime(this)) {
                    plantAge = 1
                    growAdult()
                } else {
                    plantAge += 1
                }
            }
            "ADULT" -> {
                if (plantAge > PlantState.getLifeTime(this)) {
                    plantAge = 1
                    bloom()
                } else {
                    plantAge += 1
                }
            }
            "BLOOMING" -> {
                if (plantAge > PlantState.getLifeTime(this) && isPollinated) {
                    plantAge = 1
                    fruit()
                } else if (plantAge > PlantState.getLifeTime(this) && !isPollinated) {
                    die()
                } else {
                    plantAge += 1
                    produceNectar()
                }
            }
            "FRUITION" -> {
                //val fruitionTime = (1..PlantState.getLifeTime(this)).random()
                if (plantAge > PlantState.getLifeTime(this)) {
                    die()
                } else {
                    plantAge += 1
                    produceSeed()
                }
            }
        }
    }
}