package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tag")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "time_car_in")
    private Timestamp timeCarIn;

    @Column(name = "time_car_out")
    private Timestamp timeCarOut;

}
