package com.example.uasdpm2011500002

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class Campuss(context: Context): SQLiteOpenHelper(context, "kampus", null, 1){
    var nidn = ""
    var nmdosen = ""
    var jabatan = ""
    var golonganpangkat =""
    var pendidikan =""
    var keahlian = ""
    var programstudi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            NIDN char(10) primary key,
            nama_dosen varchar(50) not null,
            Jabatan varchar (15) not null,
            GolonganPangkat varchar (30) not null,
            Pendidikan char (2) not null,
            Keahlian varchar (30) not null,
            ProgramStudi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nidn", nidn)
            put("nm_dosen", nmdosen)
            put("jabatan", jabatan)
            put("golonganpangkat", golonganpangkat)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("programstudi", programstudi)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }
    fun ubah(kode:String): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nm_dosen", nmdosen)
            put("jabatan", jabatan)
            put("golonganpangkat", golonganpangkat)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("programstudi", programstudi)
        }
        val cmd = db.update(tabel, cv, "nidn = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
    fun hapus(kode:String): Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "nidn = ?", arrayOf(kode))
        return cmd != -1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}