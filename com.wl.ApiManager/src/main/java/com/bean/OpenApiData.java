package com.bean;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * 用于存放api信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName(value = "gis_feature_clazz",autoResultMap = true)
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

    private String input2;
    private String input22;
    private String input1;
    private String input11;
    private String input3;
    private String input33;
    private String parm_query[];//用于存放
    private String parm_query_value[];

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
                ", group_api='" + group_api + '\'' +
                ", input2='" + input2 + '\'' +
                ", input22='" + input22 + '\'' +
                ", input1='" + input1 + '\'' +
                ", input11='" + input11 + '\'' +
                ", input3='" + input3 + '\'' +
                ", input33='" + input33 + '\'' +
                ", parm_query=" + Arrays.toString(parm_query) +
                ", parm_query_value=" + Arrays.toString(parm_query_value) +
                '}';
    }
}
