class Rook(_colour: Colour) : ChessFigure {
    override var name = "Rook"
    var colour = _colour
    var positionRow: Int = 0
    var positionColumn: Int = 0

    constructor(_colour: Colour, _positionRow: Int, _positionColumn: Int) : this(_colour) {
        positionRow = _positionRow
        positionColumn = _positionColumn
    }

    override fun figureTurn() {
        println("Available spots for rook:")
        printAvailable(positionRow, positionColumn)
        println()
        printAvailable(positionColumn, positionRow)
    }

    private fun printAvailable(x: Int, y: Int) {
        for (i in 1..7) {
            if (i < x) {
                println("$y-" + (x - i))
            } else {
                when (x) {
                    1 -> {
                        println("$y-" + (x + i))
                    }
                    8 -> {
                        println("$y-" + (x - i))
                    }
                    else -> {
                        println("$y-" + (x + (8 - i)))
                    }
                }
            }
        }
    }
}

fun Rook.deleteFigure(){
    this.positionColumn = 0
    this.positionRow = 0
}