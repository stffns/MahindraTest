package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
public class RestAssuredExtension {

    public static RequestSpecification Request;

    /**
     * The RestAssuredExtension class provides methods to send HTTP requests
     * (GET, POST, DELETE, PUT) with different parameters and bodies.
     *
     * The constructor initializes a request specification with a base URI and a content type.
     */
    public RestAssuredExtension(){

        //Initialize the request specification with the base URI and content type.
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://reqres.in/api");
        builder.setContentType(ContentType.JSON);
        var requestSpec = builder.build();
        Request = RestAssured.given().spec(requestSpec);
    }

    //This method is used to send a GET request with parameters in the URL path.
    public static void GetWithParameters(String url, Map<String, String> pathParams){
        //Set path parameters to the request and send the GET request.
        Request.pathParams(pathParams);
        try{
            Request.get(new URI(url));
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    //This method is used to send a GET request with query parameters.
    public static void GetWithQueryParams(String url, String queryParams)  {
        //Set query parameters to the request and send the GET request.
        try{
            Request.queryParam(queryParams);
            Request.get(new URI(url));
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    //This method is used to send a GET request and returns the server's response.
    public static ResponseOptions<Response> GetOPS(String url){
        //Send the GET request and return the response.
        try{
            return Request.get(new URI(url));
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    //This method is used to send a POST request with a body.
    public static ResponseOptions<Response> PostWithBodyParams(String url, List<Map<String, String>> body){
        //Set the body to the request and send the POST request.
        Request.body(body);
        return Request.post(url);
    }

    //This method is used to send a DELETE request with a body.
    public static ResponseOptions<Response> DeleteWithBodyParams(String url, List<Map<String, String>> body){
        //Set the body to the request and send the DELETE request.
        Request.body(body);
        return Request.delete(url);
    }

    //This method is used to send a PUT request with a body.
    public static ResponseOptions<Response> PutWithBodyParams(String url, List<Map<String, String>> body){
        //Set the body to the request and send the PUT request.
        Request.body(body);
        return Request.put(url);
    }
}
