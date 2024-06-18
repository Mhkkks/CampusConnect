package ai.verse.scheduler;


import ai.verse.Sentiment;
import ai.verse.repo.*;
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
public class Sched {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    PostRepository postRepository;

    @Autowired
    AadhaarRepository aadhaarRepository;

    @Autowired
    AQIRepository aqiRepository;

    // Call this automatically every 10 seconds
//@Scheduled(fixedRate = 10000)
    public void testData() {
        System.out.println(" --------------- testData Called on:" + new Date());
        List data = aadhaarRepository.findAll();
        System.out.println(data);
    }


  //  @Scheduled(fixedRate = 100000)
    public void updateSentimentInDatabase() {
        System.out.println(" --------------- updateSentimentInDatabase Called on:" + new Date());

        //  List<PostEntity> list = postRepository.findAll();  // Get all posts from database
        List<PostEntity> list = postRepository.findRowsWithNoSentiment();  // Lists posts with no sentiment

        System.out.println("--- NUMBER OF ROWS WITH NO SENTIMENT IS:  --->>" + list.size());

        // Here we are iterating over all the posts and calling sentimen class
        for (int h = 0; h < list.size(); h++) {
            Sentiment sm = new Sentiment();
            PostEntity post = list.get(h);   // This will get post in a loop
            String sentm = sm.getSentimentOfText(post.getPost());  // this gets the sentiment of the post
            post.setSentiment(sentm);
            postRepository.save(post);  // save in database
            //    System.out.println(post);
        }
    }


        // @Scheduled(fixedRate = 100000)
    public void callFacebookAPI() {
        try {
            String accessToken = "EAA4uBIZCLHrEBO5GR1J320nZAi35erJndJdBz6TXx3joyjGipaszk1ZCBNZCIrAGTZBwJ8zmUZCoFdVeBEOlPD0br4AnZAz9TT8wpc3i21Y3ZBhNlORcVMVwdeKTRWUrRqRjZAaZBifkCtCZB7xOsMXmSIkC9ayQ3X3lWrZByDSq3H5B2J5ObVNTOzRAKKHVWjw7FwkEUrxCGPIP";
            String uri = "https://graph.facebook.com/v19.0/me/posts?fields=comments&access_token=" + accessToken;

            RestTemplate restTemplate = new RestTemplate();  // this class is used to call external API  in Java
            String fbData = restTemplate.getForObject(uri, String.class);  // This result in json format
            System.out.println(fbData);
            JsonNode fbDataJsonNode = mapper.readTree(fbData);

            /**
             1. ArrayNode -  get("data")
             2. get zero element
             3. get Comments
             4. get "data"  has Array
             5.   Each data has message
             */

            ArrayNode fbDataNode = (ArrayNode) fbDataJsonNode.get("data");
            ArrayNode fbInnerDataNode = (ArrayNode) fbDataNode.get(0).get("comments").get("data");

            List<PostEntity> postsList = new ArrayList<>(); // create an empty list
            for (int g = 0; g < fbInnerDataNode.size(); g++) {
                System.out.println(fbInnerDataNode.get(g).get("message").asText());

                PostEntity entity = new PostEntity();
                entity.setPost(fbInnerDataNode.get(g).get("message").asText());
                postsList.add(entity);
            }

            postRepository.saveAll(postsList);
        } catch (Exception any) {
            any.printStackTrace();
        }
    }



}