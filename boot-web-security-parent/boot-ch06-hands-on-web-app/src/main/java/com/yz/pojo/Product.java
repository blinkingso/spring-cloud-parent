package com.yz.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@Entity(name = "product")
public class Product implements Serializable {

    @Id
    private Long id;
    private String name;
    private String price;
    private String currency;
}
