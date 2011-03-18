package wmh.beans;



//import java.util.Calendar;
//import java.util.Random;
import gis.dijkstra.Edge;
/**
 * Connection for WMH project - contains source and destination switches, the weight of the connection and current status
 * @author pko
 *
 */
public class Connection implements Edge {

	private Switch destination;
	private Switch source;
	//private static Random r = null;

	private int id;
	private int weight;
	private boolean status;
	@Override
	public Switch getDestination() {

		return destination;
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public Switch getSource() {

		return source;
	}

	@Override
	public int getWeight() {

		return weight;
	}

	public void setDestination(Switch destination) {
		this.destination = destination;
	}

	public void setSource(Switch source) {
		this.source = source;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setStatus(boolean s)
	{

		status = s;
		
	}
	public boolean getStatus()
	{
	//	generateStatus();
		return status;	
	 
	}
	/*private void generateStatus()
	{		
		int tmp = getRandomValue();
		if ( tmp < weight)
		{
			status = true;
			return;
		}
		status = false;
		
	}*/
	
/*	private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		System.err.println(randomNumber);
		return randomNumber;
	}

	private static int getRandomValue() {
		if (r == null)
			r = new Random(Calendar.getInstance().getTimeInMillis());
		return showRandomInteger(0, 100, r);
	}*/



}
