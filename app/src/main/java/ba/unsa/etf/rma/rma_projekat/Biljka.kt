package ba.unsa.etf.rma.rma_projekat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "biljka")
class Biljka : Serializable{
    @PrimaryKey(autoGenerate = true) val id: Int = 0
    @ColumnInfo(name = "naziv") var naziv :String
    @ColumnInfo(name = "porodica") var porodica :String
    @ColumnInfo(name = "medicinskoUpozorenje") var medicinskoUpozorenje :String
    @ColumnInfo(name = "medicinskeKoristi") var medicinskeKoristi :List<MedicinskaKorist>
    @ColumnInfo(name = "profilOkusa") var profilOkusa :ProfilOkusaBiljke
    @ColumnInfo(name = "jela") var jela :List<String>
    @ColumnInfo(name = "klimatskiTipovi") var klimatskiTipovi :List<KlimatskiTip>
    @ColumnInfo(name = "zemljisniTipovi") var zemljisniTipovi :List<Zemljiste>
    @ColumnInfo(name = "onlineChecked") var onlineChecked: Boolean = false

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