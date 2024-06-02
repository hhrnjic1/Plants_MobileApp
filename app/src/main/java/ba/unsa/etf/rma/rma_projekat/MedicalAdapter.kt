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

class MedicalAdapter(private var biljke: ArrayList<Biljka>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medical_mod, parent, false)
        return MedicalModViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderMedical = holder as MedicalModViewHolder

        viewHolderMedical.biljkaNaziv.text = biljke[position].naziv;
        viewHolderMedical.biljkaUpozorenje.text = biljke[position].medicinskoUpozorenje

        val koristi = biljke[position].medicinskeKoristi
        viewHolderMedical.biljkaKorist1.text = koristi.getOrNull(0)?.opis ?: ""
        viewHolderMedical.biljkaKorist2.text = koristi.getOrNull(1)?.opis ?: ""
        viewHolderMedical.biljkaKorist3.text = koristi.getOrNull(2)?.opis ?: ""


        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            var trefleDao : TrefleDAO = TrefleDAO(viewHolderMedical.biljkaImage.context)
            viewHolderMedical.biljkaImage.setImageBitmap(trefleDao.getImage(biljke[position]))
        }

    }

    fun updateBiljke(biljke : ArrayList<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class MedicalModViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val biljkaKorist1: TextView = itemView.findViewById(R.id.korist1Item)
        val biljkaKorist2: TextView = itemView.findViewById(R.id.korist2Item)
        val biljkaKorist3: TextView = itemView.findViewById(R.id.korist3Item)

        init {
            itemView.setOnClickListener {
                biljke.removeIf { biljka ->
                    !biljka.medicinskeKoristi.any { element -> biljke[position].medicinskeKoristi.contains(element) }
                }
                updateBiljke(biljke);
            }
        }

        }
    }







