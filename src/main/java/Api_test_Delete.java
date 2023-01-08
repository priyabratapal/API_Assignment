import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Api_test_Delete {
    String url = "https://restful-booker.herokuapp.com";
    public static Response response = null;
    String firstname = "John";String lastname = "Walker";
    String updateFirstname = "James";String updateLastname = "Brown";
    @Test(priority = 1)
    void POST(){
        baseURI = url;
        //Request Object
        RequestSpecification httpRequest = RestAssured.given();
        //Request payload
        JSONObject requestParams = new JSONObject();

        requestParams.put("firstname", firstname);
        requestParams.put("lastname", lastname);
        requestParams.put("totalprice", 111);
        requestParams.put("depositpaid", true);
        requestParams.put("additionalneeds", "Breakfast");
        HashMap<String, String> bookingdates = new HashMap<>();
        bookingdates.put("checkin",  "2018-01-01");
        bookingdates.put("checkout",  "2018-01-01");
        requestParams.put("bookingdates", bookingdates);
        System.out.println("Response Body id : " + requestParams);
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());

        //Response Object
        response = httpRequest.request(Method.POST, "/booking");

        //Print Response
        String responseBody = response.getBody().asString();
        System.out.println("ResponseBody" + responseBody);

        Integer bookingid01 = response.jsonPath().get("bookingid");
        System.out.println("Created bookingid is : " + bookingid01);

        //Verify Status Code
        System.out.println("Status Code is : " + response.getStatusCode());
        Assert.assertEquals(response.statusCode(),200);

        //Verify Updated Values
        String firstname1 = response.jsonPath().get("booking.firstname");
        Assert.assertEquals(firstname1,firstname);
        String lastname1 = response.jsonPath().get("booking.lastname");
        Assert.assertEquals(lastname1,lastname);
    }
    @Test(priority = 2)
    void GET(){
        baseURI = url;
        //Request Object
        RequestSpecification httpRequest = given();

        //Request payload
        httpRequest.header("Content-Type", "application/json");

        //Response Object
        int bookingid = response.jsonPath().get("bookingid");
        System.out.println("Created Booking ID from Post Method is : " + bookingid);
        Response response = httpRequest.request(Method.GET, "/booking/" + bookingid);
        System.out.println("Get Response is : "+response.getBody().asString());

        //Verify Updated Values
        String firstname1 = response.jsonPath().get("firstname");
        Assert.assertEquals(firstname1,firstname);
        String lastname1 = response.jsonPath().get("lastname");
        Assert.assertEquals(lastname1,lastname);
    }
    @Test(priority =3)
    void DELETE(){
        baseURI = url;

        //Request Object
        RequestSpecification httpRequest = given();

        //Request header
        httpRequest.header("Content-Type", "application/json");

        //Response Object
        int bookingid = response.jsonPath().get("bookingid");
        System.out.println("Booking ID to be deleted : " + bookingid);
        Response response = httpRequest.request(Method.DELETE, "/booking/" + bookingid);

        //Verify Status Code
        System.out.println("Status Code is : " + response.getStatusCode());
        //Assert.assertEquals(response.statusCode(),201);

        //Verify Status line
        System.out.println("Status line is : " + response.getStatusLine());

    }
}
