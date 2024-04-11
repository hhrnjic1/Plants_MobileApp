package ba.unsa.etf.rma.rma_projekat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {
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

    private var trenutnoIzabranoJelo: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka_activity)

        initUI()
        setupListeners()
        populateListViews()

        val jelaList = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
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
            val jelo = jeloET.text.toString()
            if (jelo.isNotEmpty()) {
                val adapter = jelaLV.adapter as ArrayAdapter<String>
                if (trenutnoIzabranoJelo == -1) {
                    // Dodavanje novog jela
                    adapter.add(jelo)
                } else {
                    // Izmjena postojećeg jela
                    adapter.remove(adapter.getItem(trenutnoIzabranoJelo))
                    adapter.insert(jelo, trenutnoIzabranoJelo)
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
            }
        }
    }

    private fun populateListViews() {
        val medicinskaKoristValues = MedicinskaKorist.entries.map { it.name }
        val klimatskiTipValues = KlimatskiTip.entries.map { it.name }
        val zemljisniTipValues = Zemljiste.entries.map { it.name }

        val medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristValues)
        medicinskaKoristLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKoristLV.adapter = medicinskaKoristAdapter

        val klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipValues)
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipLV.adapter = klimatskiTipAdapter

        val zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipValues)
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTipLV.adapter = zemljisniTipAdapter
    }

    private fun validateAndAddPlant(): Boolean {
        // Implementirajte logiku za validaciju i dodavanje biljke
        return true
    }
}