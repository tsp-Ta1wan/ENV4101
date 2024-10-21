package eu.telecomsudparis.smartstudy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConsumingMaterial extends Material {
	
	/**
	 * Power consumed by the material in standby mode (in W).
	 */
	protected float powerSleep; 
	
	/**
	 * Power consumed by the material in active mode (in W).
	 */
	protected float powerActive; 
	
	/**
	 * Default constructor.
	 */
	public ConsumingMaterial() {
		
	}
	
	/**
	 * Getter powerActive.
	 * @return powerActive
	 */
	public float getPowerActive() {
		return powerActive;
	}
	
	/**
	 * Getter powerSleep.
	 * @return powerSleep.
	 */
	public float getPowerSleep() {
		return powerSleep;
	}
	
}
