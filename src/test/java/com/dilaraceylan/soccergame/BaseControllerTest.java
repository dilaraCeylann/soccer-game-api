package com.dilaraceylan.soccergame;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.dilaraceylan.soccergame.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
public class BaseControllerTest {

    public static String APPLICATION_JSON = "application/json";

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @BeforeEach
    void printTestStart(TestInfo testInfo) {
        log.info(String.format("TEST STARTED: [%s]", testInfo.getDisplayName()));
    }

    @AfterEach
    void printTestFinish(TestInfo testInfo) {
        log.info(String.format("TEST FINISHED: [%s]", testInfo.getDisplayName()));
    }

    public String loadFile(String jsonFile) throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = FileUtils.toFile(classLoader.getResource(jsonFile));
        assert file != null;
        return FileUtils.readFileToString(file, "UTF-8");
    }

    public RequestPostProcessor postHeaders() {

        return request -> {
            request.addHeader("Accept", APPLICATION_JSON);
            request.addHeader("Content-Type", APPLICATION_JSON);
            request.addHeader("Authorization", generateToken());
            return request;
        };
    }

    /***
     * Adds Quotation Mark
     * 
     * @param text text
     * @return String
     */
    public String addQuat(String text) {
        return "\"" + text + "\"";
    }

    public String addEmptyQuat() {
        return addQuat("");
    }

    public String generateToken() {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("test-user@gmail.com", 123456));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        return "Bearer " + jwt;
    }
}
