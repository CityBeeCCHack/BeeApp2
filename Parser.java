import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Parser {
	String URL;
	public Parser(String url) {
		this.URL = url;
	}
	
	private ArrayList<String> read(String type){
      StringBuilder build = new StringBuilder();
  	try {
	        URL url = new URL(URL);
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	        try{
	           InputStream in = url.openStream();
	           BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	           String line;
	           while((line = reader.readLine()) != null){
	               build.append(line+ " ");
	           }
	        }  finally {
	            urlConnection.disconnect();
	        }
	  }
  		catch(Exception e) {e.printStackTrace();}
  		ArrayList<String> arr = new ArrayList<String>();
  		
  	
  		switch(type) {
  		
  		case "Temperature":
  			for(String s : build.toString().split(" ")) {
  				arr.add(s.split(",")[0]+":"+s.split(",")[1]);
  			}
  			break;
  		case "Humidity":
  			for(String s : build.toString().split(" ")) {
  				arr.add(s.split(",")[0]+":"+s.split(",")[3]);
  			}
  			break;
  		case "Pressure":
  			for(String s : build.toString().split(" ")) {
  				arr.add(s.split(",")[0]+":"+s.split(",")[2]);
  			}
  			break;
  		}
      	return arr;
  }
    
    
    public ArrayList<String> getTemperature() {
    	return read("Temperature");
    }
    
    public ArrayList<String> getHumidity() {
    	return read("Humidity");
    }
    
    public ArrayList<String> getPressure() {
    return read("Pressure");
    }
    
    
    public static void main(String[] args) {
		Parser p = new Parser("http://192.168.88.161:1337/disconnect/sensors");
		for(String s : p.getPressure()) {
			System.out.println(s);
		}
	}
}
