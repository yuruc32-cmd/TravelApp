package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class DiaryAdapter(private val context: Context, private val data: MutableList<PhotoDiaryEntry>) : BaseAdapter() {

    override fun getCount(): Int = data.size
    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.diary_item, parent, false)
        val entry = data[position]

        val imageView = view.findViewById<ImageView>(R.id.imageViewDiary)
        val txtPlace = view.findViewById<TextView>(R.id.txtPlaceName)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)

        imageView.setImageURI(Uri.parse(entry.imageUri))
        txtPlace.text = entry.placeName
        txtDate.text = entry.date

        return view
    }

    fun updateData(newData: List<PhotoDiaryEntry>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
