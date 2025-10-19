package com.example.myapplication

import Place
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class PlaceAdapter(private val context: Context, private var data: MutableList<Place>) : BaseAdapter() {

    override fun getCount(): Int = data.size
    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false)
        val place = data[position]

        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtAddress = view.findViewById<TextView>(R.id.txtAddress)
        val txtDesc = view.findViewById<TextView>(R.id.txtDesc)


        txtName.text = place.name
        txtAddress.text = place.location   // 你 Place 裡是用 address，不是 location
        txtDesc.text = place.description


        // 設定點擊事件，點擊整個項目時，跳出選擇要開地圖或瀏覽器
        view.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("選擇操作")
                .setItems(arrayOf("開啟地圖", "瀏覽網頁")) { dialog, which ->
                    when (which) {
                        0 -> openMap(place.location)
                        1 -> openWeb(place.webUrl)
                    }
                }
                .show()
        }

        return view
    }

    // 新增的更新資料方法
    fun updateData(newData: List<Place>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    private fun openMap(query: String) {
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
        Intent(Intent.ACTION_VIEW, uri).run {
            context.startActivity(this)
        }
    }

    private fun openWeb(url: String) {
        val uri = Uri.parse(url)
        Intent(Intent.ACTION_VIEW, uri).run {
            context.startActivity(this)
        }
    }

}
