package com.myyt.util;

public final class TestConstants {
    public static final String API_KEY = "my-secret-api-key";
    public static final String ANYTHING_SEARCH_QUERY = "anything";
    public static final String EMPTY_STRING = "";

    public static final String TEST_FILE_PATH = "./src/test/java/com/myyt/util/test.txt";
    public static final String FAIL_TEST_FILE_PATH = "/4210773d9d64479cf8dcead10e4eab9fd8f8f66a864e6483fba523e4c8f2484a";

    public static final int HTTP_OK_STATUS_CODE = 200;
    public static final int HTTP_BAD_REQUEST_STATUS_CODE = 400;
    public static final String HTTP_OK_MESSAGE = "Ok";

    public static final String EXAMPLE_DOMAIN_URL = "https://www.example.com/";

    public static final String MOCK_JSON_RESPONSE = """
            {
              "kind": "youtube#searchListResponse",
              "etag": "LUto8WOeUbcPkgkGJDfQ-orKi_s",
              "nextPageToken": "CAIQAA",
              "regionCode": "HN",
              "pageInfo": {
                "totalResults": 8536,
                "resultsPerPage": 2
              },
              "items": [
                {
                  "kind": "youtube#searchResult",
                  "etag": "wFf1bXgyP2VEHD_hcT0wMdVi4zM",
                  "id": {
                    "kind": "youtube#video",
                    "videoId": "7WQDckhcF9k"
                  },
                  "snippet": {
                    "publishedAt": "2018-07-11T14:22:45Z",
                    "channelId": "UCyjKBSGMcxdGbSD86iq_SBw",
                    "title": "bladee - Nike Just Do It (เกาะเสม็ด)",
                    "description": "bladee - Nike Just Do It (เกาะเสม็ด) REDLIGHT lnk.to/bladeeredlight Prod. Whitearmor Vid. ECCO2K @BladeeCity @whitearmor1 ...",
                    "thumbnails": {
                      "default": {
                        "url": "https://i.ytimg.com/vi/7WQDckhcF9k/default.jpg",
                        "width": 120,
                        "height": 90
                      },
                      "medium": {
                        "url": "https://i.ytimg.com/vi/7WQDckhcF9k/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                      },
                      "high": {
                        "url": "https://i.ytimg.com/vi/7WQDckhcF9k/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                      }
                    },
                    "channelTitle": "drain gang",
                    "liveBroadcastContent": "none",
                    "publishTime": "2018-07-11T14:22:45Z"
                  }
                },
                {
                  "kind": "youtube#searchResult",
                  "etag": "gKusrwlH1HCKtUcld0mdr83HwvI",
                  "id": {
                    "kind": "youtube#video",
                    "videoId": "sFhRM6sG7xQ"
                  },
                  "snippet": {
                    "publishedAt": "2018-05-11T09:25:54Z",
                    "channelId": "UCyjKBSGMcxdGbSD86iq_SBw",
                    "title": "bladee - Nike Just Do It",
                    "description": "Stream/download: https://lnk.to/bladeeredlight prod. @whitearmor1 cover by Daniel Swan & Bladee.",
                    "thumbnails": {
                      "default": {
                        "url": "https://i.ytimg.com/vi/sFhRM6sG7xQ/default.jpg",
                        "width": 120,
                        "height": 90
                      },
                      "medium": {
                        "url": "https://i.ytimg.com/vi/sFhRM6sG7xQ/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                      },
                      "high": {
                        "url": "https://i.ytimg.com/vi/sFhRM6sG7xQ/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                      }
                    },
                    "channelTitle": "drain gang",
                    "liveBroadcastContent": "none",
                    "publishTime": "2018-05-11T09:25:54Z"
                  }
                }
              ]
            }
            """;

    public static final String MOCK_JSON = "{\"id\":{\"hello\":\"world\"}}";

    public static final String MOCK_NO_RESULTS_QUERY = "5bf8148d1f2426f5625b50baea82dff905394fae43802515f1f4c8ba2e6f3c825bf8148d1f2426f5625b50baea82dff905394fae43802515f1f4c8ba2e6f3c825bf8148d1f2426f5625b50baea82dff905394fae43802515f1f4c8ba2e6f3c82";
}