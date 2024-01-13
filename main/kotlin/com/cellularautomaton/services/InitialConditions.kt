package com.cellularautomaton.services

class InitialConditions () {
    companion object {

        fun getMeadowX(file: List<String>): Int {
            return file[0].filter { it.isDigit() }.toInt()
        }

        fun getMeadowY(file: List<String>): Int {
            return file[1].filter { it.isDigit() }.toInt()
        }

        fun getTime(file: List<String>): Int {
            return file[2].filter { it.isDigit() }.toInt()
        }

        fun getStatsShowTime(file: List<String>): Int {
            return file[3].filter { it.isDigit() }.toInt()
        }

        fun getHives(file: List<String>): List<List<Int?>> {
            val regExpX = Regex("\\d(?=[|])")
            val regExpY = Regex("\\d(?![|])")
            return file.filter { it.contains("Hive", ignoreCase = true)}.map { listOf(regExpX.find(it)?.value?.toInt(), regExpY.find(it)?.value?.toInt()) }
        }

        fun getFlowers(file: List<String>): Map<List<Int?>, String?> {
            val regExpX = Regex("\\d(?=[|])")
            val regExpY = Regex("\\d(?![|])")
            val flowerSpecies = Regex("[a-zA-Z]*(?=[|])")


            val flowersList: List<String?> = file.filter { it.contains("Flower", ignoreCase = true)}.map { flowerSpecies.find(it)?.value }
            val flowersSpotsList: List<List<Int?>> = file.filter { it.contains("Flower", ignoreCase = true)}.map { listOf(regExpX.find(it)?.value?.toInt(), regExpY.find(it)?.value?.toInt()) }

            return flowersSpotsList.zip(flowersList).toMap()
        }
    }
}