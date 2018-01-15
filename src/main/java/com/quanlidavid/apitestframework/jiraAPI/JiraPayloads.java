package com.quanlidavid.apitestframework.jiraAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JiraPayloads {

	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	public static String loginJiraPostData() {
		String bodyStr = "{ \"username\": \"quanlidavid\", \"password\": \"53930000\" }";
		return bodyStr;
	}

	public static String createIssueJiraPostData() {
		String bodyStr = "	{\n" + "	      \"fields\": {\n" + "	        \"project\": {\n"
				+ "	          \"key\": \"RES\"\n" + "	        },\n"
				+ "	        \"summary\": \"something's wrong\",\n" + "	        \"issuetype\": {\n"
				+ "	          \"name\": \"Bug\"\n" + "	        }\n" + "			}\n" + "	}";
		return bodyStr;
	}

	public static String addCommentJiraPostData() {
		String bodyStr = "    {\n"
				+ "      \"body\": \"A coment dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.\",\n"
				+ "      \"visibility\": {\n" + "        \"type\": \"role\",\n"
				+ "        \"value\": \"Administrators\"\n" + "      }\n" + "    }";
		return bodyStr;
	}

}
