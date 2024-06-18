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

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Base64;

public class AQIClient {

    ObjectMapper mapper = new ObjectMapper();
    public static void main(String args[]) {
        AQIClient api = new AQIClient();
        try {
            api.newAPI();

        } catch (Exception any) {
            any.printStackTrace();
        }
    }


    public void newAPI() {
        String uri = "https://airquality.cpcb.gov.in/caaqms/ParameterDetails";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "https://airquality.cpcb.gov.in/ccr/");
        headers.add("Origin", "https://airquality.cpcb.gov.in");
        headers.add("Content-Type","text/plain");
        headers.add("Cookie", "ccr_captcha=\"7EqXLhZxYKEFqlFLZ4tDz1svNJ+F8g2Oi+rpgjXkHoISF34c1gO5i4BpGvz6OiRCxjMP6g4sG+x/mj2A5/bvj33/kZxt1h/cLNPdGTVFgM8rJXzoTbSaZ5E12dPdklFWy9edCPwlagzIZxwuOVrvnk8zhFMrBj168FOF6IV/+hBoESSd+SZzLTyHTzlxF0cF6OOBrmRB3E6OUnR3FmmaMYQsOhkpnKWdxmjODQ3Ix8w=\"; ccr_public=\"W7+Sa6q+ugVcf8cazBt9xBK+9R42p3roQYRbA2BkBSWbHkniZV1rrln2nocsRGa25ev/6HxCE8tAWv7hH6Br0C2sz0vojXnxhUj0GAi9Ha6bSvbnAU+YZXchFEwHB1UlQW01YaMRCVYmvoc8sXjOWGcZN3flkR984lXfcdARdGR1fvULkoH9qPQditrI7sW9VC7YRHZ1LqiuhgS8cKuq1LCeWmxMujJz8eivcUrF2G8n9be4eVruPE7bcoJkf1Geq0hTVSBvN2hIqZRnoq7zTBPnTqqNrq+bWS11XPRCSIkfSdLZbXm6QTBdvCoou3qw\"");
        LocalDate startDate = LocalDate.of(2013, 9, 29);
        LocalDate endDate = LocalDate.of(2013, 12, 31);
        LocalDate currentDate = startDate;
        int i = 0;
        while (!currentDate.isAfter(endDate)) {
            i++;
            if (i % 50 == 0) {
                System.out.println("Sleep 5 sec after 50 hits");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // {"site_id":"site_115","date":"2013-01-1"}
            String tzDate = currentDate.toString(); //currentDate+"T00:00:00Z";
            String requestDate = "{\"site_id\":\"site_115\",\"date\":\"$DT$\"}";
            requestDate = requestDate.replace("$DT$", tzDate);
            String requestDateEncoded = encodeString(requestDate) + "==";
            System.out.println(requestDate);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestDateEncoded, headers);
            String response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class).getBody();

            System.out.println("Raw Response:" + response);
            String responseData = decodeString(response);
            try
            {
                PrintStream out = new PrintStream(new FileOutputStream("C:\\projectuniv\\aqicsv\\"+tzDate+".json")) ;
                out.print(responseData);
            }
            catch(Exception any)
            {
                any.printStackTrace();
            }
            currentDate = currentDate.plusDays(1);


        }
    }


    public void getPM25Data()
    {

     String a = "{\"title\":\"Pusa, Delhi - IMD\",\"nOfCom\":100,\"down\":\"false\",\"downmessage\":\"\",\"date\":\"Wednesday, 03 Apr 2019 12:00 AM\",\"temp\":\"\",\"aqi\":{\"param\":\"NO2\",\"value\":103,\"remark\":\"Moderate\",\"color\":\"#ffff00\"},\"metrics\":[{\"name\":\"PM2.5\",\"avg\":80,\"avgDesc\":\"Over the last 24 hours\",\"min\":37,\"max\":217,\"pollutantName\":\"PM2.5\"},{\"name\":\"PM10\",\"avg\":93,\"avgDesc\":\"Over the last 24 hours\",\"min\":58,\"max\":134,\"pollutantName\":\"PM10\"},{\"name\":\"NO2\",\"avg\":103,\"avgDesc\":\"Over the last 24 hours\",\"min\":17,\"max\":429,\"pollutantName\":\"NO2\"},{\"name\":\"CO\",\"avg\":59,\"avgDesc\":\"Over the last 8 hours\",\"min\":38,\"max\":86,\"pollutantName\":\"CO\"},{\"name\":\"OZONE\",\"avg\":36,\"avgDesc\":\"Over the last 8 hours\",\"min\":13,\"max\":69,\"pollutantName\":\"OZONE\"}],\"chartData\":[[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",59,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",60,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",83,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",129,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",217,\"color:#ff9900;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",112,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",97,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",41,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",64,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",78,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",60,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",79,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",37,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",82,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",97,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",103,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",90,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",83,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",93,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",114,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",134,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",113,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",110,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",58,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",70,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",84,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",62,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",76,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",88,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",95,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",119,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",44,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",105,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",79,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",62,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",53,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",49,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",193,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",104,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",19,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",17,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",34,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",33,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",301,\"color:#ff0000;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",429,\"color:#b30000;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",119,\"color:#ffff00;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",46,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",50,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",52,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",55,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",56,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",61,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",39,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",38,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",40,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",48,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",63,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",80,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",86,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]],[[\"date\",\"value\",{\"type\":\"string\",\"role\":\"style\"}],[\"Tuesday, 02 Apr 2019, 01:00 AM\",21,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 AM\",14,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 AM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 AM\",15,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 12:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 01:00 PM\",66,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 02:00 PM\",69,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 03:00 PM\",66,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 04:00 PM\",67,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 05:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 06:00 PM\",43,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 07:00 PM\",55,\"color:#009933;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 08:00 PM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 09:00 PM\",23,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 10:00 PM\",14,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Tuesday, 02 Apr 2019, 11:00 PM\",13,\"color:#006600;stroke-color: #caccdc   ; stroke-width: 1;\"],[\"Wednesday, 03 Apr 2019, 12:00 AM\",null,\"color:#848383;stroke-color: #caccdc   ; stroke-width: 1;\"]]]}";

        JsonNode apiNodeData = null;
        try {
            apiNodeData = mapper.readTree(a);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);        }

        System.out.println("--Date:"+apiNodeData.get("date"));
        JsonNode node2 =  apiNodeData.get("metrics").get(0);
        System.out.println("--Name:"+node2.get("name").asText());
        System.out.println("Average:"+node2.get("avg").asText());
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

}
