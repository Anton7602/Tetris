package com.robkov.game.tetris

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.robkov.game.tetris.models.Tetromino

class MainActivity : AppCompatActivity() {


    private val TAG = "Placeholder"
    private val rows = 15 // Board generates in layout
    private val columns = 10 //Board generates in layout
    private val board = Array(columns) { BooleanArray(rows+4) }
    private val imageViews = Array(columns) { arrayOfNulls<ImageView>(rows) }
    private lateinit var gameThread: Thread
    private lateinit var boardLayout: ConstraintLayout
    private lateinit var buttonLeft: Button
    private lateinit var buttonRight: Button
    private lateinit var buttonRotate: Button
    private lateinit var buttonStart: Button
    private lateinit var textViewScore: TextView
    private lateinit var textViewHighscore: TextView
    private var highscore = 0
    private var currentScore = 0
    private var currentTetromino = Tetromino()
    private val tickDuration: Long = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        highscore = this.getSharedPreferences("tetrisHighscore", Context.MODE_PRIVATE).getInt("Highscore", 0)
        textViewHighscore.text = "Highscore: $highscore"
        textViewScore.text = "Score: 0"


        buttonStart.setOnClickListener {
            buttonStart.visibility = View.GONE
            currentScore = 0
            textViewScore.text = "Score: 0"
            currentTetromino.generateNew()
            //Main Gameplay Loop
            gameThread = Thread {
                while (true) {
                    //Makes current tetromino cells inactive to help with collision checks
                    removeCurrentTetromino()
                    if (canMoveDown()) {
                        //Can move down - move down
                        currentTetromino.moveDown()
                        projectCurrentTetromino()
                    } else {
                        //Can't move down - create new tetromino and check if any lines are filled or Game over condition is triggered
                        projectCurrentTetromino()
                        currentTetromino.generateNew()
                        checkCompleteLines()
                        if (isGameOver()) {
                            //Game over condition triggered - finish the game
                            clearBoard()
                            updateBoard()
                            runOnUiThread {
                                buttonStart.visibility = View.VISIBLE
                            }
                            try {
                                gameThread.interrupt()
                                break
                            } catch (e: Exception) {
                                Log.d(TAG, e.message.toString())
                            }
                        }
                    }
                    updateBoard()
                    Thread.sleep(tickDuration)
                }
            }
            gameThread.start()
        }

        buttonLeft.setOnClickListener {
            removeCurrentTetromino()
            if (canMoveLeft()) {
                currentTetromino.moveLeft()
            }
            projectCurrentTetromino()
            updateBoard()
        }
        buttonRight.setOnClickListener {
            removeCurrentTetromino()
            if (canMoveRight()) {
                currentTetromino.moveRight()
            }
            projectCurrentTetromino()
            updateBoard()
        }
        buttonRotate.setOnClickListener {
            removeCurrentTetromino()
            if (canRotate()) {
                currentTetromino.rotate(true)
            }
            projectCurrentTetromino()
            updateBoard()
        }
    }


    //Checks if there is free space within board below of every tetromino element
    private fun canMoveDown() : Boolean {
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            val elementsColumn = currentTetromino.elementsCoordinates[i].first
            val elementsRow = currentTetromino.elementsCoordinates[i].second
            if (elementsRow==0 ||  board[elementsColumn][elementsRow-1]) {
                return false
            }
        }
        return true
    }

    //Checks if there is free space within board on the left of every tetromino element
    private fun canMoveLeft() : Boolean {
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            val elementsColumn = currentTetromino.elementsCoordinates[i].first
            val elementsRow = currentTetromino.elementsCoordinates[i].second
            if (elementsColumn==0 ||  board[elementsColumn-1][elementsRow]) {
                return false
            }
        }
        return true
    }

    //Checks if there is free space within board on the right of every tetromino element
    private fun canMoveRight() : Boolean {
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            val elementsColumn = currentTetromino.elementsCoordinates[i].first
            val elementsRow = currentTetromino.elementsCoordinates[i].second
            if (elementsColumn==9 ||  board[elementsColumn+1][elementsRow]) {
                return false
            }
        }
        return true
    }
    //Checks if rotation is possible by creating another tetronimo object, rotating it and tracking it;s coordinates

    private fun canRotate() : Boolean {
        val virtualTetronimo = Tetromino()
        virtualTetronimo.rotationState = currentTetromino.rotationState
        virtualTetronimo.figureType = currentTetromino.figureType
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            virtualTetronimo.elementsCoordinates.add(Pair(currentTetromino.elementsCoordinates[i].first, currentTetromino.elementsCoordinates[i].second))
        }
        virtualTetronimo.rotate(true)
        for (i in 0..<virtualTetronimo.elementsCoordinates.size) {
            val elementsColumn = virtualTetronimo.elementsCoordinates[i].first
            val elementsRow = virtualTetronimo.elementsCoordinates[i].second
            if (elementsColumn>9 || elementsColumn<0 || elementsRow<1 ||  board[elementsColumn][elementsRow]) {
                Log.d(TAG, "Preventing Rotation")
                return false
            }
        }
        return true
    }

    //Checks board for filled rows from Top to Bottom
    //Updates score textViews if filled row found
    private fun checkCompleteLines() {
        for (i in rows-1 downTo 0) {
            var isRowComplete = true
            for (j in 0 until columns) {
                if (!board[j][i]) {
                    isRowComplete = false
                    break
                }
            }
            if (isRowComplete) {
                currentScore += 10
                if (currentScore>highscore) {
                    highscore = currentScore
                    val score = this.getSharedPreferences("localData", Context.MODE_PRIVATE)
                    val editor = score.edit()
                    editor.putInt("Highscore", currentScore)
                    editor.apply()
                }
                runOnUiThread{
                    textViewScore.text = "Score: $currentScore"
                    textViewHighscore.text = "Highscore: $highscore"
                }
                removeLine(i)
            }
        }
    }

    //Removes filled line and moves every line above it on row down
    private fun removeLine(index: Int) {
        for (i in index until rows) {
            for (j in 0 until columns) {
                if(i!=rows-1) {
                    board[j][i] = board[j][i+1]
                } else {
                    board[j][i] = false
                }
            }
        }
    }

    //Method checks 16 row of board. If any cell nonEmpty - returns true
    private fun isGameOver() : Boolean {
        for (j in 0 until columns) {
            if (board[j][rows-1]) {
                return true
            }
        }
        return false
    }

    //Makes tetromino coordinates active on the board
    private fun projectCurrentTetromino() {
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            if(currentTetromino.elementsCoordinates[i].second<rows) {
                board[currentTetromino.elementsCoordinates[i].first][currentTetromino.elementsCoordinates[i].second] = true
            }
        }
    }

    //Makes tetromino coordinates inactive on board. Helps with collision checks when need to ignore tetromino elements
    private fun removeCurrentTetromino() {
        for (i in 0..<currentTetromino.elementsCoordinates.size) {
            if(currentTetromino.elementsCoordinates[i].second<rows) {
                board[currentTetromino.elementsCoordinates[i].first][currentTetromino.elementsCoordinates[i].second] = false
            }
        }
    }


    //Colors active elements on Board in active color and inactive elements in White
    private fun updateBoard() {
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                if (board[i][j]) {
                    runOnUiThread{
                        imageViews[i][j]?.setBackgroundColor(Color.BLUE)
                    }
                }
                else {
                    runOnUiThread{
                        imageViews[i][j]?.setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
    }

    //Disactivates all elements on board
    private fun clearBoard() {
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                board[i][j] = false
            }
        }
    }

    private fun bindViews() {
        boardLayout = findViewById(R.id.cly_placeholder_board)
        buttonLeft = findViewById(R.id.btn_placeholder_left)
        buttonRight = findViewById(R.id.btn_placeholder_right)
        buttonRotate = findViewById(R.id.btn_placeholder_rotate)
        buttonStart = findViewById(R.id.btn_start)
        textViewScore = findViewById(R.id.txt_placeholder_current_score)
        textViewHighscore = findViewById(R.id.txt_placeholder_highscore)
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                var viewId = "cell"
                if (i<10) {
                    viewId += 0
                }
                viewId+=i
                if (j<10) {
                    viewId += 0
                }
                viewId+=j
                val resourceID = resources.getIdentifier(viewId, "id", packageName)
                imageViews[i][j] = findViewById(resourceID)
                board[i][j] = false
            }
        }
    }
}