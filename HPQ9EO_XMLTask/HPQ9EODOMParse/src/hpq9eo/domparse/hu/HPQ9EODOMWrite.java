package hpq9eo.domparse.hu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class HPQ9EODOMWrite {
    public static void main(String[] args) {
        try {
            // Új dokumentum létrehozása
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Gyökér elem létrehozása
            Element rootElement = doc.createElement("Szerviz");
            doc.appendChild(rootElement);

            // Tulajdonosok hozzáadása
            addTulajdonos(doc, rootElement, "123456NA", "01", "Kovács János",
                    "Budapest", "Kossuth Lajos utca", "10", "+363025815235");

            addTulajdonos(doc, rootElement, "987654TA", "02", "Remek Elek",
                    "Miskolc", "Kiraly utca", "5", "+36706584123");

            // Szalonok hozzáadása
            addSzalon(doc, rootElement, "01", "Budapest", "Fo utca", "1",
                    "www.budapestauto.hu", new String[] { "Audi" });

            addSzalon(doc, rootElement, "02", "Miskolc", "Derine utca", "7",
                    "www.miskolcauto.hu", new String[] { "Mercedes", "Ford" });

            // Autók hozzáadása
            addAuto(doc, rootElement, "ABC-123", "01", "Audi", "A3", "Dízel");
            addAuto(doc, rootElement, "CBA-321", "02", "Ford", "Fiesta", "Benzin");

            // Dokumentum kiírása konzolra fa struktúrában
            printDocumentTree(doc.getDocumentElement(), 0);

            // Dokumentum mentése fájlba
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("HPQ9EO1XML.xml"));
            transformer.transform(source, result);

            System.out.println("\nXML fájl mentve: HPQ9EO1XML.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTulajdonos(Document doc, Element root, String szemelyiSzam, String t_sz,
            String nev, String varos, String utca, String hazszam, String telefonszam) {
        Element tulajdonos = doc.createElement("Tulajdonos");
        tulajdonos.setAttribute("szemelyiSzam", szemelyiSzam);
        tulajdonos.setAttribute("t_sz", t_sz);

        Element nevElem = doc.createElement("Nev");
        nevElem.appendChild(doc.createTextNode(nev));
        tulajdonos.appendChild(nevElem);

        Element cim = doc.createElement("Cim");
        addCimElements(doc, cim, varos, utca, hazszam);
        tulajdonos.appendChild(cim);

        Element tel = doc.createElement("Telefonszam");
        tel.appendChild(doc.createTextNode(telefonszam));
        tulajdonos.appendChild(tel);

        root.appendChild(tulajdonos);
    }

    private static void addSzalon(Document doc, Element root, String id, String varos,
            String utca, String hazszam, String weboldal, String[] markak) {
        Element szalon = doc.createElement("Szalon");
        szalon.setAttribute("id", id);

        Element cim = doc.createElement("Cim");
        addCimElements(doc, cim, varos, utca, hazszam);
        szalon.appendChild(cim);

        Element web = doc.createElement("Weboldal");
        web.appendChild(doc.createTextNode(weboldal));
        szalon.appendChild(web);

        for (String marka : markak) {
            Element markaElem = doc.createElement("Markaszerviz");
            markaElem.appendChild(doc.createTextNode(marka));
            szalon.appendChild(markaElem);
        }

        root.appendChild(szalon);
    }

    private static void addAuto(Document doc, Element root, String rendszam, String a_sz,
            String marka, String tipus, String uzemanyag) {
        Element auto = doc.createElement("Auto");
        auto.setAttribute("rendszam", rendszam);
        auto.setAttribute("a_sz", a_sz);

        Element markaElem = doc.createElement("Marka");
        markaElem.appendChild(doc.createTextNode(marka));
        auto.appendChild(markaElem);

        Element tipusElem = doc.createElement("Tipus");
        tipusElem.appendChild(doc.createTextNode(tipus));
        auto.appendChild(tipusElem);

        Element uzemanyagElem = doc.createElement("Uzemanyag");
        uzemanyagElem.appendChild(doc.createTextNode(uzemanyag));
        auto.appendChild(uzemanyagElem);

        root.appendChild(auto);
    }

    private static void addCimElements(Document doc, Element cim, String varos, String utca, String hazszam) {
        Element varosElem = doc.createElement("Varos");
        varosElem.appendChild(doc.createTextNode(varos));
        cim.appendChild(varosElem);

        Element utcaElem = doc.createElement("Utca");
        utcaElem.appendChild(doc.createTextNode(utca));
        cim.appendChild(utcaElem);

        Element hazszamElem = doc.createElement("Hazszam");
        hazszamElem.appendChild(doc.createTextNode(hazszam));
        cim.appendChild(hazszamElem);
    }

    private static void printDocumentTree(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // Behúzás a mélység alapján
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("  ");
            }

            System.out.println(indent + "<" + node.getNodeName() + getAttributes(node) + ">");

            // Szöveges tartalom kiírása, ha van
            if (node.getTextContent() != null && !node.getTextContent().trim().isEmpty() &&
                    node.getChildNodes().getLength() == 1) {
                System.out.println(indent + "  " + node.getTextContent().trim());
            }

            // Gyermekek feldolgozása
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                printDocumentTree(children.item(i), depth + 1);
            }

            System.out.println(indent + "</" + node.getNodeName() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE &&
                node.getTextContent() != null && !node.getTextContent().trim().isEmpty()) {
            // Szöveges tartalom kiírása
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("  ");
            }
            System.out.println(indent + node.getTextContent().trim());
        }
    }

    private static String getAttributes(Node node) {
        StringBuilder attributes = new StringBuilder();
        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attr = node.getAttributes().item(i);
                attributes.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue())
                        .append("\"");
            }
        }
        return attributes.toString();
    }
}