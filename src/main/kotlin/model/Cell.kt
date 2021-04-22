package model

class Cell(val hexX: Int, val hexY: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (hexY != other.hexY) return false
        if (hexX != other.hexX) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hexY
        result = 31 * result + hexX
        return result
    }

    override fun toString(): String {
        return "Cell($hexX, $hexY)"
    }

}