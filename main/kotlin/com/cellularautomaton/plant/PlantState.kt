package com.cellularautomaton.plant

class PlantState() {

    companion object {
        fun changeState(flower: Flower, toWhichState: String): Enum<*> {
            return when (flower) {
                is Chamomile -> PlantState.ChamomileState.valueOf(toWhichState.uppercase())
                is Dandelion -> PlantState.DandelionState.valueOf(toWhichState.uppercase())
                else -> throw Exception("No such plant state")
            }
        }

        fun getLifeTime(flower: Flower): Int {
            return when (flower) {
                is Chamomile -> PlantState.ChamomileState.valueOf(flower.plantState.toString()).lifetime
                is Dandelion -> PlantState.DandelionState.valueOf(flower.plantState.toString()).lifetime
                else -> throw Exception("No such plant state")
            }
        }
    }

    enum class ChamomileState(val lifetime: Int) {
        SEED(1),
        SEEDLING (1),
        ADULT(1),
        BLOOMING(10),
        FRUITION(10),
        DEAD(0)
    }

    enum class DandelionState(val lifetime: Int) {
        SEED(1),
        SEEDLING(1),
        ADULT(1),
        BLOOMING(10),
        FRUITION(10),
        DEAD(0)
    }
}