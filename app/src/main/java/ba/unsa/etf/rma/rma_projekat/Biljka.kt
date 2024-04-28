package ba.unsa.etf.rma.rma_projekat

import java.io.Serializable

class Biljka : Serializable{
    lateinit var naziv :String
    lateinit var porodica :String
    lateinit var medicinskoUpozorenje :String
    lateinit var medicinskeKoristi :List<MedicinskaKorist>
    lateinit var profilOkusa :ProfilOkusaBiljke
    lateinit var jela :List<String>
    lateinit var klimatskiTipovi :List<KlimatskiTip>
    lateinit var zemljisniTipovi :List<Zemljiste>

    constructor(
        naziv: String,
        porodica: String,
        medicinksoUpozorenje: String,
        medicinskeKoristi: List<MedicinskaKorist>,
        profilOkusa: ProfilOkusaBiljke,
        jela: List<String>,
        klimatskiTipovi: List<KlimatskiTip>,
        zemljisniTipovi: List<Zemljiste>
    ) {
        this.naziv = naziv
        this.porodica = porodica
        this.medicinskoUpozorenje = medicinksoUpozorenje
        this.medicinskeKoristi = medicinskeKoristi
        this.profilOkusa = profilOkusa
        this.jela = jela
        this.klimatskiTipovi = klimatskiTipovi
        this.zemljisniTipovi = zemljisniTipovi
    }
}