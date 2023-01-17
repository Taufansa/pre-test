package com.user.pretest.dao;

import com.user.pretest.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserContactUpdateDao {
    private String id;
    private String address;
    private User user;
}
