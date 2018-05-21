import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

@Path("ZipCodeService")
public class ZipCodeService {
	
	Map<String, Map<String, String>> codeMapper = new HashMap<String, Map<String, String>>();
	Map<String, String> cityMap = new HashMap<String, String>();
	public ZipCodeService() {
		
		cityMap.put("22312", "Alexandria,VA");
		cityMap.put("22030", "Fairfax,VA");
		cityMap.put("22301", "Tysons Corner,MD");
		cityMap.put("20148", "Ahsburn,VA");
	}
	
	@GET
	@Path("/getCityState/{zipCode}")
	@Produces("text/plain")
	public String getCityState(@PathParam("zipCode") String zipCode)
	{	
		return cityMap.get(zipCode);
	}

}
