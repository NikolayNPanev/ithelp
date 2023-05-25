package bg.tu_varna.sit.task_manager.service;

import bg.tu_varna.sit.task_manager.payload.LoginDto;
import bg.tu_varna.sit.task_manager.payload.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String login(HttpServletRequest req, LoginDto loginDto);
    String register(RegisterDto registerDto);
    void logout(HttpServletRequest req);
}

