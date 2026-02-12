package files;

public class TestData {
	
	
	
	public static String addPlacePayload(String name, String language, String address, String phoneNumber) {
		return "{\n"
				+ "  \"location\": {\n"
				+ "    \"lat\": -38.383494,\n"
				+ "    \"lng\": 33.427362\n"
				+ "  },\n"
				+ "  \"accuracy\": 50,\n"
				+ "  \"name\": \""+name+"\",\n"
				+ "  \"phone_number\": \""+phoneNumber+"\",\n"
				+ "  \"address\": \""+address+"\",\n"
				+ "  \"types\": [\n"
				+ "    \"shoe park\",\n"
				+ "    \"shop\"\n"
				+ "  ],\n"
				+ "  \"website\": \"http://google.com\",\n"
				+ "  \"language\": \""+language+"\"\n"
				+ "}\n"
				+ "";
		
	}
	
	public static String deletePlacePayload(String placeId) {
		
		return "{\r\n"
				+ "    \"place_id\":\""+placeId+"\"\r\n"
				+ "}";
		
	}
	
	public static String updatePlacePayload(String placeId, String newAddress) {
		
		return "{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}";
		
	}
	

}
