package com.quanlidavid.apitestframework.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static XmlPath rawToXML(Response response) {
		String responseString = response.asString();
		XmlPath xmlPath = new XmlPath(responseString); 
		return xmlPath;
	}
	
	public static JsonPath rawToJson(Response response) {
		String responseString = response.asString();
		JsonPath jsonPath = new JsonPath(responseString);
		return jsonPath;
	}
	
}
