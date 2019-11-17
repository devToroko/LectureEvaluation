package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ForTest {
	//https://static.javadoc.io/com.google.code.gson/gson/2.7/com/google/gson/Gson.html
	//http://blog.naver.com/PostView.nhn?blogId=accond&logNo=220510235577
	public static void main(String[] args) {
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonObjects = new JsonObject();
		
		JsonArray jsonArray = new JsonArray();
		
		Gson gson = new Gson();
		Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
		
		String getValue = "";
		String getPretty = "";
		
		jsonObject.addProperty("name", "홍길동");
		jsonObject.addProperty("id", "sampark1234");
		jsonObjects.add("who", jsonObject);
		
		
		getValue = gson.toJson(jsonObjects);
		getPretty = gsonPretty.toJson(jsonObjects);
		
		System.out.println(getValue);
		System.out.println(getPretty);
		
		
		for (int i = 0; i < 10; i++) {
			jsonObject = new JsonObject();
			jsonObject.addProperty("name", i+" name");
			jsonObject.addProperty("id",  i+" id");
			jsonArray.add(jsonObject);
		}
		
		
		JsonObject json = new JsonObject();
		json.add("members", jsonArray);
//		System.out.println(gsonPretty.toJson(json));
		System.out.println(gson.toJson(json));
		
	}
}
