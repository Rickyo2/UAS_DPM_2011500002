package com.example.uasdpm2011500002

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpdsn: Adapterdosen
    private lateinit var dtdosen: ArrayList<DATA>
    private lateinit var lvdosen: ListView
    private lateinit var TidakAdaData: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btntambah)
        lvdosen = findViewById(R.id.lvdosen)
        TidakAdaData = findViewById(R.id.TidakAdaData)

        dtdosen = ArrayList()
        adpdsn = Adapterdosen(this@MainActivity, dtdosen)

        lvdosen.adapter =adpdsn

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriData::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = Campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dtdosen.size) { dtdosen.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val matkul = DATA(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpdsn.add(matkul)
                adpdsn.notifyDataSetChanged()
            }
            lvdosen.visibility = View.VISIBLE
            TidakAdaData.visibility  = View.GONE
        } else {
            lvdosen.visibility = View.GONE
            TidakAdaData.visibility = View.VISIBLE
        }
    }
}