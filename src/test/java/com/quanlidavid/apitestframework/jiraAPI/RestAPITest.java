package com.quanlidavid.apitestframework.jiraAPI;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.quanlidavid.apitestframework.utils.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestAPITest {

	private static Logger log = LogManager.getLogger(RestAPITest.class.getName());

	Properties prop = new Properties();

	@BeforeClass
	public void getData() throws IOException {

		//第一种方法
		// FileInputStream fis = new FileInputStream(
		// this.getClass().getClassLoader().getResource("jira.properties").getPath());
		//prop.load(fis);
		
		//第二种方法
		//prop.load(this.getClass().getClassLoader().getResourceAsStream("jira.properties"));
		
		//第三种方法
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\jira.properties");
		prop.load(fis);
	}

	@Test
	public void jiraE2E() {
		// Task 1- Login get response
		log.info("Host information: " + prop.getProperty("JIRAHOST"));
		log.info("Login get response.");
		RestAssured.baseURI = prop.getProperty("JIRAHOST");
		Response response = given()
								.header("Content-Type", "application/json")
								.body(JiraPayloads.loginJiraPostData())
								.when().post(JiraEndpoints.jiraLogin())
								.then().assertThat().statusCode(200)
								.and().contentType(ContentType.JSON).and().extract().response();
		
		log.info("Login response: " + response.asString());
		// Task 2- Grab the session name and value from response
		JsonPath js = ReusableMethods.rawToJson(response);
		String session_name = js.get("session.name");
		String session_value = js.get("session.value");
		log.info("Got session name: " + session_name);
		log.info("Got session value: " + session_value);

		// Task 3 create a issue and get the issue id
		response = given()
						.header("cookie", session_name + "=" + session_value)
						.header("Content-Type", "application/json")
						.body(JiraPayloads.createIssueJiraPostData())
						.when().post(JiraEndpoints.jiraCreateIssue())
						.then().assertThat().statusCode(201)
						.and().contentType(ContentType.JSON).extract().response();
		
		log.info("Create issue response: " + response.asString());
		js = ReusableMethods.rawToJson(response);
		String issueId = js.get("id");
		log.info("created issuedID: " + issueId);

		// Task 4 create a comment of the issue
		response = given()
					.header("cookie", session_name + "=" + session_value)
					.header("Accept", "application/json")
					.header("Content-Type", "application/json")
					.pathParam("issueId", issueId)
					.body(JiraPayloads.addCommentJiraPostData())
					.when().post(JiraEndpoints.jiraAddCommentToIssue("{issueId}"))
					.then().assertThat().statusCode(201)
					.and().contentType(ContentType.JSON).extract().response();
		
		log.info("Create comment response: " + response.asString());
		js = ReusableMethods.rawToJson(response);
		String commentId = js.get("id");
		log.info("created commentId: " + commentId);

		// Task 5 delete a comment of the issue
		response = given()
						.header("cookie", session_name + "=" + session_value)
						.header("Accept", "application/json")
						.pathParam("issueId", issueId).pathParam("commentId", commentId)
						.when().delete(JiraEndpoints.jiraDeleteCommentOfIssue("{issueId}", "{commentId}"))
						.then().assertThat().statusCode(204)
						.and().contentType(ContentType.JSON).extract().response();
		log.info("Delete comment response: " + response.asString());
		log.info("Comment: " + commentId + " of issue: " + issueId + " deleted.");
	}
}
