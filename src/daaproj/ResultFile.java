package daaproj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultFile {
	List<Integer> num = new ArrayList<>();
	List<Integer> vType = new ArrayList<>();
	List<Integer> vZip = new ArrayList<>();
	List<Integer> vId = new ArrayList<>();
	List<Integer> distance = new ArrayList<>();
	
	public void getFileOutput() throws IOException {

	File file = new File("C:\\Users\\snehadidigam\\Documents\\DAA\\Results");
	
		file.mkdirs();
		File res = new File(file, "resultfile.txt");
		
		FileWriter fw = new FileWriter(res);
		fw.write(String.format("%20s %20s %20s %20s %20s \r\n", "S.No","VehicleType", "Vehicle Zipcode", "Vehicle ID",
				 "Distance"));
		
		for (int i = 0; i < num.size(); i++) {
			
			fw.write(String.format("%20d %20d %20d %20d %20d \r\n", num.get(i), vType.get(i), vZip.get(i), vId.get(i), distance.get(i)));
		}
		fw.flush();
		fw.close();
	}

}
