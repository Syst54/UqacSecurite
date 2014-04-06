package localisationInformations;

import java.util.List;

import android.content.Context;
import android.location.*;
import android.util.Log;

public class Localisation {
	
	private double lat;
	private double lon;
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public Localisation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void _getLocation(Context context) {
	    // Get the location manager
	    LocationManager locationManager = (LocationManager) 
	            context.getSystemService(Context.LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    String bestProvider = locationManager.getBestProvider(criteria, true);
	    Location location = locationManager.getLastKnownLocation(bestProvider);
	    try {
	        this.lat = location.getLatitude();
	       this.lon = location.getLongitude();
	        Log.d("pos","lat :"+lat+"lon :"+lon);
	    } catch (NullPointerException e) {
	        this.lat = -1.0;
	        this.lon = -1.0;
	        Log.d("posOther","lat :"+lat+"lon :"+lon);
	    }
	}
	    public boolean getGPS(Context context) {
	    	 LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	    	 List<String> providers = lm.getProviders(true);

	    	 Location l = null;

	    	 for (int i=providers.size()-1; i>=0; i--) {
	    	  l = lm.getLastKnownLocation(providers.get(i));
	    	  if (l != null) break;
	    	 }

	    	 
	    	 if (l != null) {
	    	 this.lat= l.getLatitude();
	    	  this.lon= l.getLongitude();
	    	  Log.d("posGPS",lat+" "+lon);
	    	  return true;
	    	 }
	    	 else
	    	 return false;

	    	 //return gps;
	    	}
	    public void getBestLocalisation(Context context){
	    	 
	    	if(getGPS(context))
	    		
	    	{}
	    	else
	    	_getLocation(context);
	    	
	    }
}
