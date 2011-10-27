package pdm.Utils;

import org.apache.log4j.Logger;

/**
 * Klasa logujaca do pliku html i na konsole
 * @author jacek
 *
 */
public class PdmLog {
	/**
	 * statyczna instancja klasy
	 */
	public static PdmLog pdmLog;
	/**
	 * statyczna instancja logera
	 */
	private static Logger logger;
	
	/**
	 * konstruktor
	 */
	public PdmLog(){
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	/**
	 * zwraca instancje logera
	 * @return instancja logera
	 */
	public static Logger getLogger(){
		if (logger == null) {
			pdmLog = new PdmLog();
		}
		return logger;
	}
}
