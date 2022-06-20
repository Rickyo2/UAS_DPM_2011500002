package com.example.uasdpm2011500002

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntriData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entridata)

        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golpangkat") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") &&
                intent.hasExtra("studi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdnidn = findViewById<EditText>(R.id.etkdnidn)
        val etNmDosen = findViewById<EditText>(R.id.etnmdosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnjabatan)
        val spnGolpat = findViewById<Spinner>(R.id.spnpangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rds2)
        val rdS3 = findViewById<RadioButton>(R.id.rds3)
        val etAhli = findViewById<EditText>(R.id.etkeahlian)
        val etStudi = findViewById<EditText>(R.id.etstudi)
        val btnSimpan = findViewById<Button>(R.id.btnsimpan)

        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val adpJabatan = ArrayAdapter(
            this@EntriData,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )

        val pangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@EntriData,
            android.R.layout.simple_spinner_dropdown_item,
            pangkat
        )

        spnGolpat.adapter = adpGolpat
        spnJabatan.adapter = adpJabatan

        if(modeEdit) {
            val Nidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golpat = intent.getStringExtra("golpangkat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val studi = intent.getStringExtra("studi")

            etKdnidn.setText(Nidn)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(etjabatan.indexOf(jabatan))
            spnGolpat.setSelection(pangkat.indexOf(golpat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etAhli.setText(keahlian)
            etStudi.setText(studi)
        }
        etKdnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etKdnidn.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                "${etAhli.text}".isNotEmpty() && "${etStudi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = Campuss(this@EntriData)
                db.nidn = "${etKdnidn.text}"
                db.nmdosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golonganpangkat = spnGolpat.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etAhli.text}"
                db.programstudi = "${etStudi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etKdnidn.text}")) {
                    Toast.makeText(
                        this@EntriData,
                        "Data Dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntriData,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntriData,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}