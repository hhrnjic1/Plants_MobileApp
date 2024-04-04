package ba.unsa.etf.rma.rma_projekat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CookingAdapter(private var biljke :ArrayList<Biljka>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cooking_mod,parent,false)
        return CookingModViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderCooking = holder as CookingModViewHolder
        viewHolderCooking.biljkaNaziv.text = biljke[position].naziv
        viewHolderCooking.biljkaOkus.text = biljke[position].profilOkusa.opis


        val jela = biljke[position].jela
        viewHolderCooking.biljkaJelo1.text = jela.getOrNull(0)?.toString() ?: ""
        viewHolderCooking.biljkaJelo2.text = jela.getOrNull(1)?.toString() ?: ""
        viewHolderCooking.biljkaJelo3.text = jela.getOrNull(2)?.toString() ?: ""

        val context: Context = viewHolderCooking.biljkaImage.context
        var id: Int = context.resources.getIdentifier("default_picture", "drawable", context.packageName)
        viewHolderCooking.biljkaImage.setImageResource(id)
    }


    inner class CookingModViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaOkus: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val biljkaJelo1: TextView = itemView.findViewById(R.id.jelo1Item)
        val biljkaJelo2: TextView = itemView.findViewById(R.id.jelo2Item)
        val biljkaJelo3: TextView = itemView.findViewById(R.id.jelo3Item)

        init {
            itemView.setOnClickListener {
                biljke.removeIf {
                    biljka -> biljka.profilOkusa != biljke[position].profilOkusa && !biljka.jela.any {element -> biljke[position].jela.contains(element) }
                }
                updateBiljke(biljke)
            }
        }
    }

    fun updateBiljke(biljke: ArrayList<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
}