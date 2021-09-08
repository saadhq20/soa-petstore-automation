import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Api_automation {

    private RequestSpecification httpRequest;
    private int petId;

        @BeforeTest
        public void setUp(){
            final String base_URL = "https://petstore3.swagger.io";
            RestAssured.baseURI = base_URL;
            petId = (int)(Math.random()*100);
            httpRequest = RestAssured.given();
            JSONObject map = new JSONObject();
            map.put("id", petId);
            map.put("name", "mcgee");
            map.put("status", "available");

            JSONObject map_category = new JSONObject();
            map_category.put("id",3);
            map_category.put("name", "Cat");
            map.put("category",map_category);

            JSONArray array_photo_Url = new JSONArray();
            array_photo_Url.add("String");
            map.put("photoUrls",array_photo_Url);

            JSONObject map_tags = new JSONObject();
            map_tags.put("id",24);
            map_tags.put("name","tag1");
            JSONArray array_tags = new JSONArray();
            array_tags.add(map_tags);
            map.put("tags", array_tags);

            httpRequest.header("Content-Type","application/json");
            httpRequest.header("api_key","saadhaq");
            httpRequest.body(map.toJSONString());
            
        }

        @Test(priority = 0)
        public void addPet(){
            Response response = httpRequest.request(Method.POST,"/api/v3/pet");
            JsonPath path = response.jsonPath();


            int StatusCode = response.getStatusCode();
            String verify_name = path.get("name");
            int verify_id = path.get("id");

            System.out.println("Status: "+path.get("status"));
            Assert.assertEquals(StatusCode, 200);
            Assert.assertEquals(verify_name, "mcgee");
            Assert.assertEquals(verify_id, petId);
        }

        @Test(priority = 2)
        public void getPet() throws InterruptedException {
            Thread.sleep(1000);
            System.out.println("Pet ID getPet: "+petId);
            Response response = httpRequest.request(Method.GET,"/api/v3/pet/"+petId);
            JsonPath path = response.jsonPath();

            System.out.println(response.body().asString());
            int StatusCode = response.getStatusCode();
            String verify_name = path.get("name");
            int verify_id = path.get("id");

            System.out.println("Status: "+path.get("status"));
            Assert.assertEquals(StatusCode, 200);
            Assert.assertEquals(verify_name, "mcgee");
            Assert.assertEquals(verify_id, petId);
        }

        @Test(priority=4)
        public void updatePet(){
            // /api/v3/pet/{petID} endpoint was not working for 'POST'; Added input via URL

            //Status Updated from 'available' to 'pending'
            Response response = httpRequest.request(Method.POST,"/api/v3/pet/"+petId+"?name=mcgee&status=pending");
            JsonPath path= response.jsonPath();

            System.out.println("TEST3 PET ID: "+petId);
            int StatusCode = response.getStatusCode();
            String verify_name = path.get("name");
            int verify_id = path.get("id");
            String status = path.get("status");


            Assert.assertEquals(StatusCode, 200);
            Assert.assertEquals(verify_name, "mcgee");
            Assert.assertEquals(verify_id, petId);
            //verifying if the body was actually updated
            Assert.assertEquals(status, "pending");
            //Alternatively
            Assert.assertNotEquals(status,"available");
        }

        @Test(priority = 6)
        public void removePet(){
            Response response = httpRequest.request(Method.DELETE,"/api/v3/pet/1");


            System.out.println(response.body().asString());
            int StatusCode = response.getStatusCode();


            Assert.assertEquals(StatusCode, 200);


            Response response2 = httpRequest.request(Method.GET,"/api/v3/pet/1");
            JsonPath path = response2.jsonPath();
            String body = response2.getBody().asString();
            StatusCode = response2.getStatusCode();
            Assert.assertEquals(StatusCode, 404);
            Assert.assertEquals(body, "Pet not found");
        }


}
