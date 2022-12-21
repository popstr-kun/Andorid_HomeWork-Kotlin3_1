package com.example.chapter7

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.chapter7.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    data class Item(
        val photo: Int,
        val name : String,
        val price: Int
    )

    class MyAdapter(
        context: Context,
        data   : ArrayList<Item>,
        private val layout : Int
    ): ArrayAdapter<Item>(context,layout,data)
    {
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = View.inflate(parent.context,layout,null)
            val item = getItem(position) ?: return view

            val imagePhoto = view.findViewById<ImageView>(R.id.img_photo)
            imagePhoto.setImageResource(item.photo)

            val tvMsg  = view.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = if(layout == R.layout.adaper_vertical) item.name
                         else "${item.name}: ${item.price}元"

            return view
        }
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count      = ArrayList<String>()
        val item       = ArrayList<Item>()
        val priceRange = IntRange(10,100)
        val array      = resources.obtainTypedArray(R.array.image_list)

        for(i in 0 until array.length()){
            val photo = array.getResourceId(i,0)
            val name  = "水果${i+1}"
            val price = priceRange.random()
            count.add("${i+1}個")
            item.add(Item(photo,name,price))
        }
        array.recycle()

        binding.spinner.adapter     = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,count)
        binding.gridView.numColumns = 3
        binding.gridView.adapter    = MyAdapter(this,item,R.layout.adaper_vertical)
        binding.listView.adapter    = MyAdapter(this,item,R.layout.adapter_horizontal)
    }
}


