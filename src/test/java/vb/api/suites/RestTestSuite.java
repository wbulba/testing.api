package vb.api.suites;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.testng.Assert;
import vb.api.compare.UserData;
import vb.api.model.Token;
import vb.api.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public class RestTestSuite {

    private final String CLIENT_ID = "J5IDIJFKEHUCCQK5H2GF7JVCNNPKKJCT9HGE62LN07SJBTTRO5R86D173Q060LT5";
    private final String CLIENT_SECRET = "JJ8I55S7GK6E5VO1E47GPQ5QMLQQB0G63QA8BALTE2S1CNUN5Q25VNT0RTPH2NA5";
    private final String REDIRECT_URL = "http://test.test";
    private final String USER_AGENT = "vb/1.0 TestApp";
    private final String AUTH_URL = "https://m.hh.ru/oauth/authorize?response_type=code&client_id=" + CLIENT_ID;
    private final String ME_URL = "https://api.hh.ru/me";
    private final String USERNAME = "wbulba@mail.ru";
    private final String PASSWORD = "Rokfor5555555";

    private static String authorizationCode=null;
    private static String authorizationToken=null;

    @BeforeSuite
    public void init(){
        authorizationCode = getAuthorizationCode();
        authorizationToken = getAuthorizationToken(authorizationCode);
    }


    @Test
    public void verifyFIO(){
        User meModel=getMeModel();
        Assert.assertEquals(meModel.getId(), UserData.ID, "id name not match");
        Assert.assertEquals(meModel.getLast_name(), UserData.LAST_NAME, "last name not match");
        Assert.assertEquals(meModel.getFirst_name(), UserData.FIRST_NAME, "first name not match");
        Assert.assertEquals(meModel.getMiddle_name(), UserData.MIDDLE_NAME, "middle name not match");
    }

    @Test
    public void verifyChangeFIO(){
        changeFIO(UserData.LAST_NAME, UserData.FIRST_NAME, UserData.MIDDLE_NAME_NEW);
        User meModel = getMeModel();
        Assert.assertEquals(meModel.getLast_name(), UserData.LAST_NAME, "last name not match");
        Assert.assertEquals(meModel.getFirst_name(), UserData.FIRST_NAME, "first name not match");
        Assert.assertEquals(meModel.getMiddle_name(), UserData.MIDDLE_NAME_NEW, "middle name not match");

        changeFIO(UserData.LAST_NAME, UserData.FIRST_NAME, UserData.MIDDLE_NAME);
        meModel = getMeModel();
        Assert.assertEquals(meModel.getLast_name(), UserData.LAST_NAME, "last name not match");
        Assert.assertEquals(meModel.getFirst_name(), UserData.FIRST_NAME, "first name not match");
        Assert.assertEquals(meModel.getMiddle_name(), UserData.MIDDLE_NAME, "middle name not match");
    }

    @Test
    public void verifyAdditional(){
        User meModel=getMeModel();
        Assert.assertEquals(meModel.getEmail(), UserData.EMAIL, "email name not match");
        Assert.assertEquals(meModel.isIs_admin(), UserData.IS_ADMIN, "is_admin name not match");
        Assert.assertEquals(meModel.isIs_applicant(), UserData.IS_APPLICANT, "is_applicant name not match");
    }

    public User getMeModel(){

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client
                .resource(ME_URL);
        User responseMe = webResource.accept("application/json").header(HttpHeaders.USER_AGENT, USER_AGENT)
                .header("Authorization","Bearer "+authorizationToken)
                .get(User.class);
        client.destroy();
        return responseMe;
    }

    public void changeFIO(String lastName, String firstName, String middleName){

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
        formData.add("last_name", lastName);
        formData.add("first_name", firstName);
        formData.add("middle_name", middleName);
        WebResource webResource = client
                .resource(ME_URL);
        ClientResponse responseMe = webResource.accept("application/json").header(HttpHeaders.USER_AGENT, USER_AGENT)
                .header("Authorization", "Bearer " + authorizationToken)
                .post(ClientResponse.class, formData);
        client.destroy();
    }

    public String getAuthorizationCode(){
        String locatorUsername = "//input[@name='username']";
        String locatorPassword = "//input[@name='password']";
        String locatorSubmit = "//input[@type='submit']";

        WebDriver driver = new FirefoxDriver();
        driver.get(AUTH_URL);
        driver.findElement(By.xpath(locatorUsername)).sendKeys(USERNAME);
        driver.findElement(By.xpath(locatorPassword)).sendKeys(PASSWORD);
        driver.findElement(By.xpath(locatorSubmit)).click();
        String code = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf("?code=") + 6);
        driver.quit();
        return code;
    }

    public String getAuthorizationToken(String code){

        String TOKEN_URL = "https://m.hh.ru/oauth/token?grant_type=authorization_code&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&code="+code+"&redirect_uri=" + REDIRECT_URL;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        //client.addFilter(new LoggingFilter(System.out));
        WebResource webResource = client
                .resource(TOKEN_URL);
        Token response = webResource.accept("application/json").header(HttpHeaders.USER_AGENT, USER_AGENT)
                .post(Token.class);
        String accessToken = response.getAccess_token();
        client.destroy();
        return accessToken;
    }

}
