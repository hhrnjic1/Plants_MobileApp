package ba.unsa.etf.rma.rma_projekat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var nazivET: EditText
    private lateinit var porodicaET: EditText
    private lateinit var medicinskoUpozorenjeET: EditText
    private lateinit var jeloET: EditText
    private lateinit var medicinskaKoristLV: ListView
    private lateinit var klimatskiTipLV: ListView
    private lateinit var zemljisniTipLV: ListView
    private lateinit var profilOkusaLV: ListView
    private lateinit var jelaLV: ListView
    private lateinit var dodajJeloBtn: Button
    private lateinit var dodajBiljkuBtn: Button
    private lateinit var uslikajBiljkuBtn: Button
    private lateinit var slikaIV: ImageView
    private val jelaList = ArrayList<String>()
    private var trenutnoIzabranoJelo: Int = -1
    private lateinit var adapter: ArrayAdapter<String>
    private var slikaBiljkeBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka_activity)

        initUI()
        setupListeners()
        populateListViews()


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaLV.adapter = adapter

        jelaLV.setOnItemClickListener { parent, view, position, id ->
            jeloET.setText(jelaList[position])
            dodajJeloBtn.text = "Izmijeni jelo"
            trenutnoIzabranoJelo = position
        }

    }

    private fun initUI() {
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)
        jeloET = findViewById(R.id.jeloET)
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        jelaLV = findViewById(R.id.jelaLV)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
        slikaIV = findViewById(R.id.slikaIV)
    }

    private fun setupListeners() {
        dodajJeloBtn.setOnClickListener {
            val arrayAdapter = jelaLV.adapter as ArrayAdapter<String>
            val jelo = jeloET.text.toString().trim()
            if (jelo.isNotEmpty() && !jelaList.any { it.equals(jelo, ignoreCase = true) }) {
                if (trenutnoIzabranoJelo == -1) {
                    //Dodavanje novog jela
                    if (jelo.length in 3..19) {
                        // Provjerite postoji li jelo već u listi, ne obraćajući pažnju na veličinu slova
                        if (!jelaList.any { it.equals(jelo, ignoreCase = true) }) {
                            // Ako ne postoji, dodajte jelo u listu i osvježite adapter
                            jelaList.add(jelo)
                            adapter.notifyDataSetChanged()
                            jeloET.text.clear()
                        } else {
                            jeloET.error = "Jelo već postoji u listi."
                        }
                    } else {
                        // Ako dužina nije ispravna, postavite grešku na EditText
                        jeloET.error = "Dužina jela mora biti između 2 i 20 znakova."
                    }
                } else {
                    // Izmjena postojećeg jela
                    arrayAdapter.remove(arrayAdapter.getItem(trenutnoIzabranoJelo))
                    arrayAdapter.insert(jelo, trenutnoIzabranoJelo)
                    trenutnoIzabranoJelo = -1 // Resetujemo izbor nakon izmjene
                    dodajJeloBtn.text = "Dodaj jelo"
                }
                jeloET.text.clear()
            } else if (trenutnoIzabranoJelo != -1) {
                // Brisanje jela ako je polje prazno a neko jelo je prethodno izabrano
                (jelaLV.adapter as ArrayAdapter<String>).remove(
                    (jelaLV.adapter as ArrayAdapter<String>).getItem(trenutnoIzabranoJelo)
                )
                jeloET.text.clear()
                dodajJeloBtn.text = "Dodaj jelo"
                trenutnoIzabranoJelo = -1
            }else {
                jeloET.error = "Jelo već postoji ili je polje prazno"
            }
        }

        uslikajBiljkuBtn.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

        dodajBiljkuBtn.setOnClickListener {
            if (validateEditTextLength(nazivET, 3, 19) &&
                validateEditTextLength(porodicaET, 3, 19) &&
                validateEditTextLength(medicinskoUpozorenjeET, 3, 19) &&
                isAnyItemSelected(medicinskaKoristLV) &&
                isAnyItemSelected(klimatskiTipLV) &&
                isAnyItemSelected(zemljisniTipLV) &&
                jelaList.isNotEmpty() &&
                isAnyItemSelected(profilOkusaLV) &&
                slikaBiljkeBitmap != null) {

                // Svi uslovi su ispunjeni, dodaj biljku



            } else {
                if(!validateEditTextLength(nazivET, 3, 19)){
                    nazivET.error = "Uneseni tekst mora biti izmedju 2 i 20 karaktera!"
                }
                if(!validateEditTextLength(porodicaET, 3, 19)){
                    porodicaET.error = "Uneseni tekst mora biti izmedju 2 i 20 karaktera!"
                }
                if(!validateEditTextLength(medicinskoUpozorenjeET, 3, 19)){
                    medicinskoUpozorenjeET.error = "Uneseni tekst mora biti izmedju 2 i 20 karaktera!"
                }
                if(!isAnyItemSelected(medicinskaKoristLV)){
                    Toast.makeText(this, "Morate odabrati barem jednu medicinsku korist.", Toast.LENGTH_LONG).show()
                    medicinskaKoristLV.setBackgroundResource(R.drawable.listview_error_border)
                }else{
                    medicinskaKoristLV.setBackgroundResource(0)
                }
                if(!isAnyItemSelected(klimatskiTipLV)){
                    Toast.makeText(this, "Morate odabrati barem jedan klimatski tip.", Toast.LENGTH_LONG).show()
                    klimatskiTipLV.setBackgroundResource(R.drawable.listview_error_border)
                }else{
                    klimatskiTipLV.setBackgroundResource(0)
                }
                if(!isAnyItemSelected(zemljisniTipLV)){
                    Toast.makeText(this, "Morate odabrati barem jedan zemljisni tip.", Toast.LENGTH_LONG).show()
                    zemljisniTipLV.setBackgroundResource(R.drawable.listview_error_border)
                }else{
                    zemljisniTipLV.setBackgroundResource(0)
                }
                if(jelaList.isEmpty()){
                    Toast.makeText(this, "Morate dodati makar jedno jelo.", Toast.LENGTH_LONG).show()
                }
                if(!isAnyItemSelected(profilOkusaLV)){
                    Toast.makeText(this, "Morate odabrati profil okusa.", Toast.LENGTH_LONG).show()
                    profilOkusaLV.setBackgroundResource(R.drawable.listview_error_border)
                }else{
                    profilOkusaLV.setBackgroundResource(0)
                }
                if (slikaBiljkeBitmap == null) {
                    // Korisnik nije uslikao biljku, prikažite grešku
                    Toast.makeText(this, "Morate prvo uslikati biljku.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun populateListViews() {
        val medicinskaKoristValues = MedicinskaKorist.entries.map { it.name }
        val klimatskiTipValues = KlimatskiTip.entries.map { it.name }
        val zemljisniTipValues = Zemljiste.entries.map { it.name }
        val okusiList = ProfilOkusaBiljke.entries.map { it.name }

        val medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristValues)
        medicinskaKoristLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKoristLV.adapter = medicinskaKoristAdapter

        val klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipValues)
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipLV.adapter = klimatskiTipAdapter

        val zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipValues)
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTipLV.adapter = zemljisniTipAdapter

        val profilOkusaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, okusiList)
        profilOkusaLV.choiceMode = ListView.CHOICE_MODE_SINGLE
        profilOkusaLV.adapter = profilOkusaAdapter
    }

    private fun validateAndAddPlant(): Boolean {
        // Implementirajte logiku za validaciju i dodavanje biljke
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val slikaIV: ImageView = findViewById(R.id.slikaIV)
            slikaIV.setImageBitmap(imageBitmap)
            slikaBiljkeBitmap = imageBitmap
        }
    }

    private fun isAnyItemSelected(listView: ListView): Boolean {
        for (i in 0 until listView.count) {
            if (listView.isItemChecked(i)) {
                return true
            }
        }
        return false
    }

    private fun validateEditTextLength(editText: EditText, minLen: Int, maxLen: Int): Boolean {
        val text = editText.text.toString()
        if (text.length < minLen || text.length > maxLen) {
            editText.error = "Tekst mora biti duži od $minLen i kraći od $maxLen znakova"
            return false
        }
        return true
    }
}