package com.yz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;

import javax.persistence.*;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
