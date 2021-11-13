class Queen(_colour: Colour) : ChessFigure {
    private val CANDIDATE_MOVE_VECTOR_COORDINATE: Array<Int> = arrayOf(-9, -8,-7,-1, 1, 7,8 ,9)
    override var name = "Queen"
    var colour = _colour
    var positionRow: Int = 0
    var positionColumn: Int = 0

    constructor(_colour: Colour, _positionRow: Int, _positionColumn: Int) : this(_colour) {
        positionRow = _positionRow
        positionColumn = _positionColumn
    }

    override fun figureTurn() {
        println("Available spots for queen:")
        printAvailable(positionRow, positionColumn)
    }

    private fun printAvailable(x: Int, y: Int) {
        for(i in CANDIDATE_MOVE_VECTOR_COORDINATE){
            var position = (x*8)-8+y
            while(isValidTile(position)){
                var row = 0
                var column = 0
                if(isFirstColumnExclusion(position,i) || isEightColumnExclusion(position,i)){
                    break
                }
                position+=i
//                if(isEightColumnExclusion(position,i)){
//                    row = position/8
//                    column = 8
//                    println("$row-$column")
//                    break
//                }
                if(isValidTile(position)) {
                    val row = (position/8)+1
                    val column = position%8
                    println("$row-$column")
                }
            }
        }
    }

    private fun isFirstColumnExclusion(pos: Int, offset:Int): Boolean{
        return (pos==1 || pos==9 || pos==17 || pos==25 || pos==33 || pos==41 || pos==49 || pos==57)
                && (offset==-9 || offset==-1 || offset==7)
    }

    private fun isEightColumnExclusion(pos: Int, offset:Int): Boolean{
        return (pos==8 || pos==16 || pos==24 || pos==32 || pos==40 || pos==48 || pos==56 || pos==64)
                && (offset== -7 || offset==1 || offset==9)
    }
}

fun Queen.deleteFigure(){
    this.positionColumn = 0
    this.positionRow = 0
}