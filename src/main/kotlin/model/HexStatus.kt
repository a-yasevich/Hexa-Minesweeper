package model

enum class HexStatus{
    NEAR_ONE_BOMB,
    NEAR_TWO_BOMBS,
    NEAR_THREE_BOMBS,
    NEAR_FOUR_BOMBS,
    NEAR_FIVE_BOMBS,
    NEAR_SIX_BOMBS,
    NO_BOMBS_NEARBY,
    EXPLOSION;

    companion object {
        fun statusByNumberOfNeighbours(minedNeighbours: Int): HexStatus {
            return when (minedNeighbours) {
                0 -> NO_BOMBS_NEARBY
                1 -> NEAR_ONE_BOMB
                2 -> NEAR_TWO_BOMBS
                3 -> NEAR_THREE_BOMBS
                4 -> NEAR_FOUR_BOMBS
                5 -> NEAR_FIVE_BOMBS
                6 -> NEAR_SIX_BOMBS
                else -> EXPLOSION
            }
        }
    }
}
