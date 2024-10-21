package eu.telecomsudparis.smartstudy;

import java.util.List;

/**
 * Abstract class for the studied system, implemented by ConsumingSystem.
 */
public abstract class SmartSystem {
	
	/**
	 * Name of the smartSystem.
	 * Displayed on the graph.
	 */
	protected String smartSystemName;
	
	/**
	 * The conversion factor for electricity.
	 * To convert electricity to primary energy, this factor should be equal to 3 (according to the STERM project)
	 * To convert electricity to eq CO2, this factor should be equal to the electricityCarbonIntensity (kgCO2/J).
	 */
	protected double electricityConversionFactor; 
	
	/**
	 * Global lifetime of the system, only used in graphics to draw the horizontal line.
	 */
	protected double lifetime;
	
	/**
	 * Corresponds to the embodied parameter of the .properties file.
	 * Only use : displayed on the graph.
	 */
	protected String embodiedType;
	
	/**
	 * Using the system will induce maintenance costs.
	 * Each material in the system has a lifespan and a embodied cost.
	 * We consider that each material has to be replaced when reaching the end of its lifespan.
	 * 
	 * This global maintenance cost function is the sum of GHG_embodied over lifespan for each material;
	 * 
	 * @return energy cost to  maintain the system for one year, in joules.
	 */
	abstract double maintenanceCost();
	
	/**
	 * Sums the embodied cost for each material.
	 * @return embodied cost of the system, in joules.
	 */
	abstract double embodiedCost();
	
	/**
	 * Power consumed by the system when all items are active.
	 * @return baselinePower, in Watts.
	 */
	abstract double baselinePower();
	
	/**
	 * Energy consumed by the smart layer during one year of use.
	 * @return ernergy cost of smart layer operation for one year, in Joules.
	 */
	abstract double energyOperation();
	
	/**
	 * Compute paybackTime for the given timeGainPercentage;
	 * @param timeGainPercentage.
	 * @return paybackTime for the given timeGainPercentage.
	 */
	abstract double paybackTimetimeGainPercentage(double timeGainPercentage);
	
	/**
	 * Compute paybackTime for the given nbItems;
	 * @param nbItems.
	 * @return paybackTime for the given nbItems.
	 */
	abstract double paybackTimeNbItems(double nbItems);
	
	abstract double getActivityPercentage();
	
	abstract double getTimeGainPercentage();
	
	abstract String displaySetup();
	
	abstract int getNbItems();
}
