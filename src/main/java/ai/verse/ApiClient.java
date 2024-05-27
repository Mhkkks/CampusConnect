package ai.verse;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

public class ApiClient {

    public static void main(String args[])
    {

        ApiClient api  = new ApiClient();

        api.callFacebookAPI();
    }


    private  void callFacebookAPI()
    {
        String accessToken = "EAA4uBIZCLHrEBO2dn62DJh2GBvj0e8o2AWZBHbDYwkjP0YEvb1ceVoFNiGmV7tMbEAnddSHwFN52hlDVS5Ng6pKiZBZCLkNRT1H2uIaeix2RwSUnMKVQv3HKQSFYiqiQIh6BzCzMooyJvohKpETEoEOZBZALgT4Xs0kKskld1OrtnfVi5DDWytaI1T8trZCh0409Gv53VN7W6WRSVEzCQV4YfjG5uDZA5VAn";
         String uri = "https://graph.facebook.com/v19.0/me/posts?fields=comments&access_token="+accessToken;

     //  String uri =  URLEncoder.encode(url);

        RestTemplate restTemplate = new RestTemplate();  // this is used to call external API
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);


   // Write parser and put all posts in a list and save in db




      //  {"data":[{"comments":{"data":[{"created_time":"2024-05-14T07:20:32+0000","from":{"name":"AI Page","id":"331878276672800"},"message":"This is test post superr","id":"122098716890322306_25803638655901140"},{"created_time":"2024-05-14T07:20:24+0000","from":{"name":"AI Page","id":"331878276672800"},"message":"This is  nice","id":"122098716890322306_391857713847303"},{"created_time":"2024-05-16T05:54:48+0000","from":{"name":"AI Verse","id":"7959247167458080"},"message":"16th May another good post","id":"122098716890322306_1955450111520011"},{"created_time":"2024-05-16T05:46:34+0000","from":{"name":"AI Verse","id":"7959247167458080"},"message":"Good company","id":"122098716890322306_351382277555829"},{"created_time":"2024-05-15T05:26:35+0000","from":{"name":"AI Verse","id":"7959247167458080"},"message":"\ud83d\udc4d","id":"122098716890322306_963196278686318"},{"created_time":"2024-05-15T06:14:32+0000","from":{"name":"AI Verse","id":"7959247167458080"},"message":"Reply to post","id":"122098716890322306_3278527582442955"}],"paging":{"cursors":{"before":"NgZDZD","after":"MQZDZD"}}},"id":"331878276672800_122098716890322306"}],"paging":{"cursors":{"before":"QVFIUkRlczVaSzlsd0JWeVpXWTlGVXFlaE9KR1kxNDd5R2hwd3VsVmxGSnlBVXpXd0tpY0s4ZADF4SjNtX1NBTWtFS0lPVkRrR2hKU1pqYWVFMTBYMDY0STFMb3M4aTV5NHdmU0ZA2NjBwRldxMlcwMF9SWkVkMk9NNXRNTXVBT29DaWRSUFZAMcWN4VFd4cjVpTEJpeVQzZAnhURTRCZAG5qem4zYWVhZAWxUYmJaaEUxSnAwSFNaTF8ybFlBMGhCTEtqZA1hZAdHZAPRU5rcHZAmRHlGVmpGd0lCekpqN09ab0I2ZATdNczJhYjdlSTJlU2diVEZAVYWxwRVRiRlM3QXN0dlNRS0FtNmxoYTJEaWJPVENPODNwTXBXZADFfaUJ2dExDRVRULWpxdWRFaFRYbGQ0ZAjZATU2FqNEpXTjRRRlBhS3dxRTZA6M3U3SDNoMHVuc19sdzNIbWpGNFNtTGYzd3pWQzV3YkF2X3RtVHhELS1yMmpCYWM3dEd1RkU1cEVpa2ozY2RrN3AzMTVEVV8yUkl3MFlteEt3dFI3azlnLTNUczREQjZADOFZAPRnNCT0FhWWp2elkZD","after":"QVFIUmJfTW4yb1hUSUdDd0ZA4MGs2YUJGMU9JenVHVGlYWlotYVNwbzNGOWdtZA2xOa0FvVkd6RjFUWl85NFNPYmZArS191OXRldU01QU4zcG1QdHhOcnY1UnJUa2VfeGlGVk9vYXZA5ZATJsX2hoZATcwdzZAXd2pwUDQwb3lMQ0xlUXM1OGdtREpxeG1CSktVa1RQX2oweVh6dzZAQTDBKZAHJ4SWFhal9TTVVIdlM3bThHbkE4bmdEMWFQZA3ZALcVktbjBuOTdiNmY0MEZAGMnF2LURySC1lOU8zRzR6N2ZAPVGNPdTluTmZAiOVNkNFE5WWx4dkhwdld3ejBiUk5WeFJLcFFjUExxRWRjRjFUeVk3WXN5U1FjYk5YZAHFsWXJEZA1VTbjlIeF9GbWlmRE5mODU2QVVzYW9lVHZAYMlc3NnQ0WTllY1ZAUSUN3elZAJLWVTQUVhQjE3U1hxQ3ZAZAdXFpX2poU28xbXpxTmhCbno4NHJjZATRBaUdXZAEtIWWlrVnBsUUc5dV9UNl90aWJGZAExkdkxkMmp6bTNWemlFM2NHSWJSUkxPQ0E3OXlUWk1KcUpSdGZAzQkkZD"}}}




    }


}
