package com.example.uasdpm2011500002

import android.app.*
import android.content.*
import android.view.*
import android.widget.*

class Adapterdosen (
    private val getContext: Context,
    private val customListItem: ArrayList<DATA>
): ArrayAdapter<DATA>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvnmdosen = listLayout.findViewById(R.id.tvnmdosen)
                tvnidn = listLayout.findViewById(R.id.tvnidn)
                tvstudi = listLayout.findViewById(R.id.tvstudi)
                btnedit = listLayout.findViewById(R.id.btnedit)
                btnhapus = listLayout.findViewById(R.id.btnhapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvnmdosen!!.setText(listItem.nmdosen)
        holder.tvnidn!!.setText(listItem.nidn)
        holder.tvstudi!!.setText(listItem.programstudi)

        holder.btnedit!!.setOnClickListener{
            val i = Intent(context, EntriData::class.java)
            i.putExtra("nidn", listItem.nidn)
            i.putExtra("nama", listItem.nmdosen)
            i.putExtra("jabatan", listItem.jabatan)
            i.putExtra("golpangkat", listItem.golonganpangkat)
            i.putExtra("pendidikan", listItem.pendidikan)
            i.putExtra("keahlian", listItem.keahlian)
            i.putExtra("studi", listItem.programstudi)
            context.startActivity(i)
        }
        holder.btnhapus!!.setOnClickListener {
            val db = Campuss(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvnidn!!.text
            val nama = holder.tvnmdosen!!.text
            val studi = holder.tvstudi!!.text

            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda yakin akan menghapus ini?
                                
                                $nama
                                [$nidn-$studi]
                                """.trimIndent())
                setPositiveButton("Ya"){ _, _ ->
                    if(db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Penghapusan Data gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvnmdosen: TextView? = null
        internal var tvnidn: TextView? = null
        internal var tvstudi:TextView? = null
        internal var btnedit: ImageButton? = null
        internal var btnhapus: ImageButton? = null

    }
}