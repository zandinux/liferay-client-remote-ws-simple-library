package gr.liferay.pojo;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class File {
	
	private String name;
	
	private String mimeType;
	
	private String dl_link;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the dl_link
	 */
	public String getDl_link() {
		return dl_link;
	}

	/**
	 * @param dl_link the dl_link to set
	 */
	public void setDl_link(String dl_link) {
		this.dl_link = dl_link;
	}

	
}
