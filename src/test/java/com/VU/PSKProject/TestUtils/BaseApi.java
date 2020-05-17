package com.VU.PSKProject.TestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.google.gson.JsonObject;

public class BaseApi {
    public static String getToken(String email, String password) {
        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = RestAssured.given().contentType("application/json");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);

        System.out.println("Getting token at " + "http://localhost:8080/login" + " with email: " + email + ", password: " + password);
        Response response = request.given()
                .body(requestBody.toString())
                .post("login");
//        Assert.assertEquals(response.getStatusCode(), 200, "Verify response is OK");
        System.out.println("Token: " + response.getHeader("Authorization"));
        return response.getHeader("Authorization");
    }
}
