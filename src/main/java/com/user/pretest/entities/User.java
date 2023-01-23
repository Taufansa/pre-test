package com.user.pretest.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id @Column(length = 50)
    private String id;

    private String name;

    private Integer age;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserContact> contacts;
}
