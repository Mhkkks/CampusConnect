package ai.verse;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

public class ApiClient {

    public static void main(String args[]) {

        ApiClient api = new ApiClient();

        api.callFacebookAPI();
    }


    // Using an api : api client

    // Here facebook has created an api and we are consuming it , so we are api client

    private void callFacebookAPI() {
        String accessToken = "EAA4uBIZCLHrEBOwcED5ZBlfReZAOHZBmHCb76h41AMxi3y6tc4Mk7ndzJA3ZC9rAh8BZAmwKWZCAbH8D1unyo9pUfCtgYjz4lTIGGn6YlqRfdGyn3pn01zwZAE6kx8LFZCmJhwUIsEZCB4Be2y8wlDegbEUASz9ucvnk68MNmt1zK020TrpdNf6ZAhwVh7S63ZBZCUryDuaz6T6Fb9iPVmoib1I4B4XfoaUYkJSFn";
        String uri = "https://graph.facebook.com/v19.0/me/posts?fields=comments&access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();  // this is used to call external API  in Java
        String result = restTemplate.getForObject(uri, String.class);  // This result in json format
        System.out.println(result);


      //  Next we need to use json parser
        // Write parser and put all posts in a list and save in db

/**
        {
            "data": [
            {
                "comments": {
                "data": [
                {
                    "created_time": "2024-05-14T07:20:32+0000",
                        "from": {
                    "name": "AI Page",
                            "id": "331878276672800"
                },
                    "message": "This is test post superr",
                        "id": "122098716890322306_25803638655901140"
                },
                {
                    "created_time": "2024-05-14T07:20:24+0000",
                        "from": {
                    "name": "AI Page",
                            "id": "331878276672800"
                },
                    "message": "This is  nice",
                        "id": "122098716890322306_391857713847303"
                },
                {
                    "created_time": "2024-05-16T05:54:48+0000",
                        "from": {
                    "name": "AI Verse",
                            "id": "7959247167458080"
                },
                    "message": "16th May another good post",
                        "id": "122098716890322306_1955450111520011"
                },
                {
                    "created_time": "2024-05-16T05:46:34+0000",
                        "from": {
                    "name": "AI Verse",
                            "id": "7959247167458080"
                },
                    "message": "Good company",
                        "id": "122098716890322306_351382277555829"
                },
                {
                    "created_time": "2024-05-15T05:26:35+0000",
                        "from": {
                    "name": "AI Verse",
                            "id": "7959247167458080"
                },
                    "message": "\ud83d\udc4d",
                        "id": "122098716890322306_963196278686318"
                },
                {
                    "created_time": "2024-05-15T06:14:32+0000",
                        "from": {
                    "name": "AI Verse",
                            "id": "7959247167458080"
                },
                    "message": "Reply to post",
                        "id": "122098716890322306_3278527582442955"
                }
        ],
                "paging": {
                    "cursors": {
                        "before": "NgZDZD",
                                "after": "MQZDZD"
                    }
                }
            },
                "id": "331878276672800_122098716890322306"
            }
  ],
            "paging": {
            "cursors": {
                "before": "QVFIUmlVdFVRZAEtsdHhxSFRYd2ptSE4zenFZARXA3RUdFZAlhLS3N4SDNzc1M2cTdKamY0RE1lbjhUemtzdVVJNS1xSk8zdkxZAZADM1UEFtR3BtekllUnRQOG92Nmp3WTZAvTlZAIM1BDVVlKX0JjYWtuWm1sekRxODVlSm1uaHZAlSDYydGlwSDJiRDdyYndRMS0wMThaUGxzT3JWVkx6cWp4ZAk1LdkFrWTZAGVnFkRHI0TmV3SWhRck4yWDBYdTE0bzU1aDI1MGRZAUkNvVHhKbVJUa1Fmd2d2T0wxUmRQRkJhY2Y4ankxYjlrN1pOa01rRWlETXBNbndvWTBxMURQMXUwTHc1cC0weDYxZA2JQZAFVCN1ZA5SFd5QXAyZAXVZAMkE2bTNHSjgyTUs4TEwzM1RTLXhLLXNCRG12djdCWWh4QnN1N0FsZA1BJOVBBNjlvemVWcmlnejN1Um1hUDJMOFlWOGRVdC1DQ2FXc2lucThUOEZAFbjJ2Mk9WLUxDVjJnbEliLW1Dd3JsZAnB2WTFSWXdrNUZAMcFJpYjFha0dHSUoxandTTmpsX0pXcW03X1BBNkt2d28ZD",
                        "after": "QVFIUi1OcTZAYWThtZAXg3NWduZADQ3NzJwNlk0bUJwNEZABSGpnMGptbHFsOGJOdjRkS3JQZAmFySzZADMTR2dlFoR3ZAJemZACTWJZAZAlpMVElnenpZAUHlJMjBKdjJULVN4TXBaSFFib2Jkb2lRS2lRb1AwV1R4Q2k2aU01dGdfMTJIVmVGRDk1YW1YbjN3VTQ1SHFVUWZAKYWQzZAXFlLWRCUDV2X3I3bFBVMVltSXdUZATNNMTlmb2g4aE84ZAVZATMTY2S1NCbkVYT0ZAodWw2dHFuMFl3Ym9fTXk5cWtPaDNqcFVud1RoMVUtdzBteVdUSHVLQUtYWWFUc1lOSUVLWlRVbXhUbF9HYUlIWHR6Rlc2Q1JxbjJMMUF6R1ZAyNy1IQXMzSGdPajFBSmFZAc1JSOFBOTFVWeFdvYkJqejdEYWx0N09zeWlQdFlxbjlYYXNvdk9lQVk0QWxvdUNUU293QVdvcFZAMdnFFVmNrMHJEVVFEZAUhybU9DM3NEci10LTdEN3hoaEJwMnFHOUhaQk9rYnRaek9KYzd1NmZA0NGFHNVBXRXZA3Yk9EVW1lRkpxcXdFSGpNSjQZD"
            }
        }
        }

        */

    }


}
