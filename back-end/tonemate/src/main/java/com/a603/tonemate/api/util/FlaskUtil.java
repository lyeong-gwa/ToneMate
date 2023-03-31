package com.a603.tonemate.api.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlaskUtil {
    @Value("${FLASK_DOMAIN}")
    private String FLASK_DOMAIN;

    public Map<String, Object> requestTimbre(MultipartFile file) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
	      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	      body.add("file_wav", new ByteArrayResource(file.getBytes()) {
	          @Override
	          public String getFilename() {
	              return "file_wav";
	          }
	      });

	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

	      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(FLASK_DOMAIN+"/timbre", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>(){});

	      if(response.getStatusCode() == HttpStatus.OK) {
	    	  Map<String, Object> result = response.getBody();
	    	  System.out.println(result);
	          System.out.println(result.get("similaritypercent"));
			  return result;
	      } else {
	          System.out.println("Error: " + response.getStatusCodeValue());
	      }
		return null;
	}


}
