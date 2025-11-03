package util;

public class Function  {
	
	public static double getNumeric(String s){
		if (s.equals("")) return 0;
		try {
			if (Double.isNaN(Double.parseDouble(s))) return 0;
			else return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return 0;
		}		
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getNumeric("xxx"));
	}

}
