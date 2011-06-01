package pdm.beans;

import java.io.Serializable;
import java.sql.Blob;

import org.hibernate.Hibernate;

import pdm.Utils.Const;

import dao.Id;

public class File  implements Id,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7799629433164977563L;
	private Integer Id;
	private String Name;
    private String mime;
    private long length;
    private byte[] data;
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public String getName() {
        return Name;
    }
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
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }
    
    public String getMime(){
        return mime;
    }
	@Override
	public Integer getId() {
		return Id;
		
	}
	@Override
	public void setId(Integer id) {
		Id = id;		
	}
	
	 /** Don't invoke this.  Used by Hibernate only. */
	 public void setDataBlob(Blob imageBlob) {
	  this.data = Const.toByteArray(imageBlob);
	 }

	 /** Don't invoke this.  Used by Hibernate only. */
	 public Blob getDataBlob() {
	  return Hibernate.createBlob(this.data);
	  
	 }
	

	
}