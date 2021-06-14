package com.br.smallmanager.apismallManager.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;
import com.amazonaws.util.IOUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3StorageService {
	 	@Value("${application.bucket.name}")
	    private String bucketName;

	    @Autowired
	    private AmazonS3 s3Client;
	    
	    public String uploadFile(MultipartFile file) {
	    	
	    	if (file != null) {
	    		File fileObj = convertMultiPartFileToFile(file);
	 	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	 	        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
	 	        fileObj.delete();
	 	        return fileName;
	    	}else {
	    		return null;
	    	}
	       
	    }
	    
	    public byte[] downloadFile(String fileName) {
	        S3Object s3Object = s3Client.getObject(bucketName, fileName);
	        
	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
	        try {
	            byte[] content = IOUtils.toByteArray(inputStream);
	            return content;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public String deleteFile(String fileName) {
	    	 S3Object s3Object = s3Client.getObject(bucketName, fileName);
	    	 
	    	 if (s3Object != null){
	    		  s3Client.deleteObject(bucketName, fileName);
	    	 }
	      
	        return fileName;
	    }
 
	    private File convertMultiPartFileToFile(MultipartFile file) {
	        File convertedFile = new File(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	             
	        }
	        return convertedFile;
	    }
}
