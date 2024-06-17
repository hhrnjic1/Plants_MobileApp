package ba.unsa.etf.rma.rma_projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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

        var baza = BiljkaDatabase.getDatabase(viewHolderCooking.biljkaImage.context).biljkaDao()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            var trefleDao : TrefleDAO = TrefleDAO(viewHolderCooking.biljkaImage.context)
            var slika = baza.getBitmapById(biljke[position].id)
            if(slika == null){
                var image = trefleDao.getImage(biljke[position])
                baza.addImage(biljke[position].id,image)
                viewHolderCooking.biljkaImage.setImageBitmap(image)
            }else{
                viewHolderCooking.biljkaImage.setImageBitmap(slika)
            }
        }
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