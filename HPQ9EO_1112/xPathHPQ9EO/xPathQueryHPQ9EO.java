package HPQ9EO_1112.xPathHPQ9EO;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQueryHPQ9EO {

    public static void main(String[] args) {
        try {
            // File xmlFile = new File("studentHPQ9EO.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse("HPQ9EO_1112/xPathHPQ9EO/studentHPQ9EO.xml");

            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            String neptunkod = "class/student";

            NodeList neptunKod = (NodeList) xPath.compile(neptunkod).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < neptunKod.getLength(); i++) {
                Node node = neptunKod.item(i);

                System.out.println("\nAktuális elem: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                    Element element = (Element) node;

                    System.out.println("Hallgató ID: " + element.getAttribute("id"));

                    System.out.println(
                            "Keresztnév: " + element.getElementsByTagName("firstName").item(0).getTextContent());

                    System.out.println(
                            "Vezetéknév: " + element.getElementsByTagName("lastName").item(0).getTextContent());

                    System.out.println("Becenév: " + element.getElementsByTagName("nickname").item(0).getTextContent());

                    System.out.println("Kor: " + element.getElementsByTagName("age").item(0).getTextContent());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

}
