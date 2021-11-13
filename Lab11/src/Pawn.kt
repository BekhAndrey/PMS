class Pawn(_colour: Colour) : ChessFigure {
    override var name = "Pawn"
    private var colour = _colour
    var positionRow: Int = 0
    var positionColumn: Int = 0

    constructor(_colour: Colour, _positionRow: Int, _positionColumn: Int) : this(_colour) {
        positionRow = _positionRow
        positionColumn = _positionColumn
    }

    override fun figureTurn() {
        println("Available spots for pawn:")
        if (positionRow == 8) {
            println("No available spots")
        } else {
            println("$positionColumn-" + (positionRow + 1))
        }
    }


}

fun Pawn.deleteFigure(){
    this.positionColumn = 0
    this.positionRow = 0
}