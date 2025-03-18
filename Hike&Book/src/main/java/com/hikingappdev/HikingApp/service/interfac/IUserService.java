package com.hikingappdev.HikingApp.service.interfac;


import com.hikingappdev.HikingApp.dto.LoginRequest;
import com.hikingappdev.HikingApp.dto.Response;
import com.hikingappdev.HikingApp.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
