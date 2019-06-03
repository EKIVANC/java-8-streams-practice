package exercises;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
	public List<String> orderedJunctionBoxes(int numberOfBoxes, List<String> boxList) {

		Map<String, String> oldGenerations = new HashMap<>();
		Map<String, String> newGenerations = new LinkedHashMap<>();

		for (String box : boxList) {

			String[] tokens = box.split("\\s+");
			try {
				Integer.parseInt(tokens[1]);
				newGenerations.put(tokens[0], box.substring(tokens[0].length()));
			} catch (NumberFormatException ex) {
				oldGenerations.put(tokens[0], box.substring(tokens[0].length()));
			}

		}
		
		// sort the old oldGenerations
		
		Map<String, String> collectTestSorted = oldGenerations.entrySet().stream().sorted(
				(

						(c1, c2) -> {
							int result = c1.getValue().compareTo(c2.getValue());
							result = result *-1;
							return result;
						}



						)
				).collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));
		
		
		Map<String,String> oldInOrder =
				collectTestSorted.entrySet().parallelStream()
			       .sorted(
			    		   (c1,c2)-> {
			    			   int tmp = c1.getValue().compareTo(c2.getValue());
			    			   if(tmp == 0) {
			    				   tmp = c1.getKey().compareTo(c2.getKey());
			    			   }
			    			   return tmp;
			    		   }
			    		   )
			       .collect(Collectors.toMap(
			          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		
		
		List<String> retVal = new ArrayList<String>();
		
		for (Map.Entry<String, String> entry : oldInOrder.entrySet()) {
			retVal.add(entry.getKey() + " "+ entry.getValue() );
		}
		
		for (Map.Entry<String, String> entry : newGenerations.entrySet()) {
			retVal.add(entry.getKey() + " "+ entry.getValue() );
		}
		
		 return retVal;
		
	}
}

public class Main {
	public static void main(String[] args) {

		Solution s = new Solution();

		List<String> boxList = new ArrayList<String>();

		boxList.add("ykc 82 01");
		boxList.add("eo first qpx");
		boxList.add("09z cat hamster");
		boxList.add("06f 12 25 6");
		boxList.add("az0 first qpx");
		boxList.add("236 cat dog rabbit snake");

		s.orderedJunctionBoxes(6, boxList);

	}

}
