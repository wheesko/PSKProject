package com.VU.PSKProject.ApiTests.SmokeTests.Worker;

import com.VU.PSKProject.BaseTest;
import com.VU.PSKProject.TestUtils.BaseApi;
import com.VU.PSKProject.TestUtils.WorkerApi;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;


public class WorkerApiSmokeTest extends BaseTest {

    private int testWorkerId = 1234;
    private int testWorkerManagerId = 12345;
    private String testWorkerEmail = "email@test.com";
    private String testWorkerName = "Jonas";
    private String testWorkerSurname = "Jonauskas";


    @Test(description = "Verify CREATE worker SUCCESS", groups = "verifyCreateWorkerSuccess")
    public void verifyCreateWorkerSuccess() {
        String token = BaseApi.getToken("admin", "admin");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("id", testWorkerId);
        requestBody.addProperty("managerId", testWorkerManagerId);
        requestBody.addProperty("email", testWorkerEmail);
        Assert.assertEquals(
                "Verify get single worker response status code is 200",
                WorkerApi.createWorker(requestBody, token).getStatusCode(),
                200);
    }

    // negative test case
    @Test(description = "Verify it is not possible to create a worker without a managerId")
    public void verifyCreateWorkerWithoutManagerFailure() {
        String token = BaseApi.getToken("admin", "admin");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("id", testWorkerId);
        requestBody.addProperty("email", testWorkerEmail);
        requestBody.addProperty("name", "Should");
        requestBody.addProperty("surname", "Fail");
        Assert.assertNotEquals(
                "Verify create worker without managerId response status code is NOT 200",
                WorkerApi.createWorker(requestBody, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify GET single worker SUCCESS", groups = "verifyGetWorkerSuccess")
    public void verifyGetWorkerSuccess() {
        // 1. Create worker
        // 2. Get worker by id
        String token = BaseApi.getToken("admin", "admin");

        // Verify GET response is SUCCESS
        Assert.assertEquals(
                "Verify get single worker response status code is 200.",
                WorkerApi.getWorkerById(testWorkerId, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify GET single worker FAILURE")
    public void verifyGetWorkerFailure() {
        // 1. Get worker by id
        // 2. Verify GET response by non-existent id is NOT OK
        String token = BaseApi.getToken("admin", "admin");

        // Verify GET response is SUCCESS
        Assert.assertNotEquals(
                "Verify GET response by non-existent id is NOT OK",
                WorkerApi.getWorkerById(-1, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify DELETE single worker SUCCESS")
    public void verifyDeleteWorkerSuccess() {
        // 1. Create worker
        // 2. Delete worker by id
        // 3. Verify response is OK
        String token = BaseApi.getToken("admin", "admin");

        // Verify response is SUCCESS
        Assert.assertEquals(
                "Verify delete single worker response status code is 200.",
                WorkerApi.deleteWorkerById(testWorkerId, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify DELETE single worker FAILURE")
    public void verifyDeleteWorkerFailure() {
        // 1. Delete worker by non-existent id
        // 2. Verify response is NOT OK
        String token = BaseApi.getToken("admin", "admin");

        // Verify response is SUCCESS
        Assert.assertNotEquals(
                "Verify delete single worker by non-existent id response status code is NOT 200.",
                WorkerApi.deleteWorkerById(-1, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify UPDATE single worker SUCCESS", groups = "verifyUpdateWorkerSuccess")
    public void verifyUpdateWorkerSuccess() {
        // 1. Create a worker
        // 2. Update worker name
        // 3. Verify response is OK
        String token = BaseApi.getToken("admin", "admin");

        // Verify response is SUCCESS
        JsonObject requestBody = new JsonObject();
        // add a name to created worker
        requestBody.addProperty("name", "UpdatedName");
        Assert.assertEquals(
                "Verify update single worker response status is 200.",
                WorkerApi.updateWorkerById(testWorkerId, requestBody, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify UPDATE single worker FAILURE", groups = "verifyUpdateWorkerSuccess")
    public void verifyUpdateWorkerFailure() {
        // Verify update worker with non-existent id does not return OK
        // Please don't just check if id > 0, actually check if the id is not present in the repository
        // 1. Update worker name
        // 2. Verify response is NOT OK
        String token = BaseApi.getToken("admin", "admin");

        // Verify response is SUCCESS
        JsonObject requestBody = new JsonObject();
        // add a name to created worker
        requestBody.addProperty("name", "UpdatedName");
        Assert.assertNotEquals(
                "Verify update single worker with non-existent id response status is NOT 200.",
                WorkerApi.updateWorkerById(-1, requestBody, token).getStatusCode(),
                200);
    }

    @Test(description = "Verify GET all workers SUCCESS")
    public void verifyGetAllWorkersSuccess() {
        String token = BaseApi.getToken("admin", "admin");
        // Verify response is SUCCESS
        Assert.assertEquals(
                "Verify getAll workers response status code is 200.",
                WorkerApi.getAllWorkers(token).getStatusCode(),
                200);
    }

    @AfterGroups(groups = {"verifyCreateWorkerSuccess", "verifyGetWorkerSuccess", "verifyUpdateWorkerSuccess"})
    public void deleteWorkerAfterTest() {
        String token = WorkerApi.getToken("admin", "admin");
        WorkerApi.deleteWorkerById(testWorkerId, token);
    }

    @BeforeGroups(groups = {"verifyGetWorkerSuccess", "verifyUpdateWorkerSuccess"})
    public void createWorkerBeforeTest() {
        String token = BaseApi.getToken("admin", "admin");

        JsonObject createWorkerRequestBody = new JsonObject();
        createWorkerRequestBody.addProperty("id", testWorkerId);
        createWorkerRequestBody.addProperty("email", testWorkerEmail);
        createWorkerRequestBody.addProperty("managerId", testWorkerManagerId);
        createWorkerRequestBody.addProperty("name", "CreatedBeforeTest!");
        WorkerApi.createWorker(createWorkerRequestBody, token);
    }
}
