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

    // Call this automatically every 10 seconds
//@Scheduled(fixedRate = 1000000)
    public void updateDataAQI() {
        System.out.println(" --------------- testData Called on:" + new Date());
        List<AQIEntity> data = aqiRepository.findAll();
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