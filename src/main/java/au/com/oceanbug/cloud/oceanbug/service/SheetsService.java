package au.com.oceanbug.cloud.oceanbug.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

@Service
public class SheetsService {

    public String readSheet(){
        RestTemplate restTemplate = new RestTemplate();
        String url  = "";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        String result = null;
        try {
            root = mapper.readTree(response.getBody());
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        walkTree(root);

        return result;
    }

    public void walkTree(JsonNode root)
    {
        List<String> headers = new ArrayList<>();
        JsonNode rootNode = readObject(root);

        ArrayList<JsonNode> rootArray = readArray(rootNode);

        System.out.println("rootArray = " + rootArray.size());

        ArrayList<JsonNode> rowArray = null;

        boolean firstRow = true;
        for (JsonNode jsonNode : rootArray) {
            rowArray = readArray(jsonNode);

            for (int i = 0; i < rowArray.size(); i++) {
                JsonNode rowData = rowArray.get(i);
                if(i == 0)
                    readValues(rowData, headers);
//                else
//                    readValues(rowData, headers);
            }

        }

        for (String header : headers) {
            System.out.println("header = " + header);
        }
    }

    private JsonNode readObject(JsonNode node)
    {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next =  iterator.next();

                String name = next.getKey();
                JsonNode newNode = next.getValue();

                if("values".equalsIgnoreCase(name))
                    return newNode;
            }
        }

        return null;
    }

    private ArrayList<JsonNode> readArray(JsonNode node) {
        if (node.isArray()) {
            Iterator<JsonNode> arrayItemsIterator = node.elements();
            return Lists.newArrayList(arrayItemsIterator);
        }

        return null;
    }

    private void readValues(JsonNode node, List<String> values) {
        ArrayList<JsonNode> nodeValues = readArray((node));
        for (JsonNode nodeValue : nodeValues) {
            values.add(readValue(nodeValue));
        }
    }

    private String readValue(JsonNode node) {
        if (node.isValueNode())
           return node.asText();

        return null;
    }
}
