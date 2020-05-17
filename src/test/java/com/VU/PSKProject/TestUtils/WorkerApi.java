package com.VU.PSKProject.TestUtils;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class WorkerApi extends BaseApi {

    public static Response createWorker(JsonObject requestBody, String token) {

        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", token);
        System.out.println("Request CREATE api/workers/create ...");
        System.out.println("Request Body: " + requestBody.toString());
        Response response = request.given()
                .body(requestBody.toString())
                .post("api/workers/create");
        System.out.println("Response code: " + response.getStatusCode());
        if (response.getStatusCode() == 200)
            System.out.println("Request was successful.");
        System.out.println("Response content type: " + response.getContentType());
        ResponseBody responseBody = response.getBody();
        System.out.println("Response body: ");
        responseBody.prettyPrint();
        return response;
    }

    public static Response getWorkerById(int id, String token) {

        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", token);

        JsonObject requestBody = new JsonObject();
        System.out.println("Request GET api/workers/get/" + id + " ...");
        Response response = request.given()
                .body(requestBody.toString())
                .get("api/workers/get/" + id);
        ResponseBody responseBody = response.getBody();
        System.out.println("Response code: " + response.getStatusCode());
        if (response.getStatusCode() == 200)
            System.out.println("Request was successful.");
        System.out.println("Response content type: " + response.getContentType());
        System.out.println("Response body: ");
        responseBody.prettyPrint();
        // verify api/workers/get/{id} response is success
        return response;
    }

    public static Response deleteWorkerById(int id, String token) {

        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", token);

        JsonObject requestBody = new JsonObject();
        System.out.println("Request DELETE api/workers/delete/" + id + " ...");
        Response response = request.given()
                .body(requestBody.toString())
                .delete("api/workers/delete/" + id);
        ResponseBody responseBody = response.getBody();
        System.out.println("Response code: " + response.getStatusCode());
        if (response.getStatusCode() == 200)
            System.out.println("Request was successful.");
        System.out.println("Response content type: " + response.getContentType());
        System.out.println("Response body: ");
        responseBody.prettyPrint();
        // verify api/workers/get/{id} response is success
        return response;
    }

    public static Response updateWorkerById(int id, JsonObject requestBody, String token) {

        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", token);

        System.out.println("Request UPDATE api/workers/put/" + id + " ...");
        System.out.println("Request Body: " + requestBody.toString());
        Response response = request.given()
                .body(requestBody.toString())
                .put("api/workers/update/" + id);
        if (response.getStatusCode() == 200)
            System.out.println("Request was successful.");
        System.out.println("Response content type: " + response.getContentType());
        ResponseBody responseBody = response.getBody();
        System.out.println("Response code: " + response.getStatusCode());
        System.out.println("Response body: ");
        responseBody.prettyPrint();
        // verify api/workers/get/{id} response is success
        return response;
    }

    public static Response getAllWorkers(String token) {

        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", token);

        JsonObject requestBody = new JsonObject();
        System.out.println("Request GET api/workers/getAll ...");
        Response response = request.given()
                .body(requestBody.toString())
                .get("api/workers/getAll");
        System.out.println("Response code: " + response.getStatusCode());
        if (response.getStatusCode() == 200)
            System.out.println("Request was successful.");
        System.out.println("Response content type: " + response.getContentType());
        ResponseBody responseBody = response.getBody();
        System.out.println("Response body: ");
        responseBody.prettyPrint();
        return response;
    }
}
