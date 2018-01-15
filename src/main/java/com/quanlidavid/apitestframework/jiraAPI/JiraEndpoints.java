package com.quanlidavid.apitestframework.jiraAPI;

public class JiraEndpoints {

	public static String jiraLogin() {
		String res = "/rest/auth/1/session";
		return res;
	}

	public static String jiraCreateIssue() {
		String res = "/rest/api/2/issue";
		return res;
	}

	public static String jiraAddCommentToIssue(String issueIdOrKey) {
		String res = "/rest/api/2/issue/" + issueIdOrKey + "/comment";
		return res;
	}

	public static String jiraDeleteCommentOfIssue(String issueIdOrKey, String commentId) {
		String res = "/rest/api/2/issue/" + issueIdOrKey + "/comment/" + commentId;
		return res;
	}

}
