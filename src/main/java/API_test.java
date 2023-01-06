import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class API_test {

    @Test
    void GET(){
        Response response = get("https://restful-booker.herokuapp.com/booking");
        System.out.println("Response is : " +response.getStatusCode());

    }
}
