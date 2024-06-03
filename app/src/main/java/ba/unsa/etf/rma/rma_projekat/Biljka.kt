package ba.unsa.etf.rma.rma_projekat

import java.io.Serializable

class Biljka : Serializable{
     var naziv :String
     var porodica :String
     var medicinskoUpozorenje :String
     var medicinskeKoristi :List<MedicinskaKorist>
     var profilOkusa :ProfilOkusaBiljke
     var jela :List<String>
     var klimatskiTipovi :List<KlimatskiTip>
     var zemljisniTipovi :List<Zemljiste>

    constructor(
        naziv: String,
        porodica: String,
        medicinskoUpozorenje: String,
        medicinskeKoristi: List<MedicinskaKorist>,
        profilOkusa: ProfilOkusaBiljke,
        jela: List<String>,
        klimatskiTipovi: List<KlimatskiTip>,
        zemljisniTipovi: List<Zemljiste>
    ) {
        this.naziv = naziv
        this.porodica = porodica
        this.medicinskoUpozorenje = medicinskoUpozorenje
        this.medicinskeKoristi = medicinskeKoristi
        this.profilOkusa = profilOkusa
        this.jela = jela
        this.klimatskiTipovi = klimatskiTipovi
        this.zemljisniTipovi = zemljisniTipovi
    }
}