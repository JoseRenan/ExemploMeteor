package br.edu.test.service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.DDPClient.DdpMessageField;
import com.keysolutions.ddpclient.DDPListener;

@Path("test")
public class TestService {
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendPost(String post) {
		
		System.out.println(post);
		
		try {
			DDPClient ddp = new DDPClient("localhost", 3000);
			ddp.connect();
		
			Thread.sleep(500);
			
			Map<String,Object> options = new HashMap<String,Object>();
	        options.put("text", post);
			
	        Boolean connected = true;
	        
			ddp.collectionInsert("posts", options, new DDPListener() {
				@Override
				public void onResult(Map<String, Object> resultFields) {
					super.onResult(resultFields);
					if (resultFields.containsKey(DdpMessageField.ERROR)) {
			            Map<String, Object> error = (Map<String, Object>) resultFields.get(DdpMessageField.ERROR);
			            String errorReason = (String) error.get("reason");
			            System.err.println("Login failure: " + errorReason);
			        } else {
			        	System.err.println("Funcionou");
			        }
			    }
			});
		
		} catch (URISyntaxException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
