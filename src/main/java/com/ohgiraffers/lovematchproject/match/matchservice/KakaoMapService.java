package com.ohgiraffers.lovematchproject.match.matchservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service("kakao")
public class KakaoMapService {

    // 주소를 좌표로 바꾸고
    // 두 좌표간 거리 구하기

    // api 사용하기 //@Value를 이용해 yml에 있는 key를 비공개로 불러온다
    @Value("${api.kakaoMap.key}")
    private String kakaoMapApiKey;
    // .yml 에서 api.kakaoMap.key 라는 키를 읽어와 kakaoMapApikey변수에 값을 대입한다.

    private final RestTemplate matchRestTemplate;
    // RESTful 웹 서비스와의 통신을 쉽게 도와주는 Spring 클래스 입니다.
    // 여기서는 matchRestTemplate 변수를 통해 REST API 요청을 보낼 때 사용 됩니다.
    @Autowired
    public KakaoMapService(@Qualifier("matchRestTemplate") RestTemplate matchRestTemplate) {
        this.matchRestTemplate = matchRestTemplate;
    }
    // 이 클래스의 생성자로, RestTemplate 인스턴스를 주입받아 matchRestTemplate 변수에 할당한다.

    // 주소를 api로 검색해서 좌표 구하기
    public JSONObject getCoordinates(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedAddress;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoMapApiKey);

            ResponseEntity<String> response = matchRestTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class);

            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray documents = jsonResponse.getJSONArray("documents");

            if (documents.length() > 0) {
                JSONObject firstResult = documents.getJSONObject(0);
                double x = firstResult.getDouble("x");
                double y = firstResult.getDouble("y");
                return new JSONObject().put("x", x).put("y", y);
            }

        }catch (Exception e){
            System.err.println("Error processing address: " + address);
            e.printStackTrace();
            return null;
        }

        return null;
    }


    // Haversine 공식을 사용하여 두 좌표 간의 거리 계산
    public double calculateDistance(JSONObject coord1, JSONObject coord2) {
        final int R = 6371; // 지구 반지름 (km)

        double lat1 = coord1.getDouble("y");
        double lon1 = coord1.getDouble("x");
        double lat2 = coord2.getDouble("y");
        double lon2 = coord2.getDouble("x");

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // m로 변환

        return distance;
    }

/*
    // 두 좌표간의 거리 구하기 //kakao api버전
    public double calculateDistance(JSONObject coord1, JSONObject coord2) {
        String url = "https://dapi.kakao.com/v2/local/geo/distance.json";
        String params = String.format("?x=%f&y=%f&tx=%f&ty=%f",
                coord1.getDouble("x"), coord1.getDouble("y"),
                coord2.getDouble("x"), coord2.getDouble("y"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        ResponseEntity<String> response = matchRestTemplate.exchange(
                url + params, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray documents = jsonResponse.getJSONArray("documents");

        if (documents.length() > 0) {
            return documents.getJSONObject(0).getDouble("distance");
        }

        return -1;
    }
*/











/*    @Autowired
//    private final MatchRestTemplate matchRestTemplate;

    public KakaoMapService(RestTemplate matchRestTemplate) {
        this.matchRestTemplate = matchRestTemplate;
    }

    public String getCoordinates(String address){
        // 카카오 API를 사용하여 주소를 좌표로 변환하는 로직
        String url = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/search/address.json")
                .queryParam("query", address)
                .toUriString();
        // API 호출 및 결과 처리 로직을 여기에 구현
        try {
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Send the request
            ResponseEntity<String> response = matchRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Parse the response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray documents = jsonObject.getJSONArray("documents");
                if (documents.length() == 0) {
                    return null;
                }
                JSONObject location = documents.getJSONObject(0);
                String x = location.getString("x");
                String y = location.getString("y");
                return x + "," + y;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        return "좌표값"; // 좌표값을 반환
    }*/











    /*public double[] getCoordinates(String location) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + location;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Autorization","KakaoAK " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = matchRestTemplate.exchange(url, HttpMethod.GET, entity,
                (Class<Map<String, Object>>)(Class)Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null && !((List<?>) body.get("documents")).isEmpty()){
            Map<String, Object> documents = (Map<String, Object>) ((List<?>) body.get("documents")).get(0);
            Map<String, Object> addressData = (Map<String, Object>) documents.get("address");
            double latitude = Double.parseDouble((String) addressData.get("y"));
            double longitude = Double.parseDouble((String) addressData.get("x"));
            return new double[]{latitude, longitude};
        }
        return null;
    }*/

}
