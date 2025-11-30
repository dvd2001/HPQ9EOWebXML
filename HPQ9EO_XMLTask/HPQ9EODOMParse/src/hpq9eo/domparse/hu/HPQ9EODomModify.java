package hpq9eo.domparse.hu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class HPQ9EODomModify {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            File xmlFile = new File("C:\\webadat\\HPQ9EOWebXML\\HPQ9EO_XMLTask\\1. feladat\\HPQ9EO_XML.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("=== DOM MODIFICATION RESULTS ===\n");

            // 1. Módosítás: Audi A3 típus módosítása A4-re (séma kompatibilis)
            System.out.println("1. Audi A3 típus módosítása A4-re:");
            modifyAudiType(doc);

            // 2. Módosítás: Telefonszám módosítása (séma kompatibilis)
            System.out.println("\n2. Telefonszám módosítása:");
            modifyPhoneNumber(doc);

            // 3. Módosítás: Új végzettség hozzáadása dolgozóhoz (séma kompatibilis)
            System.out.println("\n3. Dolgozó végzettség hozzáadása:");
            addVegzettsegToDolgozo(doc);

            // 4. Módosítás: Új márka hozzáadása szalonhoz (séma kompatibilis)
            System.out.println("\n4. Új márka hozzáadása szalonhoz:");
            addNewMarkaToSzalon(doc);

            // 5. Módosítás: Műszak időpontjának módosítása (séma kompatibilis)
            System.out.println("\n5. Műszak időpontjának módosítása:");
            modifyMuszakIdopont(doc);

            // 6. Módosítás: Új kapcsolat hozzáadása (séma kompatibilis, kulcsok
            // betartásával)
            System.out.println("\n6. Új kapcsolat hozzáadása:");
            addNewKapcsolat(doc);

            // Módosított dokumentum mentése
            saveModifiedDocument(doc, "HPQ9EO_modified.xml");
            System.out.println("\nMódosított dokumentum mentve: HPQ9EO_modified.xml");

            // Séma validálás ellenőrzése
            System.out.println("\n=== SÉMA VALIDÁLÁS ELLENŐRZÉSE ===");
            if (validateAgainstSchema(doc)) {
                System.out.println("A módosított dokumentum megfelel a sémának!");
            } else {
                System.out.println("FIGYELMEZTETÉS: A módosított dokumentum nem felel meg a sémának!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Módosítás: Audi A3 típus módosítása A4-re (séma kompatibilis)
    private static void modifyAudiType(Document doc) {
        NodeList autoList = doc.getElementsByTagName("Auto");
        for (int i = 0; i < autoList.getLength(); i++) {
            Element auto = (Element) autoList.item(i);
            String marka = auto.getElementsByTagName("Marka").item(0).getTextContent();
            String tipus = auto.getElementsByTagName("Tipus").item(0).getTextContent();

            if ("Audi".equals(marka) && "A3".equals(tipus)) {
                String rendszam = auto.getAttribute("rendszam");
                System.out.println("  - Módosítás előtt: " + marka + " " + tipus + " (" + rendszam + ")");
                auto.getElementsByTagName("Tipus").item(0).setTextContent("A4");
                System.out.println("  - Módosítás után: " + marka + " A4 (" + rendszam + ")");
                break;
            }
        }
    }

    // 2. Módosítás: Telefonszám módosítása (séma kompatibilis)
    private static void modifyPhoneNumber(Document doc) {
        NodeList tulajdonosList = doc.getElementsByTagName("Tulajdonos");
        for (int i = 0; i < tulajdonosList.getLength(); i++) {
            Element tulajdonos = (Element) tulajdonosList.item(i);
            String nev = tulajdonos.getElementsByTagName("Nev").item(0).getTextContent();

            if ("Kovács János".equals(nev)) {
                String regiTelefon = tulajdonos.getElementsByTagName("Telefonszam").item(0).getTextContent();
                System.out.println("  - " + nev + " régi telefonszáma: " + regiTelefon);
                tulajdonos.getElementsByTagName("Telefonszam").item(0).setTextContent("+36301112233");
                String ujTelefon = tulajdonos.getElementsByTagName("Telefonszam").item(0).getTextContent();
                System.out.println("  - " + nev + " új telefonszáma: " + ujTelefon);
                break;
            }
        }
    }

    // 3. Módosítás: Dolgozó végzettség hozzáadása (séma kompatibilis -
    // maxOccurs="unbounded")
    private static void addVegzettsegToDolgozo(Document doc) {
        NodeList dolgozoList = doc.getElementsByTagName("Dolgozo");
        for (int i = 0; i < dolgozoList.getLength(); i++) {
            Element dolgozo = (Element) dolgozoList.item(i);
            String nev = dolgozo.getElementsByTagName("Nev").item(0).getTextContent();

            if ("Tancos Geza".equals(nev)) {
                Element ujVegzettseg = doc.createElement("Vegzettseg");
                ujVegzettseg.appendChild(doc.createTextNode("Műszaki ellenőr"));
                dolgozo.appendChild(ujVegzettseg);

                System.out.println("  - " + nev + " új végzettsége: Műszaki ellenőr");

                // Kiírjuk az összes végzettséget
                NodeList vegzettsegek = dolgozo.getElementsByTagName("Vegzettseg");
                System.out.print("    Összes végzettsége: ");
                for (int j = 0; j < vegzettsegek.getLength(); j++) {
                    System.out.print(vegzettsegek.item(j).getTextContent());
                    if (j < vegzettsegek.getLength() - 1)
                        System.out.print(", ");
                }
                System.out.println();
                break;
            }
        }
    }

    // 4. Módosítás: Új márka hozzáadása szalonhoz (séma kompatibilis -
    // maxOccurs="unbounded")
    private static void addNewMarkaToSzalon(Document doc) {
        NodeList szalonList = doc.getElementsByTagName("Szalon");
        for (int i = 0; i < szalonList.getLength(); i++) {
            Element szalon = (Element) szalonList.item(i);
            String id = szalon.getAttribute("id");

            if ("02".equals(id)) { // Miskolci szalon
                Element ujMarka = doc.createElement("Markaszerviz");
                ujMarka.appendChild(doc.createTextNode("BMW"));
                szalon.appendChild(ujMarka);

                System.out.println("  - Szalon ID " + id + " új márkája: BMW");

                // Kiírjuk az összes márkát
                NodeList markak = szalon.getElementsByTagName("Markaszerviz");
                System.out.print("    Összes márkája: ");
                for (int j = 0; j < markak.getLength(); j++) {
                    System.out.print(markak.item(j).getTextContent());
                    if (j < markak.getLength() - 1)
                        System.out.print(", ");
                }
                System.out.println();
                break;
            }
        }
    }

    // 5. Módosítás: Műszak időpontjának módosítása (séma kompatibilis)
    private static void modifyMuszakIdopont(Document doc) {
        NodeList muszakList = doc.getElementsByTagName("Muszak");
        for (int i = 0; i < muszakList.getLength(); i++) {
            Element muszak = (Element) muszakList.item(i);
            String mSz = muszak.getAttribute("M_Sz");

            if ("01".equals(mSz)) {
                String regiKezd = muszak.getElementsByTagName("Kezd").item(0).getTextContent();
                String regiVegez = muszak.getElementsByTagName("Vegez").item(0).getTextContent();

                System.out.println("  - Műszak " + mSz + " régi időpontja: " + regiKezd + " - " + regiVegez);

                muszak.getElementsByTagName("Kezd").item(0).setTextContent("9:00");
                muszak.getElementsByTagName("Vegez").item(0).setTextContent("17:00");

                String ujKezd = muszak.getElementsByTagName("Kezd").item(0).getTextContent();
                String ujVegez = muszak.getElementsByTagName("Vegez").item(0).getTextContent();
                System.out.println("  - Műszak " + mSz + " új időpontja: " + ujKezd + " - " + ujVegez);
                break;
            }
        }
    }

    // 6. Módosítás: Új kapcsolat hozzáadása (séma kompatibilis, létező kulcsokkal)
    private static void addNewKapcsolat(Document doc) {
        Element root = doc.getDocumentElement();

        // Új kapcsolat elem létrehozása - csak létező kulcsokkal
        Element ujKapcsolat = doc.createElement("U_Sz");
        ujKapcsolat.setAttribute("K_Sz", "01"); // Létező szalon ID
        ujKapcsolat.setAttribute("K_U", "253641KA"); // Létező ügyfél személyi szám

        // Hozzáadás a megfelelő helyre (U_Sz elemek közé)
        NodeList uSzList = doc.getElementsByTagName("U_Sz");
        if (uSzList.getLength() > 0) {
            // Az utolsó U_Sz elem után szúrjuk be
            Node lastUSz = uSzList.item(uSzList.getLength() - 1);
            root.insertBefore(ujKapcsolat, lastUSz.getNextSibling());
        } else {
            // Ha nincs U_Sz elem, akkor az Autók után, Ügyfelek előtt
            NodeList autoList = doc.getElementsByTagName("Auto");
            if (autoList.getLength() > 0) {
                Node lastAuto = autoList.item(autoList.getLength() - 1);
                root.insertBefore(ujKapcsolat, lastAuto.getNextSibling());
            }
        }

        System.out.println("  - Új kapcsolat hozzáadva: Szalon ID 01 - Ügyfél 253641KA");
    }

    // Séma validálás ellenőrzése (egyszerű változat)
    private static boolean validateAgainstSchema(Document doc) {
        try {
            // A séma validálás bekapcsolása
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            dbFactory.setValidating(true);
            dbFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Error handler a validációs hibák kezelésére
            dBuilder.setErrorHandler(new org.xml.sax.ErrorHandler() {
                public void warning(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
                    System.out.println("Validálási figyelmeztetés: " + e.getMessage());
                }

                public void error(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
                    System.out.println("Validálási hiba: " + e.getMessage());
                    throw e;
                }

                public void fatalError(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
                    System.out.println("Validálási kritikus hiba: " + e.getMessage());
                    throw e;
                }
            });

            // Újra betöltjük és validáljuk a módosított dokumentumot
            File modifiedFile = new File("HPQ9EO_modified.xml");
            dBuilder.parse(modifiedFile);
            return true;

        } catch (Exception e) {
            System.out.println("Validálási hiba: " + e.getMessage());
            return false;
        }
    }

    // Módosított dokumentum mentése
    private static void saveModifiedDocument(Document doc, String filename) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Formázás beállítása
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
}