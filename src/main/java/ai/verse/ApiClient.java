package ai.verse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

public class ApiClient {

    ObjectMapper mapper = new ObjectMapper();
    public static void main(String args[]) {

        ApiClient api = new ApiClient();

        try {
             api.callFacebookAPI();
          //  api.callWeatherAPI();
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


     //   String fbData = "{\"data\":[{\"comments\":{\"data\":[{\"created_time\":\"2024-05-14T07:20:32+0000\",\"from\":{\"name\":\"AI Page\",\"id\":\"331878276672800\"},\"message\":\"This is test post superr\",\"id\":\"122098716890322306_25803638655901140\"},{\"created_time\":\"2024-05-14T07:20:24+0000\",\"from\":{\"name\":\"AI Page\",\"id\":\"331878276672800\"},\"message\":\"This is  nice\",\"id\":\"122098716890322306_391857713847303\"},{\"created_time\":\"2024-05-16T05:54:48+0000\",\"from\":{\"name\":\"AI Verse\",\"id\":\"7959247167458080\"},\"message\":\"16th May another good post\",\"id\":\"122098716890322306_1955450111520011\"},{\"created_time\":\"2024-05-16T05:46:34+0000\",\"from\":{\"name\":\"AI Verse\",\"id\":\"7959247167458080\"},\"message\":\"Good company\",\"id\":\"122098716890322306_351382277555829\"},{\"created_time\":\"2024-05-15T05:26:35+0000\",\"from\":{\"name\":\"AI Verse\",\"id\":\"7959247167458080\"},\"message\":\"\\ud83d\\udc4d\",\"id\":\"122098716890322306_963196278686318\"},{\"created_time\":\"2024-05-15T06:14:32+0000\",\"from\":{\"name\":\"AI Verse\",\"id\":\"7959247167458080\"},\"message\":\"Reply to post\",\"id\":\"122098716890322306_3278527582442955\"}],\"paging\":{\"cursors\":{\"before\":\"NgZDZD\",\"after\":\"MQZDZD\"}}},\"id\":\"331878276672800_122098716890322306\"}],\"paging\":{\"cursors\":{\"before\":\"QVFIUnI4cGlnUVBiSGFSWVhyb1hJdG9NYkNCTDBULUd1QzVKZAnRYOUZAGd3FTVzFqOFMwNXRneUV0RHVLQlFmaWd3M2M2V1E1cGJxUldFa2VJQkluRVJPazRkRDYzQ2NfSDk4WW4xTDZAXY2RsYS05elI1dVA5ZAW1zV0J2dE9mVlR4SlFkdFJ2NmlueTdJTmx6aVRtN0kyejlnZAVVCdlVLZA1kyb29TZA09ybWVRaVNxZA3dTd0VBQjRMVFRlNHl4QmpCRi1pakk0Rm9SbERBMzMtZAnl6ci1ia0FRWk40TVRNWU1ONEhvRmZAkYjVIMk9XeVZAPM0tScm1BNkRhRUtGUkhSQ0pIQmg4Nk1wUElwVHl5X2F4dTFpejc0dGZApWDhTMjkxcGRTblVEMzc0XzUwUk9USnFPWEpScl9LSGRIWE56MXZAwMDY5bFJDaWwtOWVHZAEJDaWh4WGxKSlNfeDIza1pKaUU2NGtFMkhjWGRUa2kwdXF2dW9KUkZAIOE1TNmhVVElZAbGcyaFRSdXZAEMnc5ekhDNm95VVBudWtwN1lrb0dwajBONW5MQ0lHZATZAxeXY2WjQZD\",\"after\":\"QVFIUjlwdTNtRGZAkNmFyQnphWG8yY21VU0RDbmlGUTNieWNVT2dDQTRseTRiNDRDLTVTUjQtdW1yS3FpZATJXLXhIa3p2cGpMUG1EemxGaks5c1V4bnFKVmRXN0NCUlZA4U2pvTG1TNWMyQWRibWhPdmlHQkhlZAFdZASWRrdTU2V1VnazFJZAThiMzBlMDVPaDdGclBqR19TeWdwV0hHWncwZA0RSU2w1VWJxbF96SUhsN3hacXZAMUjZA5N29tcDhWcFFIQ1hqckFQT3JEdFVhNHBUZAGlhbmdaSG41MzFBeVFjaWJxaDlickhZAdFRIaFpXek10R1VxUWVqLURmUXdnd0taZAEUyaGdja1FPdUpDa0ZA4NlY5WEJMN3FLRDNBOTlNQ3hVcnJJMjhkZADFRZAFlvR1d5N0tYZA25VZA2k0VDFwVGV0U0s3NTZAWWjBtbmJWMHdoZAUxXTGZAiRkpuME0xVHF2ZAmsza0EtMWdfNWVzb2lPaDRfTVNzcUV0YjA3R01LTERZASzcyYWNkMlRQUWFtYndxSWk2R3puS0liMjJ2bkxKY1BYQjdBajZAQWnMzdm9MNll6TXcZD\"}}}";


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


            //



            /**

             1. ArrayNode -  get("data")

             2. get zero element

             3. get Comments

             4. get "data"  has Array

             5.   Each data has message



             */
















//            JsonNode fbDataJsonNode = mapper.readTree(fbData);
//            ArrayNode fbDataNode = (ArrayNode) fbDataJsonNode.get("data");
//            ArrayNode fbInnerDataNode = (ArrayNode) fbDataNode.get(0).get("comments").get("data");//
//            for (int g = 0; g < fbInnerDataNode.size(); g++) {
//                System.out.println(fbInnerDataNode.get(g).get("message").asText());
//            }
        }catch(Exception any)
        {
            any.printStackTrace();
        }
    }


}
