package wmh.dao;

import java.util.Calendar;
import java.util.Random;


/**
 * A set of static metods
 * @author pko
 *
 */
public class Const {

	private static Random r;
	//private static double variance = 5;
/**
 * Genrate randomness
 * @param what
 * @return
 */
	public static boolean check(int what) {
		//r=null;
		if (what == 100)
			return true;
		if (r == null)
			r = new Random(Calendar.getInstance().getTimeInMillis());
		int tmp  = /*  Const.getGaussian(what, variance);*/ showRandomInteger(0, 100, r)  ;
		if (tmp > what)
			return false;
		return true;

	}

	private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		
		return randomNumber;
	}
	  @SuppressWarnings("unused")
	private static double getGaussian(double aMean, double aVariance){
	      return aMean-1 + r.nextGaussian() * aVariance;
	  }
	  
	  static final String ZEROES = "000000000000";
		static final String BLANKS = "            ";
		
		public static String format( double val, int n, int w) 
		{
		//	rounding			
			double incr = 0.5;
			for( int j=n; j>0; j--) incr /= 10; 
			val += incr;
			
			String s = Double.toString(val);
			int n1 = s.indexOf('.');
			int n2 = s.length() - n1 - 1;
			
			if (n>n2)      s = s+ZEROES.substring(0, n-n2);
			else if (n2>n) s = s.substring(0,n1+n+1);

			if( w>0 & w>s.length() ) s = BLANKS.substring(0,w-s.length()) + s;
			else if ( w<0 & (-w)>s.length() ) {
				w=-w;
				s = s + BLANKS.substring(0,w-s.length()) ;
			}
			return s;
		}	


}
