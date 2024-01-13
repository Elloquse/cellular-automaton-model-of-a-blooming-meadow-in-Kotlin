package com.cellularautomaton.plant

class Chamomile : Flower() {

    override var plantState: Enum<*> = PlantState.ChamomileState.valueOf("SEED")


}