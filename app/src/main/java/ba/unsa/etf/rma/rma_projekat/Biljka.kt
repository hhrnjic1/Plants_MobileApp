package ba.unsa.etf.rma.rma_projekat

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "biljka")
class Biljka : Serializable{
    @PrimaryKey(autoGenerate = true) val id: Int = 0
     var naziv :String
     var porodica :String
     var medicinskoUpozorenje :String
     var medicinskeKoristi :List<MedicinskaKorist>
     var profilOkusa :ProfilOkusaBiljke
     var jela :List<String>
     var klimatskiTipovi :List<KlimatskiTip>
     var zemljisniTipovi :List<Zemljiste>
     var onlineChecked: Boolean = false

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