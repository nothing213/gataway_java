import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

public class test {
    public static void main(String[] args) {
        JSONObject jsonObj = new JSONObject("{\n" +
                "    \"variables\":{\n" +
                "        \"fieldName\": \"fieldValue\",\n" +
                "        \"age\": \"22\"\n" +
                "    },\n" +
                "    \"id\": \"0141c3223a6e4d9dbd7c4f605fd0fb48\",\n" +
                "    \"version\": 1,\n" +
                "    \"formTitle\": \"xxx表单\",\n" +
                "    \"processInstanceId\": \"355cfccf9bc63ce4282c0c437b71fdfb\",\n" +
                "    \"processDefinitionId\": \"355cfccf9bc63ce4282c0c437b71fdfb\",\n" +
                "    \"formDefinitionId\": \"105cfccf9bc63ce6a82c0c437b41fd3c\",\n" +
                "    \"taskId\": \"64c761a7fb7f4847992466ee76efc195\",\n" +
                "    \"author\": \"a08bcb7e67a84cb08348884688aacd02\",\n" +
                "    \"authorName\": \"张三\",\n" +
                "    \"createDate\": 12324123123,\n" +
                "    \"lastModifyDate\": 12312312312,\n" +
                "    \"lastModifier\": \"a08bcb7e67a84cb08348884688aacd02\",\n" +
                "    \"lastModifierName\": \"张三\",\n" +
                "    \"applicationId\": \"f58bcb7e67a84c458348884688aacd7f\"\n" +
                "}");
//        System.out.println(jsonObj.get("taskId")+" "+jsonObj.get("authorName"));

        String url = "openapi: 3.0.0\n" +
                "servers:\n" +
                "  # Added by API Auto Mocking Plugin\n" +
                "  # Added by API Auto Mocking Plugin\n" +
                "  - description: SwaggerHub API Auto Mocking\n" +
                "    url: https://virtserver.swaggerhub.com/apilifecare/try1.1/1.0.0\n" +
                "  - description: SwaggerHub API Auto Mocking\n" +
                "    url: https://pilatez-backend.lifecare.sg\n" +
                "info:\n" +
                "  description:  >-\n" +
                "\n" +
                "    Smart Pilatez (SPS) Project,\n" +
                "    App Specific API List,\n" +
                "    v0.1g,\n" +
                "    \n" +
                "    Backend URL: https://pilatez-backend.lifecare.sg,\n" +
                "    Frontend URL: -\n" +
                "\n" +
                "    Enterprise Admin User: sps-admin@gmail.com,\n" +
                "    End User: sps-user@gmail.com,\n" +
                "\n" +
                "    -\n" +
                "\n" +
                "    # Error Codes\n" +
                "\n" +
                "    What errors and status codes can a user expect?\n" +
                "\n" +
                "    404/409 errors\n" +
                "\n" +
                "    # Rate limit\n" +
                "\n" +
                "    Is there a limit to the number of requests an user can send?\n" +
                "\n" +
                "    no\n" +
                "\n" +
                "    # Extra info \n" +
                "    \n" +
                "    test cases : \n" +
                "    MQTT Broker: atthings-mqtt.theatthings.com:3600\n" +
                "    Username: respiree-device@gmail.com\n" +
                "    Password: astralink1234\n" +
                "   \n" +
                "  version: \"1.0.0\"\n" +
                "  title: Smart Pilatez (SPS) Project\n" +
                "  contact:\n" +
                "    email: sps-admin@gmail.com\n" +
                "  license:\n" +
                "    name: Apache 2.0\n" +
                "    url: 'http://www.apache.org/licenses/LICENSE-2.0.html'\n" +
                "tags:\n" +
                "  - name: Admin log in \n" +
                "    description: admin user info \n" +
                "  - name: Admin log out\n" +
                "    description: admin user info\n" +
                "paths:\n" +
                "  /AdminUserAccount :\n" +
                "    post:\n" +
                "      tags:\n" +
                "        - Admin log in \n" +
                "        - Admin log out\n" +
                "      summary: admin info \n" +
                "      operationId: logged in/out\n" +
                "      description: Login/logout admin account\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: \"logged in/logged out successfully\" \n" +
                "        '400':\n" +
                "          description: 'invalid input, object invalid'\n" +
                "        '409':\n" +
                "          description: \"an existing item already exists\"\n" +
                "      requestBody:\n" +
                "        content:\n" +
                "          application/json:\n" +
                "            schema:\n" +
                "              $ref: '#/components/schemas/webLogin'\n" +
                "        description: Admin login/logout info\n" +
                "components:\n" +
                "  schemas:\n" +
                "    webLogin:\n" +
                "      type: object\n" +
                "      required:\n" +
                "        - id\n" +
                "        - Password\n" +
                "        - manufacturer\n" +
                "        - releaseDate\n" +
                "      properties:\n" +
                "        id:\n" +
                "          type: string\n" +
                "          format: uuid\n" +
                "          example: sps-admin@gmail.com\n" +
                "        Password:\n" +
                "          type: string\n" +
                "          example: Astralink1234# \n" +
                "        releaseDate:\n" +
                "          type: string\n" +
                "          format: date-time\n" +
                "          example: '2016-08-29T09:12:33.001Z'\n" +
                "        manufacturer:\n" +
                "          $ref: '#/components/schemas/webLogout'\n" +
                "    webLogout:\n" +
                "      required:\n" +
                "        - Admin log out\n" +
                "      properties:\n" +
                "        name:\n" +
                "          type: string\n" +
                "          example: admin user\n" +
                "        webLogin:\n" +
                "          type: string\n" +
                "          format: url\n" +
                "          example: 'https://pilatez-backend.lifecare.sg/sps/authentication/webLogin'\n" +
                "        webLogout:\n" +
                "          type: string\n" +
                "          format: url\n" +
                "          example: 'https://pilatez-backend.lifecare.sg/sps/authentication/webLogout'\n" +
                "      type: object";
        JSONObject json = convertToJson(url);
        System.out.println(json.get("openapi"));

    }
    public static JSONObject convertToJson(String yamlString) {
        Yaml yaml= new Yaml();
        Map<String,Object> map= (Map<String, Object>) yaml.load(yamlString);

        JSONObject jsonObject=new JSONObject(map);
        return jsonObject;
    }
}
