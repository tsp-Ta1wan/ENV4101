package eu.telecomsudparis.smartstudy;

import java.io.IOException;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

public class Main {

	public static void main(String[] args) throws IOException, PythonExecutionException {
		
        if (args.length != 1) {
            System.out.println("Usage: java -jar LCA_IoT-1.0-SNAPSHOT.jar <config_file>");
            System.out.println("Please provide a single argument: the path to the configuration file.");
            return; 
        }
		
		SmartStudy study=new SmartStudy(args[0]);
		
		// You can comment one line to choose to display only one type of curb.
		study.plotTPBtimeGainPercentage();
		study.plotTPBnbItems();
	}
}
