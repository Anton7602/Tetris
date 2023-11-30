package com.robkov.game.tetris.models

import kotlin.random.Random

class Tetromino {
    var figureType: Int = 0
    var rotationState: Int = 1
    var elementsCoordinates: MutableList<Pair<Int, Int>> = mutableListOf()

    fun moveDown() {
        for (i in 0..<elementsCoordinates.size) {
            elementsCoordinates[i] = Pair(elementsCoordinates[i].first, elementsCoordinates[i].second-1)
        }
    }

    fun moveRight() {
        for (i in 0..<elementsCoordinates.size) {
            elementsCoordinates[i] = Pair(elementsCoordinates[i].first+1, elementsCoordinates[i].second)
        }
    }

    fun moveLeft() {
        for (i in 0..<elementsCoordinates.size) {
            elementsCoordinates[i] = Pair(elementsCoordinates[i].first-1, elementsCoordinates[i].second)
        }
    }

    fun rotate(clockWise: Boolean) {
        val coreCoordinate = elementsCoordinates[0]
        elementsCoordinates.clear()
        elementsCoordinates.add(coreCoordinate)
        when(figureType) {
            1 -> {
                if(rotationState==1) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                } else if(rotationState==2) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                } else if(rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                } else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                }
            }
            2 -> {
                if(rotationState==1) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second+1))
                } else if(rotationState==2) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second-1))
                } else if(rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second-1))
                } else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second+1))
                }
            }
            3 -> {
                if (rotationState==1 || rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second-1))
                }
                else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second+1))
                }
            }
            4-> {
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second-1))
            }
            5-> {
                if(rotationState==1) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second+1))
                } else if(rotationState==2) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second+1))
                } else if(rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second-1))
                } else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second-1))
                }
            }
            6 -> {
                if (rotationState==1 || rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second+1))
                }
                else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second-1))
                }
            }
            7-> {
                if (rotationState==1 || rotationState==3) {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+1))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second+2))
                }
                else {
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                    elementsCoordinates.add(Pair(elementsCoordinates[0].first-2, elementsCoordinates[0].second))
                }

            }
        }
        if (clockWise) {
            if (rotationState==4) {
                rotationState=1
            } else {
                rotationState++
            }
        }
        else {
            if (rotationState==1) {
                rotationState=4
            } else {
                rotationState--
            }
        }
    }

    fun generateNew() {
        elementsCoordinates.clear()
        figureType = Random.nextInt(7)+1
        rotationState = 1
        when (figureType) {
            1 -> {
                //T
                elementsCoordinates.add(Pair(Random.nextInt(8)+1, 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
            }
            2 -> {
                //Reverse L
                elementsCoordinates.add(Pair(Random.nextInt(8)+1, 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second+1))
            }
            3 -> {
                //Z
                elementsCoordinates.add(Pair(Random.nextInt(9), 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second+1))
            }
            4 -> {
                //Square
                elementsCoordinates.add(Pair(Random.nextInt(9), 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second-1))
            }
            5 -> {
                //L
                elementsCoordinates.add(Pair(Random.nextInt(8)+1, 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second-1))
            }
            6 -> {
                //Reverse Z
                elementsCoordinates.add(Pair(Random.nextInt(8)+1, 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first, elementsCoordinates[0].second-1))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second-1))
            }
            7 -> {
                //Line
                elementsCoordinates.add(Pair(Random.nextInt(7)+2, 16))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first+1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-1, elementsCoordinates[0].second))
                elementsCoordinates.add(Pair(elementsCoordinates[0].first-2, elementsCoordinates[0].second))
            }

        }
    }
}