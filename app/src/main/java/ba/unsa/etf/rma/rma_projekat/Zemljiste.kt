package ba.unsa.etf.rma.rma_projekat

enum class Zemljiste(val naziv: String) {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glinеno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");

    companion object {
        fun Opis(selectedItem: String): Zemljiste? {
            return Zemljiste.entries.find {it.name == selectedItem  }
        }
    }
}