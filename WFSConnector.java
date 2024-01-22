package plus.swe.ows;
import java.io.IOException;
import java.util.HashMap;

import org.geotools.api.feature.Feature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.store.ContentFeatureSource;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;

public class WFSConnector {
	
	public static void main(String[] args)
	{
		// define the getCapabilities request
		String wfsGetCapabilitiesURL = "https://data.wien.gv.at/daten/geo?";
		
		// create WFSDataStoreFactory object
		WFSDataStoreFactory wfsDSF = new WFSDataStoreFactory();
		
		// create HashMap and fill it with connection parameters
		HashMap<String, Object> connectionParameters = new HashMap<String, Object>();
		connectionParameters.put(WFSDataStoreFactory.URL.key, wfsGetCapabilitiesURL);
		connectionParameters.put(WFSDataStoreFactory.TIMEOUT.key, 10000000);
		connectionParameters.put(WFSDataStoreFactory.ENCODING.key, "UTF-8");


		// create a DataStore object
		try {
			
			WFSDataStore wfsDS = wfsDSF.createDataStore(connectionParameters);

			String[] typeNames = wfsDS.getTypeNames();
			
			for(int i=0; i<typeNames.length; i++)
			{
				System.out.println("typeName[" + i + "]:" + typeNames[i]);
			}
			
			String typeName = "ogdwien:OEFFHALTESTOGD";
			
			// retrieve the features from the service
			ContentFeatureSource featureSource = wfsDS.getFeatureSource(typeName);
						
			// parse the result/features
			SimpleFeatureCollection featureCollection = featureSource.getFeatures();

			SimpleFeatureIterator featureIterator = featureCollection.features();
			
			while(featureIterator.hasNext())
			{
				// print the features to the screen
				Feature currentFeature = featureIterator.next();

				// for type name ogdwien:OEFFHALTESTOGD
				String line = currentFeature.getProperty("HLINIEN").getValue().toString();				
				String stop = currentFeature.getProperty("HTXTK").getValue().toString();

				if (line.contains("19A"))
				{
					System.out.println(line + ": " + stop);
				} // if				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}//main
}//class



//String firstCharacter = currentFeature.getProperty("city").getValue().toString().substring(0,1);
//
//if (firstCharacter.equals("M"))
//{
//	System.out.println(currentFeature.getProperty("city").getValue());
//	System.out.println(currentFeature.getProperty("students").getValue());
//}