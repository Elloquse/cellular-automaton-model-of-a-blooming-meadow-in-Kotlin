package com.cellularautomaton.plant

class Dandelion : Flower() {

   override var plantState: Enum<*> = PlantState.DandelionState.valueOf("SEED")

}