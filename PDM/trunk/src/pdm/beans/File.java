package pdm.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import pdm.dao.Id;


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
	
	public void paint(OutputStream stream, Object object) throws IOException {
		  stream.write(this.getData());
		  
	}
	
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}
	

	
	

	
}