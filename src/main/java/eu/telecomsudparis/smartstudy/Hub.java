package eu.telecomsudparis.smartstudy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Hub extends ConsumingMaterial {
	
	/**
	 * The constructor, it reads the config.properties file for initialisation.
	 * @param id of the hub initialised.
	 */
	public Hub(final String id, final String propertiesFile) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFile));
            
            /*
             * Useless in this implementation, residual args from the ConsumingMaterial implementation.
             * Because according to the STERM model annex, hubs never enter sleep mode.
             */
            this.powerSleep = 0;
            
            try { 
            	this.powerActive = Float.parseFloat(properties.getProperty("hub." + id + ".powerActive"));
            } catch (NullPointerException | NumberFormatException e) {
            	System.out.println("Following error on : " + "hub." + id + ".powerActive");
            	e.printStackTrace();
            }
            
            try { 
            	this.ghgEmbodied = Float.parseFloat(properties.getProperty("hub." + id + ".GHG_embodied"));
            } catch (NullPointerException | NumberFormatException e) {
            	System.out.println("Following error on : " + "hub." + id + ".GHG_embodied");
            	e.printStackTrace();
            }
            
            try { 
            	this.lifetime = Float.parseFloat(properties.getProperty("hub." + id + ".lifetime"));
            } catch (NullPointerException | NumberFormatException e) {
            	System.out.println("Following error on : " + "hub." + id + ".lifetime");
            	e.printStackTrace();
            }

            
            // Convert to the good unit :
            // If embodied = greyEnergy, the calculations are made in J, so you have to convert MJ to J
            // If embodied = CO2, the calculations are made in kg eq CO2, so no conversion is needed
            try { 
            	String embodied = properties.getProperty("embodied");
            	if (embodied.equals("greyEnergy")) { //Convert MJ to J
            		this.ghgEmbodied = this.ghgEmbodied*1000000;
            	}
            } catch (NullPointerException | NumberFormatException e) {
            	System.out.println("Following error on : embodied");
            	e.printStackTrace();
            }
            
            	
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
	}

	@Override
	public String toString() {
		return "Hub [powerSleep=" + powerSleep + ", powerActive=" + powerActive + ", lifetime=" + lifetime
				+ ", ghgEmbodied=" + ghgEmbodied + "]";
	}	
	
}
