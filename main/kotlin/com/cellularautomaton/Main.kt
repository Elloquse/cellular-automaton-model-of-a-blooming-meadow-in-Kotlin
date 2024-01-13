package com.cellularautomaton

import com.cellularautomaton.meadow.Meadow
import com.cellularautomaton.services.InitialConditions
import java.io.File

fun main(args: Array<String>) {

    val initValuesList: List<String> = File(args[0]).readLines()

    val time: Int = InitialConditions.getTime(initValuesList)
    val statsShowTime: Int = InitialConditions.getStatsShowTime(initValuesList)
    val initialHives: List<List<Int?>> = InitialConditions.getHives(initValuesList)
    val initialFlowers: Map<List<Int?>, String?> = InitialConditions.getFlowers(initValuesList)

    val meadow = Meadow(InitialConditions.getMeadowX(initValuesList), InitialConditions.getMeadowY(initValuesList))
    meadow.initialize(initialHives, initialFlowers)

    for (t in 1..time) {
        if (t % statsShowTime == 0) {
            meadow.showStatistics(t)
        }
        meadow.runNewIteration()
    }

}