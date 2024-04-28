package ba.unsa.etf.rma.rma_projekat

import android.util.Log

enum class ProfilOkusaBiljke(val opis: String) {
    MENTA("Mentol - osvježavajući, hladan ukus"),
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),
    SLATKI("Sladak okus"),
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),
    LJUTO("Ljuto ili papreno"),
    KORIJENASTO("Korenast - drvenast i gorak ukus"),
    AROMATICNO("Začinski - topli i aromatičan ukus"),
    GORKO("Gorak okus");

    companion object {
        fun Opis(selectedItem: String): ProfilOkusaBiljke? {
            return entries.find {it.name == selectedItem  }
        }
    }
}
