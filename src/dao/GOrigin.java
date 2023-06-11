package dao;

/**
 * Origin manager
 * 
 * @author Daniel Serrano Rodriguez
 */
public interface GOrigin {

	/**
	 * Obtains the last set origin
	 * 
	 * @return String
	 */
	public String getOrigin();

	/**
	 * Inserts an origin
	 * 
	 * @param path String
	 * @return boolean
	 */
	public boolean insert(String path);

	/**
	 * Nukes the origins
	 */
	public void nuke();

}
