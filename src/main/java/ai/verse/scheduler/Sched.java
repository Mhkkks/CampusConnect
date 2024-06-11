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

//
//  //  @Scheduled(fixedRate = 1000000)
//public void fetchAQIDataAndSave()
//{
//
//    if(1<2) return;
//    String uri = "https://airquality.cpcb.gov.in/aqi_dashboard/aqi_all_Parameters";
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Host","https://airquality.cpcb.gov.in" );
//    headers.add("accessToken","eyJ0aW1lIjoxNzE3OTU4NzY1MDI3LCJ0aW1lWm9uZU9mZnNldCI6LTYwfQ==");
//    headers.add("Cookie","ccr_captcha=uxnO8/dPdAYrYx88aEycyeNP0UqQhpruar4T0Noo3hh5Pcddv8IWAZcVkTpBjX7T9icIgmGlXEoQsbfBSCqpVyWaYAeCNIwjzpACH9IraHs32jRE6Wz355IKjiUoroUsickh/0cIokrlJW3prbfLVboNR8icxQZvtLQEkOE1yN9EeiZ/nFBz0ZNkNjq9wc32gSnMTBRDe2kU6kpcwmJr7Z4Dd2hmYDzdV5Y+k/iFrAU=\"; ccr_public=\"ZYUY0ahpDkgdvpyWCkYXRWOmuplaN1wucjBZo5O0YmGfGQZ4h4aKL7nXaOLUdyvWIh1E/eq74iboqFAFKTMarPvm5zg/hjC0Fvset0atyTwwizrIgZN2rHgnCEPKHacNsR90Aff7gFc1cAsQYU1LOA1s0OlsJQRQ277WY3METp+d7I2SSwO56d4BWAKxh6V/sfja9ub8DJbxBjRyTQlmhvDdBsuedDLe6AWtvQ2E+FKTuWYkbYAcckhQOXRYx/D8+PvehEq8C1qlILzFo9HJcOPuAba6o7vYBhSt6Z+E8cgPVpSs+4hTFzS3IthzlwtO");
//
// //   LocalDate startDate = LocalDate.of(2019, 1, 1);
//    LocalDate startDate = LocalDate.of(2019, 12, 25);
//    // End date: December 31, 2019
//    LocalDate endDate = LocalDate.of(2019, 12, 31);
//
//    // Iterate through all dates in 2019
//    LocalDate currentDate = startDate;
//
//    int i=0;
//    while (!currentDate.isAfter(endDate)) {
//
//     //   Thread.sleep(1000);
//        System.out.println(currentDate+"T00:00:00Z");
//       i++;
//
//       if(i%20==0)
//       {
//           System.out.println("Sleep 10 sec");
//           try {
//               Thread.sleep(10000);
//           } catch (InterruptedException e) {
//               throw new RuntimeException(e);
//           }
//       }
//
//        String tzDate = currentDate+"T00:00:00Z";
//        //  2019-09-21
//        String requestDate = "{\"station_id\":\"site_107\",\"date\":\"$DT$\"}";
//        requestDate = requestDate.replace("$DT$",tzDate);
//        System.out.println(requestDate);
//
//        String requestDateEncoded = encodeString(requestDate)+"==";
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(requestDateEncoded, headers);
//        String response =    restTemplate.exchange(uri, HttpMethod.POST, entity, String.class).getBody();
//
//        System.out.println("Raw Response:"+response);
//
//        String responseData = decodeString(response);
//        System.out.println(responseData);
//
//        saveAQIData(currentDate.toString(), "site_107",responseData);
//
//        currentDate = currentDate.plusDays(1);
//
//    }
//}


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

}