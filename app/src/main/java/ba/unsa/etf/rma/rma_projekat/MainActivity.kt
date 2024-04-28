package ba.unsa.etf.rma.rma_projekat


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var sveBiljke: List<Biljka> = getBiljke()
    private var sveBiljkeList : ArrayList<Biljka> = ArrayList()
    private var filtriraneBiljkeList : ArrayList<Biljka> = ArrayList()

    private lateinit var  recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var  resetButton: Button
    private lateinit var currentAdapter: RecyclerView.Adapter<*>
    private lateinit var novaBiljkaBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sveBiljkeList.addAll(sveBiljke)
        napuniListu(sveBiljkeList)

        currentAdapter = MedicalAdapter(filtriraneBiljkeList)
        recyclerView = findViewById(R.id.biljkeRV)
        recyclerView.adapter = currentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        val listItems = listOf("Medicinski","Kuharski","Botanički")
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,listItems)
        spinner = findViewById(R.id.modSpinner)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        setAdapterBasedOnSpinnerPosition(spinner.selectedItemPosition)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setAdapterBasedOnSpinnerPosition(position);
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Nista ne radi
            }
        }

        resetButton = findViewById(R.id.resetBtn)
        resetButton.setOnClickListener{
            napuniListu(sveBiljkeList)
            recyclerView.adapter = currentAdapter
        }

        novaBiljkaBtn = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener{
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            intent.putExtra("Listabiljaka", sveBiljkeList)
            startActivity(intent)
        }

        val intentVracanja = intent
        var vracenaLista: ArrayList<Biljka>? = intentVracanja.getSerializableExtra("UpdatanaLista") as? ArrayList<Biljka>

        if(vracenaLista != null){
            sveBiljkeList.clear()
            sveBiljkeList.addAll(vracenaLista)
            napuniListu(vracenaLista)
            currentAdapter = MedicalAdapter(filtriraneBiljkeList)
            currentAdapter = CookingAdapter(filtriraneBiljkeList)
            currentAdapter = BotanicAdapter(filtriraneBiljkeList)
            setAdapterBasedOnSpinnerPosition(0)
        }
    }

    private fun setAdapterBasedOnSpinnerPosition(position: Int){

        currentAdapter = when(position){
            0 -> MedicalAdapter(filtriraneBiljkeList)
            1 -> CookingAdapter(filtriraneBiljkeList)
            2 -> BotanicAdapter(filtriraneBiljkeList)
            else -> MedicalAdapter(filtriraneBiljkeList)
        }
        recyclerView.adapter = currentAdapter
    }

    private fun napuniListu(biljke : ArrayList<Biljka>){
        this.filtriraneBiljkeList.clear();
        this.filtriraneBiljkeList.addAll(biljke)
    }

}