package ba.unsa.etf.rma.rma_projekat


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var sveBiljke: List<Biljka> = getBiljke()
    private var sveBiljkeList : ArrayList<Biljka> = ArrayList()
    private var filtriraneBiljkeList : ArrayList<Biljka> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var resetButton: Button
    private lateinit var currentAdapter: RecyclerView.Adapter<*>
    private lateinit var novaBiljkaBtn: Button
    private lateinit var brzaPretragaBtn : Button
    private lateinit var colorSpinner : Spinner
    private lateinit var substringEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sveBiljkeList.addAll(sveBiljke)
        napuniListu(sveBiljkeList)

        currentAdapter = MedicalAdapter(filtriraneBiljkeList)
        recyclerView = findViewById(R.id.biljkeRV)
        recyclerView.adapter = currentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        colorSpinner = findViewById(R.id.bojaSPIN)
        substringEditText = findViewById(R.id.pretragaET)
        brzaPretragaBtn = findViewById(R.id.brzaPretraga)
        spinner = findViewById(R.id.modSpinner)

        val listItems = listOf("Medicinski","Kuharski","Botanički")
        val colorList = listOf("red","blue","yellow","orange","purple","brown","green")
        val colorSpinerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,colorList)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,listItems)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = spinnerAdapter
        colorSpinner.adapter = colorSpinerAdapter
        setAdapterBasedOnSpinnerPosition(spinner.selectedItemPosition)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setAdapterBasedOnSpinnerPosition(position)
                if (position == 2) {
                    colorSpinner.visibility = View.VISIBLE
                    substringEditText.visibility = View.VISIBLE
                    brzaPretragaBtn.visibility = View.VISIBLE
                    brzaPretragaBtn.setOnClickListener{
                        val scope = CoroutineScope(Job() + Dispatchers.Main)
                        scope.launch{
                            val substring = substringEditText.text.toString()
                            val color = colorSpinner.selectedItem.toString()
                            var trefleDao : TrefleDAO = TrefleDAO()
                            var plantList  : ArrayList<Biljka> = trefleDao.getPlantsWithFlowerColor(color,substring) as ArrayList<Biljka>
                            currentAdapter = BotanicAdapter(plantList)
                            recyclerView.adapter = currentAdapter
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    }

                } else {
                    colorSpinner.visibility = View.GONE
                    substringEditText.visibility = View.GONE
                    brzaPretragaBtn.visibility = View.GONE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Nista ne radi
            }
        }

        resetButton = findViewById(R.id.resetBtn)
        resetButton.setOnClickListener{
            napuniListu(sveBiljkeList)
            setAdapterBasedOnSpinnerPosition(spinner.selectedItemPosition)
            recyclerView.adapter = currentAdapter
            recyclerView.adapter!!.notifyDataSetChanged()
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