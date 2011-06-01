package pdm.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pdm.beans.File;
import pdm.dao.FileDAO;

public class FileUploadBean implements Serializable{
	
	private FileDAO fileDAO;
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 429447487906084218L;
	private ArrayList<File> files = new ArrayList<File>();
    private int uploadsAvailable = 5;
    private boolean autoUpload = false;
    private boolean useFlash = false;
    public int getSize() {
        if (getFiles().size()>0){
            return getFiles().size();
        }else 
        {
            return 0;
        }
    }

    public FileUploadBean() {
    }

    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer)object).getData());
    }
    public void listener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        File file = new File();
        java.io.File f = item.getFile();       
        byte[] data = new byte[(int) f.length()];
        FileInputStream fileInputStream = new FileInputStream(f);
        fileInputStream.read(data);
        file.setLength(data.length);
        file.setName(item.getFileName());
        file.setData(data);
        files.add(file);
        uploadsAvailable--;
        try
        {
        //fileDAO.saveOrUpdate(file);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        
    }  
      
    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(5);
        return null;
    }
    
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
    
    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) { 
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }

	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	public FileDAO getFileDAO() {
		return fileDAO;
	}

}