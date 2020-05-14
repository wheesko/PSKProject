package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.LoginResponseModel;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class AuthenticationService {
    //TODO: shorten this when app goes live
    static final long EXPIRATIONTIME = 900000000;
    static final String SIGNINGKEY = "signingKey";
    static final String BEARER_PREFIX = "Bearer";

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    public void addUserDetails(HttpServletResponse res, Authentication auth) throws IOException {
        //Get context if it is not present
        Object principal = auth.getPrincipal();
        User user = (User) principal;
        UserDTO userData = userService.getUserByEmail(user.getUsername());
        String loginResponseString = new Gson().toJson(getLoginResponse(userData, null));

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(loginResponseString);
        out.flush();
    }

     public void addJWTToken(
        HttpServletResponse response,
        String username,
        Collection<? extends GrantedAuthority> userRole
     ) {

        String JwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .claim("role", userRole)
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
                .compact();
        response.addHeader("Authorization", BEARER_PREFIX + " " + JwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace(BEARER_PREFIX, ""))
                    .getBody()
                    .getSubject();

            Claims claims = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace(BEARER_PREFIX, ""))
                    .getBody();

            ArrayList<LinkedHashMap<String, String>> roles = (ArrayList) claims.get("role");
            String role = roles.get(0).get("authority");

            Collection<? extends GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(role);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            } else {
                throw new RuntimeException("Authentication failed");
            }
        }
        return null;
    }

    private LoginResponseModel getLoginResponse(UserDTO userDTO, Worker worker) {
        LoginResponseModel lr = new LoginResponseModel();
        lr.setEmail(userDTO.getEmail());
        lr.setUserId(userDTO.getId());
        lr.setUserAuthority(userDTO.getUserRole());
        //TODO: add more fields when user -> worker relationship is added
        return lr;
    }
}
