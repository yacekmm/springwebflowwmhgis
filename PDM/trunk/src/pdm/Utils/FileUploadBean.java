package pdm.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pdm.beans.File;
import pdm.beans.SearchResult;
import pdm.dao.FileDAO;
import pdm.interfacaces.Resetable;
import pdm.tree.TreeBean;

public class FileUploadBean implements Serializable, Resetable {

	private FileDAO fileDAO;
	private TreeBean treeBean;

	/**
	 * 
	 */
	private static final long serialVersionUID = 429447487906084218L;
	// private ArrayList<File> files = new ArrayList<File>();
	private int uploadsAvailable = 1;
	private boolean autoUpload = true;
	private boolean useFlash = true;

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public FileUploadBean() {
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());

	}

	public void paintObject(OutputStream stream, Object object)
			throws IOException {
		if (object != null && object instanceof File) {
			File f = (File) object;
			stream.write(f.getData());
		}
		if (object != null && object instanceof SearchResult) {
			SearchResult f = (SearchResult) object;
			
			File tmp  = f.getFile();
			if (f.getFile() != null)
			stream.write(f.getFile().getData());
		}
		if (object != null && object instanceof Integer) {
			Integer id = (Integer) object;
			File f;
			f  = fileDAO.getFileById(id);			
			if (f != null)
			stream.write(f.getData());
		}
		
		

	}

	public void listener(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();
		File file = new File();
		java.io.File f = item.getFile();
		byte[] data = new byte[(int) f.length()];
		FileInputStream fileInputStream = new FileInputStream(f);
		fileInputStream.read(data);
		file.setLength(data.length);
		file.setName(item.getFileName());
		file.setData(data);
		getFiles().add(file);
		uploadsAvailable--;
		try {
			treeBean.getAddedElement().getFiles().add(file);
			// fileDAO.saveOrUpdate(file);
		} catch (Exception e) {
			e.printStackTrace();
			PdmLog.getLogger().error("Error in FileUploadBean");
		}

	}

	public String clearUploadData() {
		PdmLog.getLogger().info("Clear upload data");
		treeBean.getAddedElement().getFiles().clear();
		getFiles().clear();
		setUploadsAvailable(1);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<File> getFiles() {
		ArrayList<File> tmp = new ArrayList<File>();
		tmp.addAll(treeBean.getAddedElement().getFiles());
		return tmp;
	}

	public void setFiles(ArrayList<File> files) {

		treeBean.getAddedElement().setFiles(files);
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

	public void setTreeBean(TreeBean treeBean) {
		this.treeBean = treeBean;
	}

	public TreeBean getTreeBean() {
		return treeBean;
	}

	@Override
	public boolean reset() {
		clearUploadData();
		setUploadsAvailable(1);
		return true;
	}

	public boolean isClear() {
		if (getFiles() == null || getFiles().isEmpty())
			return true;

		return false;
	}

}