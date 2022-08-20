package inhatc.insecure.veganday.user.controller;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.user.service.UserService;

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
  
	
    @Value("${google.id}")
    private String clientId;
    
    @CrossOrigin(origins="*")
    @PostMapping("/login")
    public ResponseEntity<ResponseFmt<String>> list(@RequestParam(required = true) String tokenId) throws GeneralSecurityException, IOException, JSONException{
      System.out.println(tokenId);
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
      .setAudience(Collections.singletonList(clientId))
      .build();
      
      GoogleIdToken idToken = verifier.verify(tokenId);
      if (idToken != null) {
        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        
        JSONObject uData = new JSONObject();
        uData.put("email" , email);
        uData.put("name" , name);
        
		UserService userService = new UserService(null);
		userService.create(userId, name, email);
//		if(userService.findById(userId).isPresent()) {
//			
//		}
          
        System.out.println("email: " + email + " name: " + name);
        return new ResponseEntity<ResponseFmt<String>>(ResponseFmt.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, uData.toString()), HttpStatus.OK);
      } else {
        System.out.println("Invalid ID token.");
        return new ResponseEntity<ResponseFmt<String>>(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_ERROR, null), HttpStatus.OK);
      }
    }
}