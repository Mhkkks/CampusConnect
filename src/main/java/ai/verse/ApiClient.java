package ai.verse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.web.client.RestTemplate;

public class ApiClient {

    ObjectMapper mapper = new ObjectMapper();

    public static void main(String args[]) {
        ApiClient api = new ApiClient();

        try {
            //   api.callFacebookAPI();
            //  api.callWeatherAPI();
            //   api.getPM25Data();
        } catch (Exception any) {
            any.printStackTrace();
        }
    }


    public void callWeatherAPI() throws Exception {

        // 3 types
        //1. Text example name="abc"
        // 2. jsonNode which means an object
        //3 ArrayNode which is an array

        String url = "http://api.weatherapi.com/v1/current.json?key=405f63f293a140f69cf65216243105&q=Mumbai&aqi=yes";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);

        //  Step 1.    Convert String to Java JsonNode
        JsonNode actualObj = mapper.readTree(result);

        // Step 2 . Get value from jsonNode
        // I have reeceived json string which has weather and geo details. I need latitude
        JsonNode obj2 = actualObj.get("location");
        String latitude = obj2.get("lat").asText();
        System.out.println("latitude is:" + latitude);

        ////////////////////////////////////////////////////////////////////
        //   Get current -->> air_quality -->> pm10 Value
        JsonNode currentobj = actualObj.get("current");
        JsonNode airQuality = currentobj.get("air_quality");
        String pm10 = airQuality.get("pm10").asText();
        System.out.println("pm10 is:" + pm10);


        /// Example of parsing of  array///////////////
        String jsonArrayExample = "{\"name\":\"John\",\"age\":30,\"cars\":[\"Ford\",\"BMW\",\"Fiat\"]}";
        JsonNode arrayExample = mapper.readTree(jsonArrayExample);
        String name = arrayExample.get("name").asText();

        // This is the way you get each element of an array
        ArrayNode carsNode = (ArrayNode) arrayExample.get("cars"); // special for array
        for (int g = 0; g < carsNode.size(); g++) {
            System.out.println(carsNode.get(g));
        }

    }


    // Using an api : api client
    // Here facebook has created an api and we are consuming it , so we are api client
    private void callFacebookAPI() {

        try {
            String accessToken = "EAA4uBIZCLHrEBO5GR1J320nZAi35erJndJdBz6TXx3joyjGipaszk1ZCBNZCIrAGTZBwJ8zmUZCoFdVeBEOlPD0br4AnZAz9TT8wpc3i21Y3ZBhNlORcVMVwdeKTRWUrRqRjZAaZBifkCtCZB7xOsMXmSIkC9ayQ3X3lWrZByDSq3H5B2J5ObVNTOzRAKKHVWjw7FwkEUrxCGPIP";
            String uri = "https://graph.facebook.com/v19.0/me/posts?fields=comments&access_token=" + accessToken;

            RestTemplate restTemplate = new RestTemplate();  // this class is used to call external API  in Java
            String fbData = restTemplate.getForObject(uri, String.class);  // This result in json format
            System.out.println(fbData);

            JsonNode fbDataJsonNode = mapper.readTree(fbData);
            ArrayNode fbDataNode = (ArrayNode) fbDataJsonNode.get("data");

            ArrayNode fbInnerDataNode = (ArrayNode) fbDataNode.get(0).get("comments").get("data");


            for (int g = 0; g < fbInnerDataNode.size(); g++) {
                System.out.println(fbInnerDataNode.get(g).get("message").asText());
            }

            /**
             1. ArrayNode -  get("data")
             2. get zero element
             3. get Comments
             4. get "data"  has Array
             5.   Each data has message
             */
        } catch (Exception any) {
            any.printStackTrace();
        }
    }


}
