package bg.tu_varna.sit.task_manager.service.impl;

import bg.tu_varna.sit.task_manager.exception.ApiException;
import bg.tu_varna.sit.task_manager.model.Role;
import bg.tu_varna.sit.task_manager.model.User;
import bg.tu_varna.sit.task_manager.payload.LoginDto;
import bg.tu_varna.sit.task_manager.payload.RegisterDto;
import bg.tu_varna.sit.task_manager.repository.RoleRepository;
import bg.tu_varna.sit.task_manager.repository.UserRepository;
import bg.tu_varna.sit.task_manager.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(HttpServletRequest req, LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()
                ));

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);

        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        return "User logged-in successfully!";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User already exist!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = registerDto.getRole()
                .stream()
                .map(this::getRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
      /*
      //задаване само на една роля
      Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(registerDto.getRole()).orElseThrow(
                ()-> new ApiException(HttpStatus.BAD_REQUEST, "Not such a role"));
        roles.add(userRole);*/

     /*
      //задаване на повече от една роля - без stream()
     Set<Role> roles = new HashSet<>();
        if(!registerDto.getRole().isEmpty()) {
            for(String r:registerDto.getRole()) {
                Role userRole = getRole(r);
                roles.add(userRole);
            }*/
    }

    private Role getRole(String r) {
        return roleRepository.findByName(r).orElseThrow(
        ()-> new ApiException(HttpStatus.BAD_REQUEST, "Not such a role"));
    }

    @Override
    public void logout(HttpServletRequest req) {

        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        HttpSession hs = req.getSession();
        hs.invalidate();
    }
}
