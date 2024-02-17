package com.bean;

/*
        用于陈旧系统的数据获取
 */


public class MessageBody {
    String url;
    String message2;
    String element;
    String result;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message2;
    }

    public void setMessage(String message) {
        this.message2 = message;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "url='" + url + '\'' +
                ", message='" + message2 + '\'' +
                ", element='" + element + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
