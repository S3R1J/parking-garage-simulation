import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Fahrzeug {
    private String nummernschild;

    public Fahrzeug() {
        this.nummernschild = generateFrenchLicensePlate();
    }

    public String getNummernschild() {
        return nummernschild;
    }

    private String generateFrenchLicensePlate() {
        Random random = new Random();
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int randomLetterIndex1 = random.nextInt(letters.length);
        int randomLetterIndex2 = random.nextInt(letters.length);
        int randomNumber = 1000 + random.nextInt(9000); // Zufällige 4-stellige Zahl
        char[] numbers = Integer.toString(randomNumber).toCharArray();
        char[] plate = new char[10];
        plate[0] = letters[randomLetterIndex1];
        plate[1] = letters[randomLetterIndex2];
        plate[2] = '-';
        plate[3] = numbers[0];
        plate[4] = numbers[1];
        plate[5] = numbers[2];
        plate[6] = numbers[3];
        plate[7] = '-';
        plate[8] = letters[randomLetterIndex1];
        plate[9] = letters[randomLetterIndex2];
        return new String(plate);
    }
}

class PKW extends Fahrzeug {
    // Hier können spezifische Eigenschaften oder Methoden für PKWs hinzugefügt werden
}

class Motorrad extends Fahrzeug {
    // Hier können spezifische Eigenschaften oder Methoden für Motorräder hinzugefügt werden
}

class Parkhaus {
    private int anzahlEtagen;
    private int parkplaetzeProEtage;
    private List<List<Fahrzeug>> parkplaetze;
    private int freieParkplaetze;

    public Parkhaus(int anzahlEtagen, int parkplaetzeProEtage) {
        this.anzahlEtagen = anzahlEtagen;
        this.parkplaetzeProEtage = parkplaetzeProEtage;
        this.parkplaetze = new ArrayList<>();

        for (int i = 0; i < anzahlEtagen; i++) {
            this.parkplaetze.add(new ArrayList<>(parkplaetzeProEtage));
        }

        this.freieParkplaetze = anzahlEtagen * parkplaetzeProEtage;
    }

    public String einfahren(Fahrzeug fahrzeug) {
        for (int etage = 0; etage < anzahlEtagen; etage++) {
            List<Fahrzeug> etagenParkplaetze = parkplaetze.get(etage);

            if (etagenParkplaetze.size() < parkplaetzeProEtage) {
                etagenParkplaetze.add(fahrzeug);
                freieParkplaetze--;
                return String.format("%s mit Nummernschild %s erfolgreich auf Etage %d, Parkplatz %d geparkt.",
                        fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild(), etage + 1, etagenParkplaetze.size());
            }
        }
        return String.format("Keine freien Parkplätze vorhanden. %s mit Nummernschild %s konnte nicht geparkt werden.",
                fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild());
    }

    public String ausfahren(Fahrzeug fahrzeug) {
        for (int etage = 0; etage < anzahlEtagen; etage++) {
            List<Fahrzeug> etagenParkplaetze = parkplaetze.get(etage);

            if (etagenParkplaetze.contains(fahrzeug)) {
                etagenParkplaetze.remove(fahrzeug);
                freieParkplaetze++;
                return String.format("%s mit Nummernschild %s erfolgreich aus Etage %d, Parkplatz %d ausgefahren.",
                        fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild(), etage + 1, etagenParkplaetze.size() + 1);
            }
        }
        return String.format("%s mit Nummernschild %s wurde nicht im Parkhaus gefunden.", fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild());
    }

    public String positionAbfragen(Fahrzeug fahrzeug) {
        for (int etage = 0; etage < anzahlEtagen; etage++) {
            List<Fahrzeug> etagenParkplaetze = parkplaetze.get(etage);

            if (etagenParkplaetze.contains(fahrzeug)) {
                int parkplatz = etagenParkplaetze.indexOf(fahrzeug);
                return String.format("%s mit Nummernschild %s befindet sich auf Etage %d, Parkplatz %d.",
                        fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild(), etage + 1, parkplatz + 1);
            }
        }
        return String.format("%s mit Nummernschild %s wurde nicht im Parkhaus gefunden.", fahrzeug.getClass().getSimpleName(), fahrzeug.getNummernschild());
    }

    public String freieParkplaetzeAbfragen() {
        return String.format("Es sind noch %d von %d Parkplätzen frei.", freieParkplaetze, anzahlEtagen * parkplaetzeProEtage);
    }
}

public class Parkinggarage {
    public static void main(String[] args) {
        int anzahlEtagen = 3;
        int parkplaetzeProEtage = 5;

        Parkhaus parkhaus = new Parkhaus(anzahlEtagen, parkplaetzeProEtage);

        PKW auto1 = new PKW();
        PKW auto2 = new PKW();
        Motorrad motorrad1 = new Motorrad();

        System.out.println(parkhaus.einfahren(auto1));
        System.out.println(parkhaus.einfahren(auto2));
        System.out.println(parkhaus.einfahren(motorrad1));

        System.out.println(parkhaus.positionAbfragen(auto1));
        System.out.println(parkhaus.freieParkplaetzeAbfragen());

        System.out.println(parkhaus.ausfahren(auto2));

        System.out.println(parkhaus.freieParkplaetzeAbfragen());
    }
}
