import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class Hpq9eoDomRead {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("Hpq9eoOrarend.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ora");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    System.out.println("Class ID: " + element.getAttribute("id"));
                    System.out.println("Type: " + element.getAttribute("tipus"));
                    System.out.println("Subject: " + element.getElementsByTagName("targy").item(0).getTextContent());
                    System.out.println("Day: " + element.getElementsByTagName("nap").item(0).getTextContent());
                    System.out.println("Time: " + element.getElementsByTagName("tol").item(0).getTextContent() + " - " +
                            element.getElementsByTagName("ig").item(0).getTextContent());
                    System.out.println("Location: " + element.getElementsByTagName("helyszin").item(0).getTextContent());
                    System.out.println("Instructor: " + element.getElementsByTagName("oktato").item(0).getTextContent());
                    System.out.println("Specialization: " + element.getElementsByTagName("szak").item(0).getTextContent());
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}