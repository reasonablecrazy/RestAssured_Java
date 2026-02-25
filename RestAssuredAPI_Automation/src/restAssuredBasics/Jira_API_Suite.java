package restAssuredBasics;

import static io.restassured.RestAssured.baseURI;
import pojo.*;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.TestData;
import io.restassured.path.json.JsonPath;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jira_API_Suite {

	static String issueID;

	@BeforeMethod
	public void setup() {
		baseURI = "https://oasis9988.atlassian.net";
	}

	@DataProvider(name = "getData")
	public Object[][] getData(Method method) throws IOException {

		String methodName = method.getName();

		Path path;
		String payload;

		if (methodName.equals("createIssue")) {
			path = Path.of(System.getProperty("user.dir"), "src", "files_jiraAPI", "createIssue.JSON");
			payload = Files.readString(path);
			return new Object[][] { { payload } };
		}
		return null;
	}
	
	@DataProvider(name = "getScreenshot")
	public Object[][] getScreenshot() throws IOException {

		Path path = Path.of(System.getProperty("user.dir"), "src", "files", "Screenshot.png");
		
		File file = path.toFile();
		
		return new Object[][] { { file } };
	}

	@Test(dataProvider = "getData")
	public void createIssue(String payload) {

		JsonPath js;
		String response;

		response = given().log().all().header("Content-Type", "application/json").header("Authorization",
				"Basic cmVzaG1pa2FkaXlha29sQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBhbHl0Nk53SzhMa21TYjJZOVpPRFFIcWJFZmlROW1VS0JjakJtNDZRclJaOFJSNDhMNnVHb2VVUjFWcmVIWWVEX2htVjZhUi1rUFVPZk5FMFN5VEhHOS1EX0o4dFcwOEd0UGJ4T1c1TVlVaUhzXy1ZSXpXajdxUXFKTHNPVVQxU0M1UWdFeVd0eG1kTkcyZXZrLUtkUkZrZUZVZVA0RXZYMWpqdkZheS1jQ1E9RDlCN0YzRDQ")
				.body(payload).post("/rest/api/3/issue").then().assertThat().statusCode(201).log().all().extract()
				.response().asString();

		js = new JsonPath(response);
		issueID = js.getString("id");
		System.out.println("Created bug ID: " + issueID);

	}

	@Test(dependsOnMethods="createIssue", dataProvider = "getScreenshot")
	public void addScreenshot(File file) {

		JsonPath js;
		String response;
		String postString = "/rest/api/3/issue/" + issueID + "/attachments";

		response = given().log().all().header("Authorization",
				"Basic cmVzaG1pa2FkaXlha29sQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBhbHl0Nk53SzhMa21TYjJZOVpPRFFIcWJFZmlROW1VS0JjakJtNDZRclJaOFJSNDhMNnVHb2VVUjFWcmVIWWVEX2htVjZhUi1rUFVPZk5FMFN5VEhHOS1EX0o4dFcwOEd0UGJ4T1c1TVlVaUhzXy1ZSXpXajdxUXFKTHNPVVQxU0M1UWdFeVd0eG1kTkcyZXZrLUtkUkZrZUZVZVA0RXZYMWpqdkZheS1jQ1E9RDlCN0YzRDQ")
				.header("X-Atlassian-Token", "no-check")
			    .multiPart("file", file).post(postString).then().assertThat().statusCode(200).log().all().extract().response()
				.asString();

		//js = new JsonPath(response);
		//System.out.println(js);

	}
	
	// File: `RestAssuredAPI_Automation/src/restAssuredBasics/Jira_API_Suite.java`

	@Test
	public void createIssueUsingPOJO() {

	    Jira_POJO jiraPOJO = new Jira_POJO();

	    Project project = new Project();
	    project.setKey("SCRUM");

	    IssueType issueType = new IssueType();
	    issueType.setName("Bug");

	    TextContent textContent = new TextContent();
	    textContent.setText("Creating of an issue using project keys and issue type names using the REST API");
	    textContent.setType("text");

	    Content content = new Content();
	    content.setType("paragraph");
	    content.setContent(Arrays.asList(textContent));

	    Description description = new Description();
	    description.setType("doc");
	    description.setVersion(1);
	    description.setContent(Arrays.asList(content));

	    Fields fields = new Fields();
	    fields.setProject(project);
	    fields.setSummary("Button is not working");
	    fields.setIssuetype(issueType);
	    fields.setDescription(description);

	    jiraPOJO.setFields(fields);

	    JsonPath js = given()
	        .log().all()
	        .header("Content-Type", "application/json")
	        .header("Authorization", "Basic cmVzaG1pa2FkaXlha29sQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBhbHl0Nk53SzhMa21TYjJZOVpPRFFIcWJFZmlROW1VS0JjakJtNDZRclJaOFJSNDhMNnVHb2VVUjFWcmVIWWVEX2htVjZhUi1rUFVPZk5FMFN5VEhHOS1EX0o4dFcwOEd0UGJ4T1c1TVlVaUhzXy1ZSXpXajdxUXFKTHNPVVQxU0M1UWdFeVd0eG1kTkcyZXZrLUtkUkZrZUZVZVA0RXZYMWpqdkZheS1jQ1E9RDlCN0YzRDQ")
	        .body(jiraPOJO)
	        .post("/rest/api/3/issue")
	        .then()
	        .assertThat()
	        .statusCode(201)
	        .log().all()
	        .extract()
	        .jsonPath();

	    issueID = js.getString("id");
	    System.out.println("Created bug ID using POJO: " + issueID);
	}


	
	

}
