package hpq9eojson;

import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWriteHPQ9EO {
    public static void main(String[] args) {
        try{
            FileReader reader = new FileReader("C:/webadat/HPQ9EOWebXML/HPQ9EO_1119/orarendHPQ9EO.json");
            //Parse
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Root, majd óra lista lekérése
            JSONObject root = (JSONObject) jsonObject.get("HPQ9EO_orarend");
            JSONArray lessons = (JSONArray) root.get("ora");
            //Óra adatok kiírása
            FileWriter writer = new FileWriter(new File("C:/webadat/HPQ9EOWebXML/HPQ9EO_1119/orarendHPQ9EO1.json"));
            writer.write("{\n  \"HPQ9EO_orarend\": {\n    \"ora\": [\n");
            for(int i =0; i<lessons.size(); i++) {
                JSONObject lesson = (JSONObject) lessons.get(i);
                JSONObject time = (JSONObject) lesson.get("idopont");
                writer.write("      {\n");
                writer.write("        \"targy\": \"" + lesson.get("targy") + "\",\n");
                writer.write("        \"idopont\": {\n");
                writer.write("          \"nap\": \"" + time.get("nap") + "\",\n");
                writer.write("          \"tol\": \"" + time.get("tol") + "\",\n");
                writer.write("          \"ig\": \"" + time.get("ig") + "\"\n");
                writer.write("        },\n");
                writer.write("        \"helyszin\": \"" + lesson.get("helyszin") + "\",\n");
                writer.write("        \"oktato\": \"" + lesson.get("oktato") + "\",\n");
                writer.write("        \"szak\": \"" + lesson.get("szak") + "\"\n");
                if(i < lessons.size() - 1) {
                    writer.write("      },\n");
                } else {
                    writer.write("      }\n");
                }
            }
        }
    }
}
