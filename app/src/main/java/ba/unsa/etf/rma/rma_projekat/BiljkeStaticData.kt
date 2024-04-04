package ba.unsa.etf.rma.rma_projekat

fun getBiljke(): List<Biljka> {
    return listOf(
        Biljka("Bosiljak (Ocimum basilicum)","Lamiaceae (usnate)","Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE), ProfilOkusaBiljke.BEZUKUSNO,listOf("Salata od paradajza", "Punjene tikvice"),
            listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)),

        Biljka("Nana (Mentha spicata)","Lamiaceae (metvice)","Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA), ProfilOkusaBiljke.MENTA,listOf("Jogurt sa voćem", "Gulaš"),
            listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)),

        Biljka("Kamilica (Matricaria chamomilla)","Asteraceae (glavočike)","Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO), ProfilOkusaBiljke.AROMATICNO,listOf("Čaj od kamilice"),
            listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)),

        Biljka("Ružmarin (Rosmarinus officinalis)","Lamiaceae (metvice)","Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA), ProfilOkusaBiljke.AROMATICNO,listOf("Pečeno pile", "Grah","Gulaš"),
            listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)),

        Biljka("Lavanda (Lavandula angustifolia)","Lamiaceae (metvice)","Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),  ProfilOkusaBiljke.AROMATICNO,listOf("Jogurt sa voćem"),
            listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)),

        Biljka("Đumbir (Zingiber officinale)","Đumbirovke (Zingiberaceae)","Oprez kod trudnica, prekomjerna konzumacija đumbira može  utjecati na razvoj fetusa.",
            listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPROBAVE),  ProfilOkusaBiljke.GORKO,listOf("Čaj od đumbira"),
            listOf(KlimatskiTip.UMJERENA),listOf(Zemljiste.CRNICA)),

        Biljka("Majčina dušica (Thymus timijan)","Lamiaceae (usnate)","Majčina dušica može interagirati s određenim lijekovima koji sprječavaju zgrušavanje krvi i lijekovi za kontrolu krvnog tlaka",
            listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),  ProfilOkusaBiljke.AROMATICNO,listOf("Čaj od majčine dušice"),
            listOf(KlimatskiTip.UMJERENA),listOf(Zemljiste.ILOVACA,Zemljiste.PJESKOVITO)),

        Biljka("Peršun (Petroselinum crispum)","Apiaceae (Štitarice)","Osobe koje imaju problema s bubrezima trebaju biti oprezne s konzumacijom peršuna",
            listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),  ProfilOkusaBiljke.BEZUKUSNO,listOf("Peršun pesto", "Gulaš"),
            listOf(KlimatskiTip.UMJERENA),listOf(Zemljiste.CRNICA)),

        Biljka("Cimet (Cinnamomum verum)","Laurels (Lovori)","Cimet može imati antikoagulantna svojstva koja mogu utjecati na zgrušavanje krvi",
            listOf(MedicinskaKorist.REGULACIJAPROBAVE),  ProfilOkusaBiljke.AROMATICNO,listOf("Čaj od cimeta", "Kolač"),
            listOf(KlimatskiTip.TROPSKA,KlimatskiTip.SUBTROPSKA),listOf(Zemljiste.CRNICA)),

        Biljka("Jagoda (Fragaria)","Rosaceae (Ružovke)","Osobe s dijabetesom trebaju biti oprezne s konzumacijom jagoda zbog njihovog prirodnog šećera",
            listOf(MedicinskaKorist.PODRSKAIMUNITETU),  ProfilOkusaBiljke.SLATKI,listOf("Jogurt sa voćem", "Voćna salata", "Kolač"),
            listOf(KlimatskiTip.UMJERENA),listOf(Zemljiste.CRNICA))
    )
}