package com.codingblocks.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var PLAYER_X = true
    var TURN_COUNT = 0
    var boardStatus = Array(3) { IntArray(3) }

    lateinit var board: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = arrayOf(
            arrayOf(button, button2, button3),
            arrayOf(button4, button5, button6),
            arrayOf(button7, button8, button9)
        )
        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }
        initializeBoardStatus()

        resetBtn.setOnClickListener {
            PLAYER_X = true
            TURN_COUNT = 0
            initializeBoardStatus()
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.button -> {
                updateValue(row = 0, col = 0, player = PLAYER_X)
            }
            R.id.button2 -> {
                updateValue(row = 0, col = 1, player = PLAYER_X)
            }
            R.id.button3 -> {
                updateValue(row = 0, col = 2, player = PLAYER_X)
            }
            R.id.button4 -> {
                updateValue(row = 1, col = 0, player = PLAYER_X)
            }
            R.id.button5 -> {
                updateValue(row = 1, col = 1, player = PLAYER_X)
            }
            R.id.button6 -> {
                updateValue(row = 1, col = 2, player = PLAYER_X)
            }
            R.id.button7 -> {
                updateValue(row = 2, col = 0, player = PLAYER_X)
            }
            R.id.button8 -> {
                updateValue(row = 2, col = 1, player = PLAYER_X)
            }
            R.id.button9 -> {
                updateValue(row = 2, col = 2, player = PLAYER_X)

            }
        }

        TURN_COUNT++
        PLAYER_X = !PLAYER_X
        if (PLAYER_X) {
            result("Player X turn");
        } else {
            result("Player 0 turn");
        }
        if (TURN_COUNT == 9) {
            result("Game Draw");
        }

        checkWinner();
    }

    private fun checkWinner() {
        //Horizontal --- rows
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    result("Player X winner")
                    break
                } else if (boardStatus[i][0] == 0) {
                    result("Player 0 winner")
                    break
                }
            }
        }

        //Vertical --- columns
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    result("Player X winner")
                    break
                } else if (boardStatus[0][i] == 0) {
                    result("Player 0 winner")
                    break
                }
            }
        }

        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0]== boardStatus[2][2])

        //First diagonal
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                result("Player X winner")
            } else if (boardStatus[0][0] == 0) {
                result("Player 0 winner")
            }
        }

        //Second diagonal
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                result("Player X winner")
            } else if (boardStatus[0][2] == 0) {
                result("Player 0 winner")
            }
        }
    }

    private fun result(result: String) {
        displayTv.text = result
        if (result.contains("winner")) {
            disableButton()
        } else {

        }

    }

    private fun disableButton() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }


    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if (player) "X" else "0"
        val value = if (player) 1 else 0
        board[row][col].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][col] = value
    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
            }
        }

        for (i in board) {
            for (button in i) {
                button.isEnabled = true
                button.text = ""

            }
        }


    }


}

