package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiGroup {

    private int id;
    private String apiGroup;


    @Override
    public String toString() {
        return "ApiGroup{" +
                "id=" + id +
                ", apiGroup='" + apiGroup + '\'' +
                '}';
    }
}
