package self.alevinetx.plugins.maven.glo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;



public class GloUtil
{

	private String user_pat;
	private String glo_api;

	public static String GLO_BASE_URL = "https://gloapi.gitkraken.com/v1/glo";

	public static String ALL_BOARDS_REST_URL = "boards";

	public static String ADD_CARD_REST_URL = "boards/{boardId}/cards";

	public static String KEY_RESULT_CODE = "KEY_RESULT_CODE";
	public static String KEY_RESULT_VALUE = "KEY_RESULT_VALUE";
	public static String KEY_RESULT_PASSED = "KEY_RESULT_PASSED";

	public static String KEY_RESPONSE_CARD_ID = "id";

	com.fasterxml.jackson.databind.ObjectMapper json = new com.fasterxml.jackson.databind.ObjectMapper();

	public GloUtil(String userPat)
	{
		this(userPat, GLO_BASE_URL);
	}

	public GloUtil(String userPat, String api_url)
	{
		init();
		user_pat = userPat;
		glo_api = api_url;
	}

	private void init()
	{
		// Only one time
		Unirest.setObjectMapper(new ObjectMapper()
			{
				private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

				public <T> T readValue(String value, Class<T> valueType)
				{
					try
					{
						return jacksonObjectMapper.readValue(value, valueType);
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				}

				public String writeValue(Object value)
				{
					try
					{
						return jacksonObjectMapper.writeValueAsString(value);
					}
					catch (JsonProcessingException e)
					{
						throw new RuntimeException(e);
					}
				}
			});
	} // init

	public String getBoardIdByName(String boardName) throws Exception
	{
		throw new Exception("Not implemented");
	}

	public String getColumnIdByName(String boardId, String columnName) throws Exception
	{
		throw new Exception("Not implemented");
	}

	public Map<String, Object> addSimpleCard(String boardId, String columnId, String name, String description) throws Exception
	{

		Map<String, Object> processResult = new HashMap<String, Object>();
		Map<String, Object> card = createSimpleCard(columnId, name, description);

		try
		{
			// @formatter:off
			HttpResponse<String> response = Unirest.post(GLO_BASE_URL + "/" + ADD_CARD_REST_URL)
					.header("Authorization", "Bearer " + user_pat)
					.header("Content-Type", "application/json")
					.routeParam("boardId", boardId)
					.body(card)
					.asString();
			// @formatter:on

			int result = response.getStatus();

			processResult.put(KEY_RESULT_CODE, result);

			if (result >= 400)
			{
				processResult.put(KEY_RESULT_VALUE, response.getStatusText());
				processResult.put(KEY_RESULT_PASSED, Boolean.FALSE);
			}
			else
			{
				String card_id = getValueFromJson(response.getBody(), KEY_RESPONSE_CARD_ID);
				processResult.put(KEY_RESULT_VALUE, card_id);
				processResult.put(KEY_RESULT_PASSED, Boolean.TRUE);
			}
		}
		catch (Exception e)
		{
			throw new Exception("Error adding a simple card", e);
			
		}

		return processResult;
	}

	// utility functions
	private Map<String, Object> createSimpleCard(String columnId, String name, String description)
	{
		Map<String, Object> card = new HashMap<String, Object>();

		card.put("name", name);
		card.put("column_id", columnId);

		Map<String, String> cardDescription = new HashMap<String, String>();
		cardDescription.put("text", description);
		card.put("description", cardDescription);

		return card;

	} // createSimpleCard

	private String getValueFromJson(String nodeSrc, String key) throws Exception
	{
		String value = null;
		
		JsonNode node = json.readTree(nodeSrc);

		value = node.findValue(key).asText();

		return value;
	} // getValueFromJson

	/**
	 * rcode = response.getStatus();
	 * 
	 * if (rcode >= 200 && rcode <= 299) { String body =
	 * response.getBody().toString();
	 * 
	 * Map respmap = json.reader(Map.class).readValue(body); ArrayList<Map>
	 * repousers = (ArrayList<Map>) respmap.get("values"); for (Map<String, String>
	 * usermap : repousers) { String groupname = usermap.get("name");
	 * System.out.println(","+groupname+",,,,,,");
	 * 
	 * 
	 * 
	 * // what users in the group? System.out.println(",,Users,,,,,"); response =
	 * Unirest.get(LISTGROUP_USERS_REST_URL) .basicAuth(userName, password).
	 * routeParam("name", groupname). asJson();
	 * 
	 * rcode = response.getStatus();
	 * 
	 * if (rcode >= 200 && rcode <= 299) { body = response.getBody().toString();
	 * respmap = json.reader(Map.class).readValue(body); ArrayList<Map> usergroups =
	 * (ArrayList<Map>) respmap.get("values"); for (Map<String, String> usergroupmap
	 * : usergroups) { String username= usergroupmap.get("name");
	 * System.out.println(",,,"+username+",,,,");
	 * 
	 * } } // if 200-299 for LISTGROUP_USERS_REST_URL } // for each repo user } //
	 * if 200-299 for LISTGROUPS_REST_URL
	 * 
	 * 
	 * } // try catch (
	 * 
	 * Exception e) { e.printStackTrace(); }
	 * 
	 * // return slugs; } //fullAudit
	 * 
	 * 
	 **/

}