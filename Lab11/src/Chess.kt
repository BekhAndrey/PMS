import java.awt.Point

fun main() {
    val rook = Rook(Colour.BLACK,5,2)
    rook.figureTurn()
    val pawn = Pawn(Colour.WHITE, 8,5)
    pawn.figureTurn()
    val bishop = Bishop(Colour.WHITE, 3, 7)
    bishop.figureTurn()
    val knight = Knight(Colour.WHITE, 2, 2)
    knight.figureTurn()
    val queen = Queen(Colour.WHITE, 5, 4)
    queen.figureTurn()
}

interface ChessFigure {
    var name: String
    fun figureTurn()
}

fun isValidTile(pos:Int): Boolean{
    return pos in 0..63
}


enum class Colour {
    BLACK,
    WHITE
}

data class Player(val name:String, val rating:Int, val wins:Int, val loses:Int){}

class A{
    private lateinit var prop: String
    fun setUp() {
        prop = "100 + 200"
    }
    fun display() {
        if(::prop.isInitialized){
            println(prop)
        }
    }

    override operator fun equals(other: Any?) : Boolean{
        return true
    }

    fun compareTo(other: A): Int{
        return compareValuesBy(this, other, A::prop)
    }

    fun converter (which: String): Unit? {
        fun plus(){ println("i'm a plus function")}
        fun minus() {println("i'm a minus function")}
        fun divide() {println("i'm a divide function")}
        fun multiply() {println("i'm a multiple function")}

        return when (which) {
            "+" -> plus()
            "-" -> minus()
            "/" -> divide()
            "*" -> multiply()
            else -> null
        }
    }

}

operator fun Point.plus(other: Point) : Point{
    return Point(x + other.x, y + other.y)
}

