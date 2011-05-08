package pdm.Utils;

import org.apache.log4j.Logger;

public class PdmLog {
	public static PdmLog pdmLog;
	private static Logger logger;
	
	public PdmLog(){
		logger = Logger.getLogger(this.getClass().getName());
	}
	
//	public static PdmLog getLogger(){
//		if (pdmLog == null) {
//			pdmLog = new PdmLog();
//		}
//		return pdmLog;
//	}
	
	public static Logger getLogger(){
		if (logger == null) {
			pdmLog = new PdmLog();
		}
		return logger;
	}
}
