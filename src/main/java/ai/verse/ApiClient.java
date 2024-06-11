package ai.verse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Base64;

public class ApiClient {

    ObjectMapper mapper = new ObjectMapper();
    public static void main(String args[]) {

        ApiClient api = new ApiClient();

        try {
          //   api.callFacebookAPI();
          //  api.callWeatherAPI();
            api.getPM25Data();
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


    public String encodeString(String decodedString)
    {
        String encodedString =
                Base64.getEncoder().withoutPadding().encodeToString(decodedString.getBytes());
        System.out.println(encodedString);
        return encodedString;
    }



    public String decodeString(String encodedString)
    {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
        return decodedString;
    }


    public void getPM25Data()
    {

     String a = "{\"title\":\"Pusa, Delhi - IMD\",\"nOfCom\":100,\"down\":\"false\",\"downmessage\":\"\",\"date\":\"Wednesday, 03 Apr 2019 12:00 AM\",\"temp\":\"\",\"aqi\":{\"param\":\"NO2\",\"value\":103,\"remark\":\"Moderate\",\"color\":\"#ffff00\"},\"metrics\":[{\"name\":\"PM2.5\",\"avg\":80,\"avgDesc\":\"Over the last 24 hours\",\"min\":37,\"max\":217,\"pollutantName\":\"PM2.5\"},{\"name\":\"PM10\",\"avg\":93,\"avgDesc\":\"Over the last 24 hours\",\"min\":58,\"max\":134,\"pollutantName\":\"PM10\"},{\"name\":\"NO2\",\"avg\":103,\"avgDesc\":\"Over the last 24 hours\",\"min\":17,\"max\":429,\"pollutantName\":\"NO2\"},{\"name\":\"CO\",\"avg\":59,\"avgDesc\":\"Over the last 8 hours\",\"min\":38,\"max\":86,\"pollutantName\":\"CO\"},{\"name\":\"OZONE\",\"avg\":36,\"avgDesc\":\"Over the last 8 hours\",\"min\":13,\"max\":69,\"pollutantName\":\"OZONE\"}],\"chartData\":[[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",59,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",60,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",83,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",129,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",217,\"color:#ff9900;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",112,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",97,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",41,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",64,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",78,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",60,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",79,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",37,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",82,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",97,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",103,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",90,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",83,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",93,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",114,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",134,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",113,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",110,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",58,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",70,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",84,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",62,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",76,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",88,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",95,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",119,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",44,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",105,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",79,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",62,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",53,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",49,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",193,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",104,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",19,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",17,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",34,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",33,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",301,\"color:#ff0000;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",429,\"color:#b30000;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",119,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",46,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",50,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",52,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",55,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",61,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",39,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",40,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",63,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",80,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",86,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",21,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",14,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",15,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",66,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",69,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",66,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",67,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",43,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",55,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",23,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",14,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]]]}";

        JsonNode apiNodeData = null;
        try {
            apiNodeData = mapper.readTree(a);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("--Date:"+apiNodeData.get("date"));

        JsonNode node2 =  apiNodeData.get("metrics").get(0);

        System.out.println("--Name:"+node2.get("name").asText());
        System.out.println("Average:"+node2.get("avg").asText());



    }


}
