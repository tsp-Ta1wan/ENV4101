package eu.telecomsudparis.smartstudy;

import java.util.List;

public class Material {
	
	/**
	 * Lifespan of the material (in years).
	 */
	protected float lifetime; 
	
	/**
	 * Embodied energy (kg eq CO2 or MJ, depending on the value of embodied).
	 */
	protected float ghgEmbodied; 

	@Override
	public String toString() {
		return "Material [lifetime=" + lifetime + ", ghgEmbodied=" + ghgEmbodied + "]";
	}
	
	/**
	 * Getter ghgEmbodied.
	 * @return ghgEmbodied.
	 */
	public float getGhgEmbodied() {
		return ghgEmbodied;
	}
	
	/**
	 * Getter lifetime.
	 * @return lifetime.
	 */
	public float getLifetime() {
		return lifetime;
	}
	
}
