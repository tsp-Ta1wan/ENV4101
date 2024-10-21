package eu.telecomsudparis.smartstudy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ScaleBuilder;

/**
 * System interface.
 */
public class SmartStudy {

	/**
	 * The studied SmartSystem.
	 */
	private SmartSystem smartSystem;
	
	/**
	 * SmartStudy constructor.
	 * 
	 * @return SmartStudy initialised with a ConsumingSystem
	 */
	public SmartStudy(final String propertiesFile) {
		this.smartSystem = new ConsumingSystem(propertiesFile);
	}
	
	/**
	 * Plot function, paybackTime in years depending on different timeGainPercentage.
	 * 
	 * @throws IOException errors from plot.show()
	 * @throws PythonExecutionException errors from plot.show()
	 */
	public void plotTPBtimeGainPercentage() throws IOException, PythonExecutionException {
		double maxThreshold = 100; //Avoid nonsense data and graphs errors by limiting the paybackTime to 100 years.
		
		List<Double> timeGainPercentageList=NumpyUtils.linspace(0, 100, 1001); //TimeGainPercentage range, increasing by 0.1 each. 
		ArrayList<Double> paybackTimeList = new ArrayList<Double>();
		double paybackTime;
		
		for (double timeGainPercentage : timeGainPercentageList) { //For each percentage, we compute the paybackTime and add it to the list (with normal, errorDown and errorUp).
			paybackTime = smartSystem.paybackTimetimeGainPercentage(timeGainPercentage);
			
			paybackTimeList.add((paybackTime > 0 && paybackTime < maxThreshold) ? paybackTime : maxThreshold);
		}
				
		//creates a constant list to draw a horizontal line with y = smartSystem.lifetime;
		List<Double> systemLifetime = timeGainPercentageList.stream().map(alpha-> smartSystem.lifetime).collect(Collectors.toList());
		
		Plot plt = Plot.create();
		plt.figure("System " + smartSystem.smartSystemName + ": Payback time by gain for " + smartSystem.embodiedType);
	

		plt.yscale(ScaleBuilder.Scale.log);//we use the logarithmic scale
		
		plt.xlim(0, 100);//we limit the value of x to have a better view of the graph
		
		double minPaybackTime = paybackTimeList.get(1000);
		
		double yLowerLimit = Math.min(1, minPaybackTime * 0.5);
		plt.ylim(yLowerLimit, 50);//we limit the value of y to have a better view of the graph
		plt.title("System " + smartSystem.smartSystemName + ": Payback time by gain for " + smartSystem.embodiedType 
				 + "\\n Activity percentage = " + smartSystem.getActivityPercentage() *100 
				+ "%  \\n Setup = " + smartSystem.displaySetup());
		
		plt.xlabel("timeGain [%]");
		plt.ylabel("PaybackTime [Years]");
		
		drawGrid(plt,0,100,yLowerLimit,50,10,2);
		
		plt.plot().add(timeGainPercentageList, paybackTimeList, "#3120de").label("Payback time"); //draw the payback time
		plt.plot().add(timeGainPercentageList, systemLifetime, "#ff00ff").label(smartSystem.lifetime + " years System lifetime").linestyle("-");
		
		plt.legend();
		plt.savefig("Results/"+smartSystem.smartSystemName+"-PBT-ByGain-"+smartSystem.embodiedType+".png").dpi(200);
		plt.show();
	}
	
	/**
	 * Plot function, paybackTime in years depending on different item numbers.
	 * 
	 * @throws IOException errors from plot.show()
	 * @throws PythonExecutionException errors from plot.show()
	 */
	public void plotTPBnbItems() throws IOException, PythonExecutionException {
		double maxThreshold = 100; //Avoid nonsense data and graphs errors by limiting the paybackTime to 100 years.
		
		int itemsNbx2 = smartSystem.getNbItems() * 2;
		itemsNbx2 = (itemsNbx2 < 50) ? 50 : itemsNbx2;
		
		List<Double> nbItemsList = NumpyUtils.linspace(1, itemsNbx2, itemsNbx2); //nbItems from 1 to 50, because we consider that 50 items is the limit for 1 hub
		ArrayList<Double> paybackTimeList = new ArrayList<Double>();
		double paybackTime;
		
		for (double nbItem : nbItemsList) { //For each nbItem, we compute the paybackTime and add it to the list (with normal, errorDown and errorUp).
			paybackTime = smartSystem.paybackTimeNbItems(nbItem);
			
			paybackTimeList.add((paybackTime > 0 && paybackTime < maxThreshold) ? paybackTime : maxThreshold);
		}
				
		//creates a constant list to draw a horizontal line with y = smartSystem.lifetime;
		List<Double> systemLifetime = nbItemsList.stream().map(alpha-> smartSystem.lifetime).collect(Collectors.toList());
		
		Plot plt = Plot.create();
		plt.figure("System " + smartSystem.smartSystemName + ": Payback time by number of items for " +  smartSystem.embodiedType );

		plt.yscale(ScaleBuilder.Scale.log);//we use the logarithmic scale
		
		plt.xlim(0, itemsNbx2);//we limit the value of x to have a better view of the graph
		
		double minPaybackTime = paybackTimeList.get(itemsNbx2-1);
		
		double yLowerLimit = Math.min(1, minPaybackTime * 0.5);
		plt.ylim(yLowerLimit, 50);//we limit the value of y to have a better view of the graph
		
		drawGrid(plt,0,itemsNbx2,yLowerLimit,50,itemsNbx2/10,2);
		
		plt.plot().add(nbItemsList, paybackTimeList, "#3120de").label("Payback time"); //draw the payback time
		plt.plot().add(nbItemsList, systemLifetime, "#ff00ff").label(smartSystem.lifetime + " years System lifetime").linestyle("-");
		
		plt.title("System " + smartSystem.smartSystemName + ": Payback time by number of items for " +  smartSystem.embodiedType + 
				"\\n Time gain percentage = " + smartSystem.getTimeGainPercentage() * 100 + "% ; Activity percentage = " + smartSystem.getActivityPercentage() * 100 + "%");
		
		plt.xlabel("Number of smart items [/]");
		plt.ylabel("PaybackTime [Years]");
		
		plt.legend();
		plt.savefig("Results/"+smartSystem.smartSystemName+"-PBT-ByNbItems-"+smartSystem.embodiedType+".png").dpi(200);
		plt.show();
	}
	
	
	/**
	 * This function is made to draw a grid on the plot.
	 * The matplotlib for Java we use is rudimentary and doesn't include the function plt.grid(), so this is a simple function to draw a grid.
	 * @param plt the Plot instance.
	 * @param xStart x start point
	 * @param xEnd x end point
	 * @param yStart y start point
	 * @param yEnd y end point
	 * @param xSpacing space between each vertical line
	 * @param yFactor space between each horizontal line (adapted to log scale)
	 */
	private void drawGrid(Plot plt, int xStart, int xEnd, double yStart, int yEnd, int xSpacing, double yFactor) {
		// Draw vertical grid lines
		for (int x = xStart; x <= xEnd; x += xSpacing) {
		    List<Number> xValues = Arrays.asList((double)x, (double)x);
		    List<Number> yValues = Arrays.asList((double)yStart, (double)yEnd);
		    plt.plot().add(xValues, yValues, "#d0d0d0").linestyle("--");
		}

		double y = yStart;
		while (y <= yEnd) {
		    List<Number> xValues = Arrays.asList((double)xStart, (double)xEnd);
		    List<Number> yValues = Arrays.asList(y, y);
		    plt.plot().add(xValues, yValues, "#d0d0d0").linestyle("--");
		    
		    y *= yFactor; // Move to the next grid line position
		}
	}
}
