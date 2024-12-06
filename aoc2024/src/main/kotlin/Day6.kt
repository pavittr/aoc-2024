package code

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IO.println

fun main() {
    val gridSpots = BufferedReader(FileReader(File("data/day6.real"))).lines()
    .toList().mapIndexed { y, line ->
        line.mapIndexed { x, location ->
            GridSpot(x,y, location.toString())
        }.toList()
    }.flatten()
    val grid = Grid(gridSpots)
    val gaurdStart = gridSpots.first { it.v == "^" }
    val gaurd = Guard(gaurdStart,Direction.NORTH,  grid)

    val visited = mutableSetOf<GridSpot>()
    while(gaurd.gridSpot.v != "E") {
       visited.add(gaurd.next())
    }

    println(visited.size)


    // Rethink this If I turned right now, would I end up walking a root that touched itself?
    //so, for each step, if I've not been here before in this direction
    // if I turned right and then started again, would I bump into myself?
    // Need a new run trhough for that, so a new guard, and to insert a new grid spot
    // Its above 503, below 3800, and not 897ish

    val potentialObstacles = mutableSetOf<GridSpot>()
    val guard = Guard(gridSpots.first { it.v == "^" },Direction.NORTH,  grid)
    while(guard.gridSpot.v != "E") {
        // See if this square leads to a loop
        if (placeBarrierAtNextAndWalk(grid, guard)) {
            potentialObstacles.add(grid.get(guard.direction.nextCoords(guard.gridSpot)))
            println(""+guard.gridSpot+""+ guard.direction)
            println(guard.direction.nextCoords(guard.gridSpot))
        }
        // Now move forward and repeat
        guard.next()


    }

    println(potentialObstacles.size)

}

fun placeBarrierAtNextAndWalk(currentGrid: Grid, currentGuard: Guard ): Boolean {
    // Is the step ahead of the guard free?
    val stepAhead = currentGrid.get(currentGuard.direction.nextCoords(currentGuard.gridSpot))
    if (stepAhead.v == "." ){
        val newGridspots = currentGrid.gridSpots.filter { it.x != stepAhead.x || it.y != stepAhead.y }.union(listOf(GridSpot(stepAhead.x, stepAhead.y, "#"))).toList()
        // This is viable to be turned into an obstacle. DO it and then walk the new map form the start until either they walk off, or they hit a point they already hit
        val grid = Grid(newGridspots)
        val guard = Guard(newGridspots.first { it.v =="^" }, Direction.NORTH, grid)
        val visitedSpots = mutableSetOf<Pair<Direction, GridSpot>>()

        while (guard.gridSpot.v != "E") {
            if (visitedSpots.contains(Pair(guard.direction, guard.gridSpot)) ) {
                // We went this way already, so this is now a loop
                return true
            }
            visitedSpots.add(Pair(guard.direction, guard.gridSpot))
            guard.next()
        }


    }
    return false
}


class Grid(val gridSpots: List<GridSpot>) {



    fun get(coord: Pair<Int, Int>) : GridSpot {
        return gridSpots.firstOrNull {
            it.x == coord.first && it.y == coord.second
        }?: GridSpot(coord.first,coord.second, "E")
    }
}

data class GridSpot(val x: Int, val y: Int, val v: String) {



}

enum class Direction(val xchange: Int, val yChange: Int) {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);
    fun nextCoords(spot: GridSpot) : Pair<Int, Int> {
        return Pair(spot.x+this.xchange, spot.y+ this.yChange)
    }
    fun turnRight() : Direction {
        return when(this) {
            NORTH -> EAST
            SOUTH -> WEST
            EAST -> SOUTH
            WEST -> NORTH
        }
    }
}


class Guard(var gridSpot: GridSpot, var direction: Direction, val grid: Grid) {
    fun next(): GridSpot {
        val newCoords = direction.nextCoords(gridSpot)
        val nextSpot = grid.get(newCoords)
        if (nextSpot.v == "#") {
            direction = direction.turnRight()
            return gridSpot
        }
        gridSpot = nextSpot
        return nextSpot

    }

}