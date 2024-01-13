package com.cellularautomaton.bee
import com.cellularautomaton.meadow.AllSpots
import com.cellularautomaton.meadow.Spot
import com.cellularautomaton.plant.Flower
import kotlin.math.abs

class Bee (hive: Hive) {

    private var beeSpot: Spot? = null
    private val beeHive: Hive = hive
    private val nectarLoadCapacity: Int = 5
    private val maxFlights: Int = 20
    private var currentFlights: Int = 0
    private val maxFlightDistance: Int = 7
    private var currentFlightedDistance: Int = 0
    private var currentNectar: Int = 0
    private var stepsToReturn: Int = 0
    private var currentStepInReturning: Int = 0
    private var caughtPollen: Any? = null

    init {
        beeSpot?.spotBeesOn?.add(this)
        beeSpot = hive.hiveSpot
    }

    fun move() {
        if (currentFlightedDistance == maxFlightDistance) {
            currentFlights += 1
            if (currentFlights == maxFlights) {
                die()
                return
            } else {
                returnToHive()
            }
        } else {
            val x = beeSpot?.getXCoordinate()
            val y = beeSpot?.getYCoordinate()
            if (x != null && y != null) {
                try {
                    val spotToBeeOn = AllSpots.getSpotObject((((x - 1)..(x + 1))).random(), (((y - 1) .. (y + 1))).random())
                    if (spotToBeeOn != null) {
                        beeSpot?.spotBeesOn?.remove(this)
                        beeSpot = spotToBeeOn
                        beeSpot?.spotBeesOn?.add(this)
                        currentFlightedDistance += 1
                    }
                } catch (e: NoSuchElementException) {
                    move()
                }
            }
        }
    }

    fun spotProcessing() {
        if (beeSpot?.spotOccupancy is Flower) {
            catchPollen()
            currentNectar = (beeSpot?.spotOccupancy as Flower).getNectar(currentNectar, nectarLoadCapacity)
        }

    }

    private fun returnToHive() {
        if (stepsToReturn == 0) {
            val hiveX = beeHive.hiveSpot?.getXCoordinate()
            val hiveY = beeHive.hiveSpot?.getYCoordinate()
            val beeX = beeSpot?.getXCoordinate()
            val beeY = beeSpot?.getYCoordinate()

            if (hiveX != null && hiveY != null && beeX != null && beeY != null) {
                stepsToReturn = abs((hiveX + hiveY) - (beeX + beeY))
            }
            beeSpot?.spotBeesOn?.remove(this)
            beeSpot = null
            return
        }

        if (currentStepInReturning == stepsToReturn) {
            beeSpot = beeHive.hiveSpot
            beeSpot?.spotBeesOn?.add(this)
            stepsToReturn = 0
            giveNectarToHive()
            return
        }
        currentStepInReturning += 1
    }

    private fun giveNectarToHive() {
        beeHive.takeNectar(currentNectar)
        currentNectar = 0
    }

    private fun die() {
        beeSpot?.spotBeesOn?.remove(this)
        beeHive.listOfCurrentDeadBees.add(this)
        beeHive.beesDead += 1
    }

    private fun catchPollen() {

        if (caughtPollen == null) {

            caughtPollen = beeSpot?.spotOccupancy
        } else {
            if ((beeSpot?.spotOccupancy as Flower).plantState.toString() == "BLOOMING" &&
                caughtPollen!!::class.simpleName == beeSpot?.spotOccupancy!!::class.simpleName
                ) {
                (beeSpot?.spotOccupancy as Flower).pollinate()
                caughtPollen = null
            }
        }
    }
}