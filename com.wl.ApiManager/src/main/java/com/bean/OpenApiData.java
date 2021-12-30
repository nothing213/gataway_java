package com.bean;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiData {

   private int id;
   private String apiName;
   private String openApi;
   private String info;
   private String servers;
   private String methods;
   private String paths;
   private String requestBody;
   private String responseResult;
   private String group_api;

    @Override
    public String toString() {
        return "OpenApiData{" +
                "id=" + id +
                ", apiName='" + apiName + '\'' +
                ", openApi='" + openApi + '\'' +
                ", info='" + info + '\'' +
                ", servers='" + servers + '\'' +
                ", methods='" + methods + '\'' +
                ", paths='" + paths + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", group='" + group_api + '\'' +
                '}';
    }
}
