package requestBuilder;

import io.restassured.response.Response;

import  static Common.BasePath.ndosi_BaseURL ;
import static Common.ContentType.contentType;

import static PayloadBuilder.PayloadBuilder.createUserPayload;
import static io.restassured.RestAssured.given;
import static PayloadBuilder.PayloadBuilder.createUserPayload ;
import static PayloadBuilder.PayloadBuilder.loginPayload;

public class RequestBuilder
{
    public static String token;
    public static String userId;

    public static Response createUserResponse(String firstName, String lastName, String email, String password, String confirmPassword, String groupId) {
        Response response = given().
                when().
                body(createUserPayload(firstName, lastName, email, password, confirmPassword, groupId)).
                contentType(contentType).
                log().all().
                post(ndosi_BaseURL + "/register").
                then().
                log().all().
                extract().response();

        userId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response loginrResponse(String userName, String password) {
        Response response = given().
                when().
                body(loginPayload(userName, password)).
                contentType(contentType).
                log().all().
                post(ndosi_BaseURL + "/login").
                then().
                log().all().
                extract().response();
        token = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response approveUser()
    {
        Response response = given().
                when().
                contentType(contentType).
                header("Authorization", "Bearer " + token).
                log().all().
                put(ndosi_BaseURL + "/admin/users/" + userId + "/approve").
                then().
                log().all().
                extract().response();
        return response;
    }

    public static Response enrollUserCourse()
    {
        Response response =
                given()
                        .baseUri("https://www.ndosiautomation.co.za")
                        .basePath("/APIDEV")
                        .header("accept", "*/*")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .post("/courses/44c56443-dd75-4ed8-aa17-397cce243c0c/enroll")
                        .then()
                        .extract()
                        .response();

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response: " + response.asString());

        return response;
    }
}
