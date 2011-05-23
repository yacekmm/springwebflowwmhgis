package pdm.beans;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public class FileUploadBean{
    
    private ArrayList<File> files = new ArrayList<File>();
    private int uploadsAvailable = 1;
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

	private boolean autoUpload = false;
    private boolean useFlash = false;
    public int getSize() {
        if (files.size()>0){
            return files.size();
        }else 
        {
            return 0;
        }
    }

    public FileUploadBean() {
    }

   /* public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer)object).getData());
    }*/
    public void listener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        File file = item.getFile();
        //File file = new File();
        
       // file.setLength(item.getData().length);
       // file.setName(item.getFileName());
       // file.setData(item.getData());
        files.add(file);
        uploadsAvailable--;
    }

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public boolean isUseFlash() {
		return useFlash;
	}  


}
