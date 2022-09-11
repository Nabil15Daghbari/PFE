package com.nabil.SystemRecrutement.serviceImpl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.nabil.SystemRecrutement.service.flickrService;



@Service
public class flickerServiceImpl  implements flickrService {
	    
	
	
	
	
	    @Value("${flickr.apiKey}")
		private String apiKey ;
	    
	    @Value("${flickr.apiSecret}")
	    private String apiSecret ;
	    
	    
	    @Value("${flickr.appKey}")
	    private String appKey ;
	    
	    @Value("${flickr.appSecret}")
	    private String appSecret ;
	    
	  //  private Flickr flickr ;
	    
	
	
	//    @Autowired
	//    public flickerServiceImpl(Flickr flickr  ) {
	//		this.flickr = flickr ;
	//		
	//	}

	    
	    
	    
	    
	    
	
	@Override
	public String savePhoto(InputStream photo, String title)  throws FlickrException{
		
		 Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
		    Auth auth = new Auth();
		    auth.setPermission(Permission.DELETE);
		    auth.setToken(appKey);
		    auth.setTokenSecret(appSecret);
		  
		    
		    RequestContext requestContext = RequestContext.getRequestContext();
		    requestContext.setAuth(auth);
		    flickr.setAuth(auth);

		
		
		
		
		
		UploadMetaData uploadMetaData = new UploadMetaData();
		uploadMetaData.setTitle(title);
		
		String photoId = flickr.getUploader().upload(photo, uploadMetaData);
		
		
		
		
		return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
		
	}


	
	
	
}
