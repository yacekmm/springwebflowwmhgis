package pdm.beans;

import java.io.Serializable;

import pdm.dao.Id;

/** 
 * Klasa plikow(obrazu) - mapowanie do bazy danych
 * @author pkonstanczuk
 *
 */
public class File  implements Id,Serializable{

    /**
	 * Serializacja
	 */
	private static final long serialVersionUID = -7799629433164977563L;
	 /**
	 * ID
	 */
	private Integer Id;
	 /**
	 * Nazwa pliku
	 */
	private String Name;
	 /**
	 *Typ mime pliku
	 */
    private String mime;
    /**
	 * Dlugosc pliku
	 */
    private long length;
    /**
	 * Plik w postaci binarnej
	 */
    private byte[] data;
    
    /**
     * Getter data
     * @return
     */
    public byte[] getData() {
        return data;
    }
    /**
     * Setter data     
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    /**
     * Getter name
     * @return
     */
    public String getName() {
        return Name;
    }
    /**
     * Setter name    
     */
    public void setName(String name) {
        Name = name;
        int extDot = name.lastIndexOf('.');
        if(extDot > 0){
            String extension = name.substring(extDot +1);
            if("bmp".equals(extension)){
                mime="image/bmp";
            } else if("jpg".equals(extension)){
                mime="image/jpeg";
            } else if("gif".equals(extension)){
                mime="image/gif";
            } else if("png".equals(extension)){
                mime="image/png";
            } else {
                mime = "image/unknown";
            }
        }
    }
    /**
     * Getter length
     * @return
     */
    public long getLength() {
        return length;
    }
    /**
     * Setter length

     */
    public void setLength(long length) {
        this.length = length;
    }
    /**
     * Getter mime
     * @return
     */
    public String getMime(){
        return mime;
    }
    /**
     * Getter id

     */
	@Override
	public Integer getId() {
		return Id;
		
	}
	  /**
     * Setter id
     * @return
     */
	@Override
	public void setId(Integer id) {
		Id = id;		
	}

	

	
	

	
}