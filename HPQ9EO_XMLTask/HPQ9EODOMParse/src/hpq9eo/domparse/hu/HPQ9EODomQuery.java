package hpq9eo.domparse.hu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;

public class HPQ9EODomQuery {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            File xmlFile = new File("C:\\webadat\\HPQ9EOWebXML\\HPQ9EO_XMLTask\\1. feladat\\HPQ9EO_XML.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("=== DOM QUERY RESULTS ===\n");

            // 1. Lekérdezés: Minden Audi autó
            System.out.println("1. Összes Audi autó:");
            queryAudiAutos(doc);

            // 2. Lekérdezés: Budapesti szalonok
            System.out.println("\n2. Budapesti szalonok:");
            queryBudapestSzalonok(doc);

            // 3. Lekérdezés: Autószerelő végzettségű dolgozók
            System.out.println("\n3. Autószerelő végzettségű dolgozók:");
            queryAutoszereloDolgozok(doc);

            // 4. Lekérdezés: Dízel üzemanyagú autók
            System.out.println("\n4. Dízel üzemanyagú autók:");
            queryDieselAutos(doc);

            // 5. Lekérdezés: Miskolci című tulajdonosok
            System.out.println("\n5. Miskolci című tulajdonosok:");
            queryMiskolciTulajdonosok(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Lekérdezés: Audi autók
    private static void queryAudiAutos(Document doc) {
        NodeList autoList = doc.getElementsByTagName("Auto");
        for (int i = 0; i < autoList.getLength(); i++) {
            Element auto = (Element) autoList.item(i);
            String marka = auto.getElementsByTagName("Marka").item(0).getTextContent();
            if ("Audi".equals(marka)) {
                String rendszam = auto.getAttribute("rendszam");
                String tipus = auto.getElementsByTagName("Tipus").item(0).getTextContent();
                System.out.println("  - " + marka + " " + tipus + " (" + rendszam + ")");
            }
        }
    }

    // 2. Lekérdezés: Budapesti szalonok
    private static void queryBudapestSzalonok(Document doc) {
        NodeList szalonList = doc.getElementsByTagName("Szalon");
        for (int i = 0; i < szalonList.getLength(); i++) {
            Element szalon = (Element) szalonList.item(i);
            Element cim = (Element) szalon.getElementsByTagName("Cim").item(0);
            String varos = cim.getElementsByTagName("Varos").item(0).getTextContent();

            if ("Budapest".equals(varos)) {
                String id = szalon.getAttribute("id");
                String weboldal = szalon.getElementsByTagName("Weboldal").item(0).getTextContent();
                System.out.println("  - Szalon ID: " + id + ", Web: " + weboldal);

                // Márkaszervizek listázása
                NodeList markak = szalon.getElementsByTagName("Markaszerviz");
                System.out.print("    Márkák: ");
                for (int j = 0; j < markak.getLength(); j++) {
                    System.out.print(markak.item(j).getTextContent());
                    if (j < markak.getLength() - 1)
                        System.out.print(", ");
                }
                System.out.println();
            }
        }
    }

    // 3. Lekérdezés: Autószerelő végzettségű dolgozók
    private static void queryAutoszereloDolgozok(Document doc) {
        NodeList dolgozoList = doc.getElementsByTagName("Dolgozo");
        for (int i = 0; i < dolgozoList.getLength(); i++) {
            Element dolgozo = (Element) dolgozoList.item(i);
            NodeList vegzettsegek = dolgozo.getElementsByTagName("Vegzettseg");
            boolean autoszerelo = false;

            for (int j = 0; j < vegzettsegek.getLength(); j++) {
                if ("Autoszerelo".equals(vegzettsegek.item(j).getTextContent())) {
                    autoszerelo = true;
                    break;
                }
            }

            if (autoszerelo) {
                String nev = dolgozo.getElementsByTagName("Nev").item(0).getTextContent();
                String szemelyiSzam = dolgozo.getAttribute("szemelyiSzam");
                System.out.println("  - " + nev + " (" + szemelyiSzam + ")");
            }
        }
    }

    // 4. Lekérdezés: Dízel üzemanyagú autók
    private static void queryDieselAutos(Document doc) {
        NodeList autoList = doc.getElementsByTagName("Auto");
        for (int i = 0; i < autoList.getLength(); i++) {
            Element auto = (Element) autoList.item(i);
            String uzemanyag = auto.getElementsByTagName("Uzemanyag").item(0).getTextContent();
            if ("Dízel".equals(uzemanyag)) {
                String marka = auto.getElementsByTagName("Marka").item(0).getTextContent();
                String tipus = auto.getElementsByTagName("Tipus").item(0).getTextContent();
                String rendszam = auto.getAttribute("rendszam");
                System.out.println("  - " + marka + " " + tipus + " (" + rendszam + ") - " + uzemanyag);
            }
        }
    }

    // 5. Lekérdezés: Miskolci című tulajdonosok
    private static void queryMiskolciTulajdonosok(Document doc) {
        NodeList tulajdonosList = doc.getElementsByTagName("Tulajdonos");
        for (int i = 0; i < tulajdonosList.getLength(); i++) {
            Element tulajdonos = (Element) tulajdonosList.item(i);
            Element cim = (Element) tulajdonos.getElementsByTagName("Cim").item(0);
            String varos = cim.getElementsByTagName("Varos").item(0).getTextContent();

            if ("Miskolc".equals(varos)) {
                String nev = tulajdonos.getElementsByTagName("Nev").item(0).getTextContent();
                String telefonszam = tulajdonos.getElementsByTagName("Telefonszam").item(0).getTextContent();
                System.out.println("  - " + nev + ", Tel: " + telefonszam);
            }
        }
    }
}