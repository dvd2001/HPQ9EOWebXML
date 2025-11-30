package hpq9eo.domparse.hu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;

public class HPQ9EODomRead {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            File xmlFile = new File("C:\\webadat\\HPQ9EOWebXML\\HPQ9EO_XMLTask\\1. feladat\\HPQ9EO_XML.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Fájl írása
            FileWriter writer = new FileWriter("output_read.txt");

            // Gyökér elem kiírása
            String rootElement = doc.getDocumentElement().getNodeName();
            System.out.println("Root element: " + rootElement);
            writer.write("Root element: " + rootElement + "\n");
            System.out.println("=================================");
            writer.write("=================================\n");

            // Tulajdonosok feldolgozása
            processElements(doc, "Tulajdonos", "Tulajdonosok", writer);

            // Szalonok feldolgozása
            processElements(doc, "Szalon", "Szalonok", writer);

            // Autók feldolgozása
            processElements(doc, "Auto", "Autók", writer);

            // Ugyfelek feldolgozása
            processElements(doc, "Ugyfel", "Ugyfelek", writer);

            // Dolgozók feldolgozása
            processElements(doc, "Dolgozo", "Dolgozók", writer);

            // Műszakok feldolgozása
            processElements(doc, "Muszak", "Műszakok", writer);

            // Kapcsolatok feldolgozása
            processElements(doc, "U_Sz", "Ugyfel-Szalon kapcsolatok", writer);

            writer.close();
            System.out.println("Adatok kiírva az output_read.txt fájlba.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processElements(Document doc, String tagName, String sectionName, FileWriter writer)
            throws Exception {
        System.out.println("\n=== " + sectionName + " ===");
        writer.write("\n=== " + sectionName + " ===\n");

        NodeList nodeList = doc.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("\n" + tagName + " " + (i + 1) + ":");
                writer.write("\n" + tagName + " " + (i + 1) + ":\n");

                // Attribútumok kiírása
                if (element.hasAttributes()) {
                    for (int j = 0; j < element.getAttributes().getLength(); j++) {
                        Node attr = element.getAttributes().item(j);
                        System.out.println("  " + attr.getNodeName() + ": " + attr.getNodeValue());
                        writer.write("  " + attr.getNodeName() + ": " + attr.getNodeValue() + "\n");
                    }
                }

                // Gyermek elemek kiírása
                NodeList children = element.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String childName = child.getNodeName();
                        String childValue = child.getTextContent();
                        System.out.println("  " + childName + ": " + childValue);
                        writer.write("  " + childName + ": " + childValue + "\n");
                    }
                }
            }
        }
        System.out.println("---------------------------------");
        writer.write("---------------------------------\n");
    }
}