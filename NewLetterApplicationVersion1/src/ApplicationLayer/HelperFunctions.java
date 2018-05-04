package ApplicationLayer;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelperFunctions {
	
   public ArrayList<Number> cosinesimilarity= new ArrayList<>();


	public ArrayList<Number> calculateCosineSimilarity(Map<String,Number>listOfuserprofile,ArrayList<HashMap<String,Number>>listOfnewsprofile)

	{    
		for(int i=0;i<listOfnewsprofile.size();i++)
		{Map<String,Number> listOfANewsProfile=new HashMap<String,Number>();
		  listOfANewsProfile=listOfnewsprofile.get(i);
		 Set<String> intersection=getIntersection(listOfuserprofile,listOfANewsProfile);
		 double  dotproduct=findDotProduct(listOfuserprofile,listOfANewsProfile,intersection);
		// System.out.println("dot : " + dotproduct);
		 double cosineSimilarity=findCosineSimilarity(dotproduct,listOfANewsProfile,listOfuserprofile);
		 cosinesimilarity.add(cosineSimilarity);
		 
			
		}
		
		System.out.println("cosinesimilarity of two vectors :" +cosinesimilarity);	
		
		 return cosinesimilarity;
	}

	private Set<String> getIntersection(Map<String, Number> listOfuserprofile,Map<String, Number> listOfANewsProfile)
	{    Set<String> intersection = new HashSet<>(listOfANewsProfile.keySet());
    intersection.retainAll(listOfuserprofile.keySet());
    return intersection;
		
	
	
	
	}
	private double findDotProduct(Map<String, Number> listOfuserprofile,Map<String, Number> listOfANewsProfile,Set<String> intersection) 
	{ double dotProduct = 0;
    for (final String key : intersection) {
    	//System.out.println("    1   "+ listOfuserprofile.get(key).doubleValue());
    	//System.out.println("     2   "+ listOfANewsProfile.get(key).doubleValue());
//    	double one = listOfuserprofile.get(key).doubleValue();
//    	double TWO = listOfANewsProfile.get(key).doubleValue();
//    	System.out.println("     3   "+ one + "  " + TWO + "   " + (one*TWO));
//    	double value = one*TWO;
//    	DecimalFormat df=new DecimalFormat("0.00");
//    	String formate = df.format(value); 
//    	double finalValue;
//		try {
//			finalValue = df.parse(formate).doubleValue();
//			System.out.println(finalValue);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
        dotProduct += listOfuserprofile.get(key).doubleValue()* listOfANewsProfile.get(key).doubleValue();
    }
    return dotProduct;
		
	}
	private double findCosineSimilarity(double dotproduct,Map<String, Number> listOfANewsProfile,Map<String, Number> listOfuserprofile )
	{
		if (listOfANewsProfile == null || listOfuserprofile == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }

       
        double d1 = 0.0d;
        for (final Number value : listOfANewsProfile.values()) {
            d1 += Math.pow(value.doubleValue(), 2);
        }
        double d2 = 0.0d;
        for (final Number value : listOfuserprofile.values()) {
            d2 += Math.pow(value.doubleValue(), 2);
        }
        double cosineSimilarity;
//        if (d1 <= 0.0 || d2 <= 0.0) {
//            cosineSimilarity = 0.0;
//        } else {
            cosineSimilarity = dotproduct / (Math.sqrt(d1) * Math.sqrt(d2));
//   /     }
        return cosineSimilarity;
	}
}	
	
	    		
