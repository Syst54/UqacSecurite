package com.example.spyapp;

import android.app.DownloadManager.Request;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;

public class Facebook {

	/*private Session.StatusCallback fbStatusCallback = new Session.StatusCallback() {
	    public void call(Session session, SessionState state, Exception exception) {
	        if (state.isOpened()) {
	            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	                public void onCompleted(GraphUser user, Response response) {
	                    if (response != null) {
	                        // do something with <response> now
	                        try{
	                            get_id = user.getId();
	                            get_name = user.getName();
	                            get_gender = (String) user.getProperty("gender");
	                            get_email = (String) user.getProperty("email");
	                            get_birthday = user.getBirthday();
	                            get_locale = (String) user.getProperty("locale");
	                            get_location = user.getLocation().toString();   

	                        Log.d(LOG_TAG, user.getId() + "; " +  
	                            user.getName() + "; " +
	                            (String) user.getProperty("gender") + "; " +        
	                            (String) user.getProperty("email") + "; " +
	                            user.getBirthday()+ "; " +
	                            (String) user.getProperty("locale") + "; " +
	                            user.getLocation());
	                        } catch(Exception e) {
	                             e.printStackTrace();
	                             Log.d(LOG_TAG, "Exception e");
	                         }

	                    }
	                }
	            });
	        }
	    }
	};*/
}
