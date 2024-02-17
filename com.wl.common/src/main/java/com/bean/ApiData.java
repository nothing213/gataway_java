package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiData {
    @NotNull(message = "id 不能为空")
    private int id;
    private String apiname;
    private String head;
    private String body;
    private String parm;

    public String getParm() {
        return parm;
    }

    public void setParm(String parm) {
        this.parm = parm;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ApiData{" +
                "id=" + id +
                ", apiname='" + apiname + '\'' +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                ", parm='" + parm + '\'' +
                '}';
    }
}
