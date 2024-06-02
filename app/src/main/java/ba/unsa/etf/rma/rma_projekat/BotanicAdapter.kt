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

class BotanicAdapter(private var biljke :ArrayList<Biljka>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanic_mod,parent,false)
        return BotanicModViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderBotanic = holder as BotanicModViewHolder
        viewHolderBotanic.biljkaNaziv.text = biljke[position].naziv
        viewHolderBotanic.biljkaPorodica.text = biljke[position].porodica
        viewHolderBotanic.biljkaKlima.text = biljke[position].klimatskiTipovi[0].opis
        viewHolderBotanic.biljkaZemljiste.text = biljke[position].zemljisniTipovi[0].naziv

        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            var trefleDao : TrefleDAO = TrefleDAO(viewHolderBotanic.biljkaImage.context)
            viewHolderBotanic.biljkaImage.setImageBitmap(trefleDao.getImage(biljke[position]))
        }
    }

    inner class BotanicModViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaPorodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val biljkaKlima: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val biljkaZemljiste: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        init {
            itemView.setOnClickListener {
                var odabrana = biljke[position]
                biljke.removeIf{biljka ->
                    biljka.porodica != odabrana.porodica ||
                    biljka.klimatskiTipovi.intersect(odabrana.klimatskiTipovi).isEmpty() ||
                    biljka.zemljisniTipovi.intersect(odabrana.zemljisniTipovi).isEmpty()
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