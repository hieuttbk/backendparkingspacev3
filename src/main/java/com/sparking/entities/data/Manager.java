package com.sparking.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "manager")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "last_time_access")
    private Timestamp lastTimeAccess;

    @Column(name = "acp", nullable = false)
    private Boolean acp;

    @Column(name = "address", nullable = false)
    private String address ;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "id_number", nullable = false)
    private int idNumber;

}
