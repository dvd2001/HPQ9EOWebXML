import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public static void main(String argv[]) {
    try {
        File inputFile = new File("Hpq9eo_orarend.xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.parse(inputFile);

        Node orarend = doc.getFirstChild();
        NodeList oraList = doc.getElementsByTagName("ora");

        Node ora = doc.getElementsByTagName("ora").item(0);

        Node oraado = doc.createElement("oraado");
        oraado.setTextContent("Prof. Kovács László");

        ora.appendChild(oraado);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        System.out.println("---Módosított fájl---");
        StreamResult consoResult = new StreamResult(System.out);
        StreamResult result = new StreamResult(new File("orarendModify1Hpq9eo.xml"));
        transformer.transform(source, consoResult);
        transformer.transform(source, result);

        for (int temp = 0; temp < oraList.getLength(); temp++) {
            Node node = oraList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                NodeList oraChildList = eElement.getChildNodes();
                for (int i = 0; i < oraChildList.getLength(); i++) {
                    if (oraChildList.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
                        Element oraChildElement = (Element) oraChildList.item(i);

                        if ("tipus".equals(oraChildElement.getNodeName())) {
                            if ("gyakorlat".equals(oraChildElement.getTextContent())) {
                                oraChildElement.setTextContent("elmelet");
                            }
                        }
                    }
                }
            }
        }

        DOMSource source2 = new DOMSource(doc);
        System.out.println("\n---Módosított fájl 2---");
        transformer.transform(source2, consoResult);
    } catch (Exception e) {
        e.printStackTrace();
    }
}