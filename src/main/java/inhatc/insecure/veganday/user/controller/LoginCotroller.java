package inhatc.insecure.veganday.user.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@RestController
public class LoginCotroller {

    @GetMapping("/login")
    public ResponseEntity list(@RequestParam(required = true) String tokenId) throws GeneralSecurityException, IOException{
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
      // Specify the CLIENT_ID of the app that accesses the backend:
      .setAudience(Collections.singletonList("903572796053-1h25l5pim005lmje1htgg9g6t7fluscb.apps.googleusercontent.com"))
      // Or, if multiple clients access the backend:
      //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
      .build();
  
      // (Receive idTokenString by HTTPS POST)
      
      GoogleIdToken idToken = verifier.verify(tokenId);
      if (idToken != null) {
        Payload payload = idToken.getPayload();
      
        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);
      
        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
      
        // Use or store profile information
        // ...
        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, email), HttpStatus.OK);
      } else {
        System.out.println("Invalid ID token.");
        return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_ERROR, null), HttpStatus.OK);
      }
    }
}