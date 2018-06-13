package daaproj;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class VehMatch {
	private final int id;
	private final int type;
	private final int zipcode;

	public VehMatch(int id, int type, int zipcode) {
		super();
		this.id = id;
		this.type = type;
		this.zipcode = zipcode;
	}

	public void getVehicle(List<Integer> idNew, List<Integer> typeNew,List<Integer> zipcodeNew) throws Exception {

		List<Integer> id = new ArrayList<>();
		List<Integer> type = new ArrayList<>();
		List<Integer> zipcode = new ArrayList<>();
		HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
		int flag = 0;
		final Path path = Paths.get("vehdata.txt");
		final List<VehMatch> parsed;
		try (final Stream<String> lines = Files.lines(path)) {
			parsed = lines.skip(1).map(line -> line.split("\\s*\\|\\s*")).map(line -> {
				final int x = Integer.parseInt(line[0]);
				id.add(x);
				final int y = Integer.parseInt(line[1]);
				type.add(y);
				final int z = Integer.parseInt(line[2]);
				zipcode.add(z);
				return new VehMatch(x, y, z);
			}).collect(Collectors.toList());
		}
		Dijkstra dj = new Dijkstra();
		List<Integer> dist = new ArrayList<>();
		int i = 0, j = 0;
		int distance = 0;
		int value = 0;
		int x = 0;
		int min = 0;
		List<Integer> vehicleDist = new ArrayList<>();
		List<Integer> vehicleIds = new ArrayList<>();
		int typeOld = 0;
		for (i = 0; i < typeNew.size(); i++) {

			if (hmap.size() > 1 && typeNew.get(i) != typeOld && flag > 0) {
				vehicleIds.add(value);
				dist.add(min);
				vehicleDist = new ArrayList<>();
//				System.out.println(value + "Value");
				flag = 0;

			}
			int a = typeNew.get(i);
			for (j = 0; j < type.size(); j++) {
				int b = type.get(j);
				if (a == (b)) {
					if (zipcodeNew.get(i).equals(zipcode.get(j))) {
						System.out.println("your requested connected vehicles : ");
						System.out.println(id.get(j) + "   is Id Of Vehicle of type: " + type.get(j) + " and zip code: " +zipcode.get(j));
						System.out.println("****");
						vehicleIds.add(id.get(i));
						dist.add(0);
						break;
					} else if (zipcodeNew.get(i) != (zipcode.get(j))) {
						String Start = zipcode.get(j).toString();
						String End = zipcodeNew.get(i).toString();
						distance = dj.getDistance(Start, End);
						hmap.put(distance, id.get(j));

						vehicleDist.add(distance);

						min = vehicleDist.get(0);
						for (x = 0; x < vehicleDist.size(); x++) {
							int number = vehicleDist.get(x);

							if (number < min) {
								min = number;
							}
						}
						if (x > 0) {
							System.out.println("****");
//							System.out.println("hmap Min " + hmap.get(min));
							value = hmap.get(min);
							typeOld = typeNew.get(i);
							System.out.println("through ID:"+value + " we reach ");
							System.out.println("****");

							flag++;
						}
					}
				}
			}
		}
		ResultFile out = new ResultFile();
		out.num = idNew;
		out.vId = vehicleIds;
		out.vType = typeNew;
		out.vZip = zipcodeNew;
		out.distance = dist;
		out.getFileOutput();
	}

}
