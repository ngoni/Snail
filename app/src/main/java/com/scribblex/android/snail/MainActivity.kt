package com.scribblex.android.snail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import com.scribblex.android.snail.Direction.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var snailGrid: GridView
    private lateinit var scoreTextView: TextView
    private lateinit var upButton: Button
    private lateinit var downButton: Button
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var gridAdapter: SnailGridAdapter
    private lateinit var currentPxn: Pair<Int, Int>
    private lateinit var applePxn: Pair<Int, Int>

    private var count: Int = 0
    private val gridSize: Int = 7
    private var tileGrid: MutableList<MutableList<Tile>> =
        //Row
        MutableList(gridSize) {
            //Column
            MutableList(gridSize) { Tile(tileColor = R.color.gray) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        initializeGrid()
        currentPxn = addSnailOnGrid()
        applePxn = addAppleOnGrid()
        updateGridUiWithTiles()
    }

    private fun addSnailOnGrid(): Pair<Int, Int> {
        tileGrid[3][0] = Tile(tileColor = R.color.orange)
        return Pair(3, 0)
    }

    private fun moveSnail(row: Int, column: Int) {
        tileGrid[row][column] = Tile(tileColor = R.color.orange)
        updateGridUiWithTiles()
    }

    private fun initializeViews() {
        snailGrid = findViewById(R.id.snail_gridview)
        scoreTextView = findViewById(R.id.score_textview)
        upButton = findViewById(R.id.up_button)
        downButton = findViewById(R.id.down_button)
        leftButton = findViewById(R.id.left_button)
        rightButton = findViewById(R.id.right_button)

        scoreTextView.text = getString(R.string.score_text, 0)
        upButton.setOnClickListener { tapped(UP) }
        downButton.setOnClickListener { tapped(DOWN) }
        leftButton.setOnClickListener { tapped(LEFT) }
        rightButton.setOnClickListener { tapped(RIGHT) }
    }

    private fun initializeGrid() {
        gridAdapter = SnailGridAdapter(this)
        snailGrid.numColumns = gridSize
        snailGrid.adapter = gridAdapter
        gridAdapter.submitGrid(tileGrid)
    }

    private fun tapped(direction: Direction) {
        val row = currentPxn.first
        val column = currentPxn.second

        when (direction) {
            UP -> {
                if (row > 0) {
                    moveSnail(row - 1, column)
                    clearPreviousTile(row, column)
                    currentPxn = Pair(row - 1, column)
                }

            }
            DOWN -> {
                if (row < gridSize - 1) {
                    moveSnail(row + 1, column)
                    clearPreviousTile(row, column)
                    currentPxn = Pair(row + 1, column)
                }
            }
            LEFT -> {
                if (column > 0) {
                    moveSnail(row, column - 1)
                    clearPreviousTile(row, column)
                    currentPxn = Pair(row, column - 1)
                }
            }
            RIGHT -> {
                if (column < gridSize - 1) {
                    moveSnail(row, column + 1)
                    clearPreviousTile(row, column)
                    currentPxn = Pair(row, column + 1)
                }
            }
        }

        if (applePxn == currentPxn) {
            count++
            scoreTextView.text = getString(R.string.score_text, count)
            applePxn = addAppleOnGrid()
        }
    }

    private fun clearPreviousTile(row: Int, column: Int) {
        tileGrid[row][column] = Tile(tileColor = R.color.gray)
        updateGridUiWithTiles()
    }


    private fun addAppleOnGrid(): Pair<Int, Int> {
        val row = Random.nextInt(7)
        val column = Random.nextInt(7)
        tileGrid[row][column] = Tile(tileColor = R.color.red)
        return Pair(row, column)
    }

    private fun updateGridUiWithTiles() {
        //TODO: Modify the grid here

        //Posts your updates to the gridview
        gridAdapter.submitGrid(tileGrid)
    }
}