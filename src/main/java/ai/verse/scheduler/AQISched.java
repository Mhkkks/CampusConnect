package ai.verse.scheduler;


import ai.verse.Sentiment;
import ai.verse.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class AQISched {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    AQIRepository aqiRepository;


   // @Scheduled(fixedRate = 1000000)
public void fetchAQIDataAndSave()
{

    //if(1<2) return;
    String uri = "https://airquality.cpcb.gov.in/aqi_dashboard/aqi_all_Parameters";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Host","https://airquality.cpcb.gov.in" );
    headers.add("accessToken","eyJ0aW1lIjoxNzE4NjA2NDAyMDM5LCJ0aW1lWm9uZU9mZnNldCI6LTYwfQ==");
    headers.add("Cookie","_xsrf=462b9dbb4c0c44cb85a9a5b3aba67280; ccr_captcha=\"8rDT/kRz07wYMrOH2SbX6ndC5NG2ylrmlQgs3KEw7P20lrO3DPUDQhNPA4cJ6Khe20gzeTS/IZ2oYAoOVb/NYq21aOcNRZXfG9tNUVBSs6xsvAU2mgH/c3PjPWuCJn0tj4FDYJs5+fvlN9U9YxL9iF3pdD+qJi8vkwqoKKgSGhDlc921kp44uIdclW5PVQ1L7VBM0nu/RUTKPUdajOJlVZTNinPfL6Mc4Ix2TVdVZm0=\"; ccr_public=\"vKWEKbgClpRIM15O6MWHLPEsWuWTJdY2BHfTJRxUnk1VHJICC2raDAG1KyeMN0EhbiGYhMcf1Sf+Qios/nsRMbJ9Dr1XGHq1oSbYvhqYVmKL1DVpTxnQteZCtLP5BPYsRHAtfz7QpClXKqFRo80ovqjM1SDrLRkq4g/i4MqXwjP27+7QowhtNmqdYa1p2abN87wCIlAsc//Rj03RVMqZcdynaGzOiVu/+qqNQL3u+AgdxNluFYs2jfkXFaizM/fpfsTKpWU4/zJJwguPdCxf3/L/octGLrmVoBS6mylLCAcX8gbVxhZChUuSs/+jN/PR\"");
 //   LocalDate startDate = LocalDate.of(2019, 1, 1);
    LocalDate startDate = LocalDate.of(2013, 03, 01);
    // End date: December 31, 2019
    LocalDate endDate = LocalDate.of(2013, 12, 31);

    // Iterate through all dates in 2019
    LocalDate currentDate = startDate;

    int i=0;
    while (!currentDate.isAfter(endDate)) {

     //   Thread.sleep(1000);
        System.out.println(currentDate+"T00:00:00Z");
       i++;

       if(i%20==0)
       {
           System.out.println("Sleep 10 sec");
           try {
               Thread.sleep(10000);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }

        String tzDate = currentDate+"T00:00:00Z";
        //  2019-09-21
     //   String requestDate = "{\"station_id\":\"site_107\",\"date\":\"$DT$\"}";
        String requestDate = "{\"station_id\":\"site_115\",\"date\":\"$DT$\"}";
        requestDate = requestDate.replace("$DT$",tzDate);
        System.out.println(requestDate);

        String requestDateEncoded = encodeString(requestDate)+"==";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestDateEncoded, headers);
        String response =    restTemplate.exchange(uri, HttpMethod.POST, entity, String.class).getBody();

        System.out.println("Raw Response:"+response);

        String responseData = decodeString(response);
        System.out.println(responseData);

        saveAQIData(currentDate.toString(), "site_115",responseData);

        currentDate = currentDate.plusDays(1);

    }
}


    public void saveAQIData(String dttime, String station, String jsonData )
    {
        AQIEntity aqi = new AQIEntity( );
        aqi.setDatetime(dttime);
        aqi.setJsondata(jsonData);
        aqi.setStation(station);
        aqiRepository.save(aqi);
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


    // Call this automatically every 10 seconds
// @Scheduled(fixedRate = 1000000)
    public void updateDataAQI() {
        System.out.println(" --------------- testData Called on:" + new Date());
        List<AQIEntity> data = aqiRepository.findAllByIdGreaterThan(Long.valueOf(395));
        System.out.println(data);

        for(int k=0; k< data.size(); k++)
        {
            AQIEntity _data = data.get(k);


            JsonNode apiNodeData = null;
            try {
                apiNodeData = mapper.readTree(_data.getJsondata());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            System.out.println("--Date:"+apiNodeData.get("date"));

            JsonNode node2 =  apiNodeData.get("metrics").get(0);




            _data.setJsondate(apiNodeData.get("date").asText());
            if(node2 !=null && node2.get("avg") !=null) {
                System.out.println("Average:"+node2.get("avg").asText());
                System.out.println("--Name:"+node2.get("name").asText());
                _data.setJsonavg25(node2.get("avg").asInt(0));
            }
            aqiRepository.save(_data);

        }
    }


}