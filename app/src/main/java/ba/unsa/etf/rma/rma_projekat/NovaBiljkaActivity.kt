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

    private lateinit var medicinskaKorist : ListView
    private lateinit var klimatskiTip : ListView
    private lateinit var zemljisniTip : ListView
    private lateinit var profilOkusa : ListView
    private lateinit var jela : ListView
    private lateinit var naziv :EditText
    private lateinit var porodica :EditText
    private lateinit var medicinskoUpozorenje :EditText
    private lateinit var jelo :EditText
    private lateinit var dodajBiljkuBtn:Button
    //private lateinit var pozicijaZaIzmjenu : String;
    private lateinit var uslikajBiljkuBtn: Button
    private lateinit var slikaIV: ImageView
    private lateinit var jelaList: ArrayList<String>
    private var trenutnoIzabranoJelo: Int = -1
    private lateinit var adapter: ArrayAdapter<String>
    //private var slikaBiljkeBitmap: Bitmap? = null
    private lateinit var dodajJeloBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka_activity)

        zemljisniTip = findViewById<ListView>(R.id.zemljisniTipLV);
        klimatskiTip = findViewById<ListView>(R.id.klimatskiTipLV);
        medicinskaKorist = findViewById<ListView>(R.id.medicinskaKoristLV);
        profilOkusa = findViewById<ListView>(R.id.profilOkusaLV);

        populateListViews();

        naziv = findViewById<EditText>(R.id.nazivET)
        porodica = findViewById<EditText>(R.id.porodicaET)
        medicinskoUpozorenje = findViewById<EditText>(R.id.medicinskoUpozorenjeET)
        jelo = findViewById<EditText>(R.id.jeloET)

        jela = findViewById<ListView>(R.id.jelaLV)

        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
        dodajBiljkuBtn = findViewById<Button>(R.id.dodajBiljkuBtn)

        slikaIV = findViewById(R.id.slikaIV)

        jelaList = ArrayList<String>();
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jela.adapter = adapter;

        val intentZaDodavanje = intent
        val listaBiljaka: ArrayList<Biljka>? =
            intentZaDodavanje.getSerializableExtra("Listabiljaka") as? ArrayList<Biljka>

        uslikajBiljkuBtn.setOnClickListener {
            val intentSlike = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intentSlike.resolveActivity(packageManager) != null) {
                startActivityForResult(intentSlike, 1)
            }
        }

        jela.setOnItemClickListener { parent, view, position, id ->
            jelo.setText(jelaList[position])
            dodajJeloBtn.text = "Izmijeni jelo"
            trenutnoIzabranoJelo = position
        }

        dodajJeloBtn.setOnClickListener {
            val arrayAdapter = jela.adapter as ArrayAdapter<String>
            val jeloA = jelo.text.toString().trim()
            if (jeloA.isNotEmpty() && !jelaList.any { it.equals(jeloA, ignoreCase = true) }) {
                if (trenutnoIzabranoJelo == -1) {
                    //Dodavanje novog jela
                    if (jeloA.length in 3..19) {
                        // Provjerite postoji li jelo već u listi, ne obraćajući pažnju na veličinu slova
                        if (!jelaList.any { it.equals(jeloA, ignoreCase = true) }) {
                            // Ako ne postoji, dodajte jelo u listu i osvježite adapter
                            jelaList.add(jeloA)
                            adapter.notifyDataSetChanged()
                            jelo.text.clear()
                        } else {
                            jelo.error = "Jelo već postoji u listi."
                        }
                    } else {
                        // Ako dužina nije ispravna, postavite grešku na EditText
                        jelo.error = "Dužina jela mora biti između 2 i 20 znakova."
                    }
                } else {
                    // Izmjena postojećeg jela
                    arrayAdapter.remove(arrayAdapter.getItem(trenutnoIzabranoJelo))
                    arrayAdapter.insert(jeloA, trenutnoIzabranoJelo)
                    trenutnoIzabranoJelo = -1 // Resetujemo izbor nakon izmjene
                    dodajJeloBtn.text = "Dodaj jelo"
                }
                jelo.text.clear()
            } else if (trenutnoIzabranoJelo != -1) {
                // Brisanje jela ako je polje prazno a neko jelo je prethodno izabrano
                (jela.adapter as ArrayAdapter<String>).remove(
                    (jela.adapter as ArrayAdapter<String>).getItem(trenutnoIzabranoJelo)
                )
                jelo.text.clear()
                dodajJeloBtn.text = "Dodaj jelo"
                trenutnoIzabranoJelo = -1
            }else {
                jelo.error = "Jelo već postoji ili je polje prazno"
            }
        }
        dodajBiljkuBtn.setOnClickListener {
            if (validirajUnosKorisnika()) {
                val klimatskiTipovi = mutableListOf<KlimatskiTip>()
                val medicinskeKoristi = mutableListOf<MedicinskaKorist>()
                val zemljisniTipovi = mutableListOf<Zemljiste>()
                val izabranaJela = mutableListOf<String>()
                lateinit var izabraniOkus: ProfilOkusaBiljke;

                var indexi = klimatskiTip.checkedItemPositions
                for (i in 0 until indexi.size()) {
                    val position = indexi.keyAt(i)
                    if (indexi.valueAt(i)) {
                        val selectedItem = klimatskiTip.adapter.getItem(position) as String
                        KlimatskiTip.Opis(selectedItem)?.let { it1 -> klimatskiTipovi.add(it1) }
                    }
                }
                indexi = medicinskaKorist.checkedItemPositions
                for (i in 0 until indexi.size()) {
                    val position = indexi.keyAt(i)
                    if (indexi.valueAt(i)) {
                        val selectedItem = medicinskaKorist.adapter.getItem(position) as String
                        MedicinskaKorist.Opis(selectedItem)
                            ?.let { it1 -> medicinskeKoristi.add(it1) }
                    }
                }
                indexi = profilOkusa.checkedItemPositions
                for (i in 0 until indexi.size()) {
                    val position = indexi.keyAt(i)
                    if (indexi.valueAt(i)) {
                        val selectedItem = profilOkusa.adapter.getItem(position) as String
                        selectedItem.trim()
                        izabraniOkus = ProfilOkusaBiljke.Opis(selectedItem)!!
                    }
                }
                indexi = zemljisniTip.checkedItemPositions
                for (i in 0 until indexi.size()) {
                    val position = indexi.keyAt(i)
                    if (indexi.valueAt(i)) {
                        val selectedItem = zemljisniTip.adapter.getItem(position) as String
                        Zemljiste.Opis(selectedItem)?.let { it1 -> zemljisniTipovi.add(it1) }
                    }
                }
                for (i in 0 until jela.count) {
                    val item = jela.getItemAtPosition(i)
                    izabranaJela.add(item.toString())
                }
                listaBiljaka?.add(
                    Biljka(
                        naziv.text.toString(),
                        porodica.text.toString(),
                        medicinskoUpozorenje.text.toString(),
                        medicinskeKoristi,
                        izabraniOkus,
                        izabranaJela,
                        klimatskiTipovi,
                        zemljisniTipovi
                    )
                )
                val intentZaVracanje = Intent(this, MainActivity::class.java)
                intentZaVracanje.putExtra("UpdatanaLista", listaBiljaka)
                startActivity(intentZaVracanje)
            }
        }
        uslikajBiljkuBtn.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
        fun validirajUnosKorisnika(): Boolean {

            var ispravno = true;
            if (naziv.text.length < 2 || naziv.text.length > 20) {
                naziv.setError("Duzina texta mora biti izmedju 2 i 20!")
                ispravno = false
            }
            if (porodica.text.length < 2 || porodica.text.length > 20) {
                porodica.setError("Duzina texta mora biti izmedju 2 i 20!")
                ispravno = false
            }
            if (medicinskoUpozorenje.text.length < 2 || medicinskoUpozorenje.text.length > 20) {
                medicinskoUpozorenje.setError("Duzina texta mora biti izmedju 2 i 20!")
                ispravno = false
            }
            if (medicinskaKorist.checkedItemCount < 1) {
                Toast.makeText(
                    this,
                    "Morate izbrati makar jednu medicinsku korist",
                    Toast.LENGTH_SHORT
                ).show()
                medicinskaKorist.setBackgroundResource(R.drawable.listview_error_border)
                ispravno = false
            }
            if (klimatskiTip.checkedItemCount < 1) {
                Toast.makeText(this, "Morate izbrati makar jedan klimatski tip", Toast.LENGTH_SHORT).show()
                klimatskiTip.setBackgroundResource(R.drawable.listview_error_border)
                ispravno = false
            }
            if (zemljisniTip.checkedItemCount < 1) {
                Toast.makeText(this, "Morate izabrati makar jedan zemljisni tip", Toast.LENGTH_SHORT).show()
                zemljisniTip.setBackgroundResource(R.drawable.listview_error_border)
                ispravno = false
            }
            if (profilOkusa.checkedItemCount < 1) {
                Toast.makeText(this, "Morate izabrati profil okusa", Toast.LENGTH_SHORT).show()
                profilOkusa.setBackgroundResource(R.drawable.listview_error_border)
                ispravno = false
            }
            if (jela.count < 1) {
                Toast.makeText(this, "Morate dodati makar jedno jelo", Toast.LENGTH_SHORT).show()
                ispravno = false
            }
            return ispravno
        }
    private fun populateListViews() {
        val medicinskaKoristValues = MedicinskaKorist.entries.map { it.name }
        val klimatskiTipValues = KlimatskiTip.entries.map { it.name }
        val zemljisniTipValues = Zemljiste.entries.map { it.name }
        val okusiList = ProfilOkusaBiljke.entries.map { it.name }

        val medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristValues)
        medicinskaKorist.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKorist.adapter = medicinskaKoristAdapter

        val klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipValues)
        klimatskiTip.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTip.adapter = klimatskiTipAdapter

        val zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipValues)
        zemljisniTip.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTip.adapter = zemljisniTipAdapter

        val profilOkusaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, okusiList)
        profilOkusa.choiceMode = ListView.CHOICE_MODE_SINGLE
        profilOkusa.adapter = profilOkusaAdapter

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val REQUEST_IMAGE_CAPTURE = 1;
        val prikazSlike = findViewById<ImageView>(R.id.slikaIV)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            prikazSlike.setImageBitmap(imageBitmap)
        }
    }
}