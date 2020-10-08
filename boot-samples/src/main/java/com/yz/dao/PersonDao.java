package com.yz.dao;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Data
@ToString
public class PersonDao {

    private String label = "1";

    public List<String> lists() {
        List<String> list = new ArrayList<>(8);
        for (int i = 0; i < 10; i++) {
            list.add("id_" + i);
        }
        return list;
    }

}
