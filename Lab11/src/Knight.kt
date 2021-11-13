class Knight(_colour: Colour) : ChessFigure {
    private val CANDIDATE_MOVE_COORDINATE: Array<Int> = arrayOf(-17, -15, -10, -6, 6, 10, 15, 17)
    override var name = "Knight"
    var colour = _colour
    var positionRow: Int = 0
    var positionColumn: Int = 0

    constructor(_colour: Colour, _positionRow: Int, _positionColumn: Int) : this(_colour) {
        positionRow = _positionRow
        positionColumn = _positionColumn
    }

    override fun figureTurn() {
        println("Available spots for knight:")
        printAvailable(positionRow, positionColumn)
    }

    private fun printAvailable(x: Int, y: Int) {
        for (i in CANDIDATE_MOVE_COORDINATE) {
            var position = (x * 8) - 8 + y
            if(isValidTile(position)) {
                var row = 0
                var column = 0
                var offset = position + i
                if (isValidTile(offset)) {
                    if(isFirstColumnExclusion(position,i) ||
                            isSecondColumnExclusion(position,i)||
                            isSeventhColumnExclusion(position,i)||
                            isEightColumnExclusion(position,i)){
                        continue
                    }
                    val row = (offset / 8) + 1
                    val column = offset % 8
                    println("$row-$column")
                }
            }
        }
    }

    private fun isFirstColumnExclusion(pos: Int, offset: Int): Boolean {
        return (pos == 1 || pos == 9 || pos == 17 || pos == 25 || pos == 33 || pos == 41 || pos == 49 || pos == 57)
                && (offset == -17 || offset == -10 || offset ==6 || offset == 15)
    }

    private fun isSecondColumnExclusion(pos: Int, offset: Int): Boolean {
        return (pos == 2 || pos == 10 || pos == 18 || pos == 26 || pos == 34 || pos == 42 || pos == 50 || pos == 58)
                && (offset == -10 || offset ==6)
    }

    private fun isSeventhColumnExclusion(pos: Int, offset: Int): Boolean {
        return (pos == 7 || pos == 15 || pos == 23 || pos == 31 || pos == 39 || pos == 47 || pos == 55 || pos == 63)
                && (offset == -6 || offset == 10)
    }

    private fun isEightColumnExclusion(pos: Int, offset: Int): Boolean {
        return (pos == 8 || pos == 16 || pos == 24 || pos == 32 || pos == 40 || pos == 48 || pos == 56 || pos == 64)
                && (offset == -15 || offset == -6 || offset == 10  || offset == 17)
    }
}

fun Knight.deleteFigure(){
    this.positionColumn = 0
    this.positionRow = 0
}