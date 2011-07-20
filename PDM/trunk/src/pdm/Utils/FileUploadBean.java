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
import pdm.interfacaces.Resetable;
import pdm.tree.TreeBean;

/**
 * Klasa odpowiedzialna za import plików(obrazów) do aplikacji
 * 
 * @author pkonstanczuk
 * 
 */
public class FileUploadBean implements Serializable, Resetable {
	/**
	 * Referencja do FileDAO
	 */
	private FileDAO fileDAO;
	/**
	 * Referencja do treeBean(silnika aplikacji)
	 */
	private TreeBean treeBean;

	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = 429447487906084218L;
	
	/**
	 * Liczba możliwych uploadów
	 */
	private int uploadsAvailable = 1;
	/**
	 * Flaga automatycznego uploadu
	 */
	private boolean autoUpload = true;
	/**
	 * Flaga użycia Flash
	 */
	private boolean useFlash = true;
/**
 * Getter ilości pobranych plików
 * @return
 */
	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}
/** Konstruktor domyślny
 * 
 */
	public FileUploadBean() {
	}
/**
 * Funkcja odrysowująca dany plik w danym strumieniu
 * @param stream
 * @param object
 * @throws IOException
 */
	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());

	}
/**
 * Funkcja odrysowująca pierwszy obraz danego SearchResult w danym strumieniu
 * @param stream
 * @param object
 * @throws IOException
 */
	public void paintObject(OutputStream stream, Object object)
			throws IOException {
	/*	if (object != null && object instanceof File) {
			File f = (File) object;
			stream.write(f.getData());
		}
		if (object != null && object instanceof SearchResult) {
			SearchResult f = (SearchResult) object;

			File tmp = f.getFile();
			if (f.getFile() != null)
				stream.write(f.getFile().getData());
		}*/
		if (object != null && object instanceof Integer) {
			Integer id = (Integer) object;
			File f;
			f = fileDAO.getFileById(id);
			if (f != null)
				stream.write(f.getData());
		}

	}
/**
 * Listener FileUploadu, przez któy komunikuje się z GUI
 * @param event
 * @throws Exception
 */
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
/**
 * Resetowanie klasy
 * @return
 */
	public String clearUploadData() {
		PdmLog.getLogger().info("Clear upload data");
		treeBean.getAddedElement().getFiles().clear();
		getFiles().clear();
		setUploadsAvailable(1);
		return null;
	}
/**
 * Pobranie aktualnego timestampa
 * @return
 */
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}
/**
 * Getter listy plików 
 * @return
 */
	public ArrayList<File> getFiles() {
		ArrayList<File> tmp = new ArrayList<File>();
		tmp.addAll(treeBean.getAddedElement().getFiles());
		return tmp;
	}
/**
 * Setter listy plików
 * @param files
 */
	public void setFiles(ArrayList<File> files) {

		treeBean.getAddedElement().setFiles(files);
	}
/**
 * Getter uploadsAvailable
 * @return
 */
	public int getUploadsAvailable() {
		return uploadsAvailable;
	}
/**
 * Setter uploadAvailable
 * @param uploadsAvailable
 */
	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}
/**
 * Getter autoUpload
 * @return
 */
	public boolean isAutoUpload() {
		return autoUpload;
	}
/**
 * Setter AutoUpload
 * @param autoUpload
 */
	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}
/**
 * Getter useFlash
 * @return
 */
	public boolean isUseFlash() {
		return useFlash;
	}
/**
 * Setter useFlash
 * @param useFlash
 */
	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}
/**
 * Setter fileDAO
 * @param fileDAO
 */
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}
/**
 * Getter fileDAO
 * @return
 */
	public FileDAO getFileDAO() {
		return fileDAO;
	}
/**
 * Setter treeBean
 * @param treeBean
 */
	public void setTreeBean(TreeBean treeBean) {
		this.treeBean = treeBean;
	}
/**
 * Getter treeBean
 * @return
 */
	public TreeBean getTreeBean() {
		return treeBean;
	}
/**
 * Funkcja resetująca
 */
	@Override
	public boolean reset() {
		clearUploadData();
		setUploadsAvailable(1);
		return true;
	}
/**
 * Funkcja informująca o stanie instancji klasy
 * @return
 */
 
	public boolean isClear() {
		if (getFiles() == null || getFiles().isEmpty())
			return true;

		return false;
	}

}