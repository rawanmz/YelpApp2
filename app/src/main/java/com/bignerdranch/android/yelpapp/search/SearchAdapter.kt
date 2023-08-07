package com.bignerdranch.android.yelpapp.search

import android.view.ViewGroup
import android.view.LayoutInflater
import com.bignerdranch.android.yelpapp.R
import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.view.View
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import javax.inject.Inject

class SearchAdapter(context: Context?, cursor: Cursor?, flags: Int)
    : CursorAdapter(context, cursor, flags) {
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(
                R.layout.search_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.tag = viewHolder
        return view
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val viewHolder = view.tag as ViewHolder
        viewHolder.title.text =
                cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
    }

    fun getSuggestionText(position: Int): String? {
        if (position >= 0 && position < cursor.count) {
            val cursor = cursor
            cursor.moveToPosition(position)
            return cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
        }
        return null
    }

    class ViewHolder(view: View) {
        var title: TextView = view.findViewById<View>(R.id.text) as TextView

    }
}