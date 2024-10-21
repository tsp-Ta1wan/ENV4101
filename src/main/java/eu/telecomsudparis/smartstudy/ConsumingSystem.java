package eu.telecomsudparis.smartstudy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;
import java.io.FileInputStream;

public class ConsumingSystem extends SmartSystem { 
	
	/**
	 * Percentage of system utilisation saved due to the smart layer.
	 */
	private double timeGainPercentage; 
	
	/**
	 * Percentage of time the system is active.
	 */
	private double activityPercentage; 
	
	/**
	 * The list of the different types of sensors used.
	 */
	private List<Sensor> sensors; 
	
	/**
	 * The corresponding number of sensors.
	 */
	private List<Integer> sensorsNb;
	
	/**
	 * The list of the different types of items used.
	 */
	private List<Item> items; 
	/**
	 * The corresponding number of items.
	 */
	private List<Integer> itemsNb;
	
	/**
	 * The list of the different types of hubs used.
	 */
	private List<Hub> hubs; 
	/**
	 * The corresponding number of hubs.
	 */
	private List<Integer> hubsNb;
	
	/**
	 * The list of the different types of batteries used.
	 */
	private List<Battery> batteries; 
	
	/**
	 * The corresponding number of batteries.
	 */
	private List<Integer> batteriesNb;
	
	/**
	 * The constructor. 
	 * It reads the config.properties file for initialisation.
	 */
	public ConsumingSystem(String propertiesFile) {
		
        Properties properties = new Properties();
        
        try {
            properties.load(new FileInputStream(propertiesFile));
            
            this.smartSystemName = properties.getProperty("SmartSystem");          
            
            this.lifetime = Double.parseDouble(properties.getProperty("systemLifetime"));

            this.timeGainPercentage = Double.parseDouble(properties.getProperty("timeGainPercentage"));            
            this.activityPercentage = Double.parseDouble(properties.getProperty("activityPercentage"));           
            
            this.embodiedType = properties.getProperty("embodied");
            if (embodiedType.equals("greyEnergy")) {
            	this.electricityConversionFactor = 3; //According to the STERM Project, see readme for more details
            }
            else if (embodiedType.equals("CO2")) {
            	//Convert g eq CO2 / kWh to kg eq CO2 / J
            	this.electricityConversionFactor = Double.parseDouble(properties.getProperty("electricityCarbonIntensity")) / 3600000 / 1000;
            }
            else {
            	throw new IllegalArgumentException("Invalid value for embodied: " + embodiedType);
            }
            
            
            String[] sensorsStr = properties.getProperty("sensors").split(",");
            this.sensors = new ArrayList<Sensor>();
            for (String sensor : sensorsStr) {
                this.sensors.add(new Sensor(sensor,propertiesFile));
            }
            String[] sensorsNbStr = properties.getProperty("sensorsNb").split(",");
            this.sensorsNb = new ArrayList<Integer>();
            for (String sensorNb : sensorsNbStr) {
            	this.sensorsNb.add(Integer.parseInt(sensorNb));
            }
            assert	(this.sensors.size() == this.sensorsNb.size());
            

            String[] hubsStr = properties.getProperty("hubs").split(",");
            this.hubs = new ArrayList<Hub>();
            for (String hub : hubsStr) {
                this.hubs.add(new Hub(hub,propertiesFile));
            }
            String[] hubsNbStr = properties.getProperty("hubsNb").split(",");
            this.hubsNb = new ArrayList<Integer>();
            for (String hubNb : hubsNbStr) {
            	this.hubsNb.add(Integer.parseInt(hubNb));
            }
            assert	(this.hubs.size() == this.hubsNb.size());

            
            String[] itemsStr = properties.getProperty("items").split(",");
            this.items = new ArrayList<Item>();
            for (String item : itemsStr) {
                this.items.add(new Item(item,propertiesFile));
            }
            String[] itemsNbStr = properties.getProperty("itemsNb").split(",");
            this.itemsNb = new ArrayList<Integer>();
            for (String itemNb : itemsNbStr) {
            	this.itemsNb.add(Integer.parseInt(itemNb));
            }
            assert	(this.items.size() == this.itemsNb.size());
            
            
            String[] batteriesStr = properties.getProperty("batteries").split(",");
            this.batteries = new ArrayList<Battery>();
            for (String battery : batteriesStr) {
                this.batteries.add(new Battery(battery,propertiesFile));
            } 
            String[] batteriesNbStr = properties.getProperty("batteriesNb").split(",");
            this.batteriesNb = new ArrayList<Integer>();
            for (String batteryNb : batteriesNbStr) {
            	this.batteriesNb.add(Integer.parseInt(batteryNb));
            }
            assert	(this.batteries.size() == this.batteriesNb.size());

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
	}
	

	@Override
	public double maintenanceCost() {
	    Double cost = 0.0;
	    for (int i = 0; i < sensors.size(); i++) {
	    	cost += sensors.get(i).getGhgEmbodied() * sensorsNb.get(i) / sensors.get(i).getLifetime();
	    }
	    for (int i = 0; i < items.size(); i++) {
	    	cost += items.get(i).getGhgEmbodied() * itemsNb.get(i) / items.get(i).getLifetime();
	    }
	    for (int i = 0; i < hubs.size(); i++) {
	    	cost += hubs.get(i).getGhgEmbodied() * hubsNb.get(i) / hubs.get(i).getLifetime();
	    }
	    for (int i = 0; i < batteries.size(); i++) {
	    	cost += batteries.get(i).getGhgEmbodied() * batteriesNb.get(i) / batteries.get(i).getLifetime();
	    }	    

	    return cost;
	}

	@Override
	public double embodiedCost() {
	    Double cost = 0.0;
	    for (int i = 0; i < sensors.size(); i++) {
	    	cost += sensors.get(i).getGhgEmbodied() * sensorsNb.get(i);
	    }
	    for (int i = 0; i < items.size(); i++) {
	    	cost += items.get(i).getGhgEmbodied() * itemsNb.get(i);
	    }
	    for (int i = 0; i < hubs.size(); i++) {
	    	cost += hubs.get(i).getGhgEmbodied() * hubsNb.get(i);
	    }
	    for (int i = 0; i < batteries.size(); i++) {
	    	cost += batteries.get(i).getGhgEmbodied() * batteriesNb.get(i);
	    }	 

	    return cost;
	}
	
	
	@Override
	public double baselinePower() {
		double baselinePower = 0d;
		for (int i = 0; i < items.size(); i++) {
			baselinePower += items.get(i).getPowerActive() * itemsNb.get(i);
		}
		
	    return baselinePower;
	}
	
	@Override
	public double energyOperation() {
		Double energyOperation=0.0;
		int year2seconds = 365 * 24 * 3600;
		
		//Sensors aren't included here because they rely on batteries.
	    for (int i = 0; i < items.size(); i++) {
	    	energyOperation += items.get(i).getPowerSleep() * year2seconds * itemsNb.get(i);
	    }
	    for (int i = 0; i < hubs.size(); i++) {
	    	energyOperation += hubs.get(i).getPowerActive() * year2seconds * hubsNb.get(i);
	    }
	    
		return energyOperation;
	}
	
	@Override
	public double paybackTimetimeGainPercentage(double energyGainPercentage) { 
		//This function changes the parameter timeGainPercentage and then call computePaybackTime()
		
		double temp = this.timeGainPercentage;
		this.timeGainPercentage = energyGainPercentage/100;
		double paybackTime = computePaybackTime();
		this.timeGainPercentage = temp;
		return paybackTime;
	}
	
	@Override
	public double paybackTimeNbItems(double nbItems) {
		//This function changes the parameter nbItems and then call computePaybackTime()
		
		List<Integer> temp = this.itemsNb;
		this.itemsNb.set(0, (int)nbItems);
		double paybackTime = computePaybackTime();
		this.itemsNb = temp;
		return paybackTime;
	}
	
	/**
	 * Compute the paybackTime for the current system parameters.
	 * @return paybackTime;
	 */
	private double computePaybackTime() {
		double energySum=embodiedCost();
		double d1= electricityConversionFactor*timeGainPercentage*activityPercentage*baselinePower()*365*24*3600; //Energy for one year of activity
		double d2= electricityConversionFactor*energyOperation();
		double d3= maintenanceCost();	
		
		// Payback time formula : cost of implementation of the system divided by the energy saved each year.
		// The implementation cost is the embodiedCost.
		// The energy saved each year is the electrical energy economised due to the smart layer, minus the cost of using and maintaining this layer.
		// See Readme.md for more detailed explanations.
		double paybackTime = energySum / (d1-d2-d3);
		
		return paybackTime;
	}

	@Override
	public String toString() {
		return "Smart System : Consuming System \n\ntimeGainPercentage = " + timeGainPercentage + "\nactivityPercentage=" + activityPercentage + "\n\nSensors :\n" + toStringList(sensors) + "\nItems :\n" + toStringList(items) + "\nHubs :\n" + toStringList(hubs) + "\nBatteries :\n"
				+ toStringList(batteries);
	}
	
	
	/**
	 * PrettyPrinter for a list.
	 * @param <T> type
	 * @param list the list to print
	 * @return string of the list
	 */
	public <T> String toStringList(final List<T> list) {
	    StringBuilder builder = new StringBuilder("    ");
	    
	    for (T item : list) {
	        builder.append(item).append("\n    ");
	    }
	    
	    return builder.toString();
	}
	
	public double getActivityPercentage() {
		return activityPercentage;
	}
	
	public double getTimeGainPercentage() {
		return timeGainPercentage;
	}
	
	public int getNbItems() {
		return itemsNb.get(0);
	}
	
	public String displaySetup() {
		return sensorsNb.stream().mapToInt(Integer::intValue).sum() + "#sensors, "
				+ itemsNb.stream().mapToInt(Integer::intValue).sum() + "#items, "
				+ hubsNb.stream().mapToInt(Integer::intValue).sum() + "#hubs";
	}
}
