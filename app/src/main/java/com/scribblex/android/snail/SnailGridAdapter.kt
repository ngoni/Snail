package com.scribblex.android.snail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

/**
 * Array adapter to render the grid provided.
 * You do not have to make changes here, however you are welcome to if it aids your solution.
 */
class SnailGridAdapter(
    context: Context
) : ArrayAdapter<Tile>(context, 0) {

    fun submitGrid(tileGrid: List<List<Tile>>) {
        clear()
        addAll(tileGrid.flatten())
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.tile_view, parent, false)
        val tile = getItem(position) as Tile
        val colorInt = context.resources.getColor(tile.tileColor, context.theme)
        view.findViewById<View>(R.id.tile_colorview).setBackgroundColor(colorInt)

        return view
    }
}