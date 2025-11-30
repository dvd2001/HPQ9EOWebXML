package hpq9eo.domparse.hu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;

public class HPQ9EODOMWrite {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            File xmlFile = new File("C:\\webadat\\HPQ9EOWebXML\\HPQ9EO_XMLTask\\1. feladat\\HPQ9EO_XML.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("=== XML DOKUMENTUM TARTALMA - FA STRUKTÚRA ===");
            System.out.println("=============================================");

            // Dokumentum tartalmának kiírása fa struktúrában a konzolra
            printDocumentTree(doc.getDocumentElement(), 0);

            // Dokumentum tartalmának mentése új XML fájlba
            saveDocumentToFile(doc, "HPQ9EO1XML.xml");
            System.out.println("\n=============================================");
            System.out.println("Dokumentum sikeresen elmentve: HPQ9EO1XML.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Rekurzív metódus a dokumentum fa struktúrájának kiírására
    private static void printDocumentTree(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // Behúzás a mélység alapján
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("  ");
            }

            // Elem nyitó tagje attribútumokkal
            Element element = (Element) node;
            System.out.println(indent + "<" + element.getNodeName() + getAttributes(element) + ">");

            // Szöveges tartalom ellenőrzése
            boolean hasElementChildren = hasElementChildren(element);
            String textContent = element.getTextContent().trim();

            // Ha van szöveges tartalom és nincs element gyermek, kiírjuk
            if (!textContent.isEmpty() && !hasElementChildren) {
                System.out.println(indent + "  " + textContent);
            }

            // Gyermek elemek feldolgozása
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                printDocumentTree(children.item(i), depth + 1);
            }

            // Elem záró tagje (csak ha van element gyermek vagy a szöveg nem üres)
            if (hasElementChildren || (!textContent.isEmpty() && hasElementChildren)) {
                System.out.println(indent + "</" + element.getNodeName() + ">");
            }

        } else if (node.getNodeType() == Node.TEXT_NODE) {
            // Szöveges tartalom kiírása, ha nem üres
            String text = node.getTextContent().trim();
            if (!text.isEmpty()) {
                StringBuilder indent = new StringBuilder();
                for (int i = 0; i < depth; i++) {
                    indent.append("  ");
                }
                System.out.println(indent + text);
            }
        }
    }

    // Ellenőrzi, hogy egy elemnek van-e element típusú gyermeke
    private static boolean hasElementChildren(Element element) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }
        return false;
    }

    // Az elem attribútumainak összegyűjtése string formátumba
    private static String getAttributes(Element element) {
        StringBuilder attributes = new StringBuilder();
        if (element.hasAttributes()) {
            for (int i = 0; i < element.getAttributes().getLength(); i++) {
                Node attr = element.getAttributes().item(i);
                attributes.append(" ").append(attr.getNodeName())
                        .append("=\"").append(attr.getNodeValue()).append("\"");
            }
        }
        return attributes.toString();
    }

    // Dokumentum mentése fájlba
    private static void saveDocumentToFile(Document doc, String filename) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Formázási beállítások
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println("Hiba a fájl mentése során: " + e.getMessage());
            e.printStackTrace();
        }
    }
}