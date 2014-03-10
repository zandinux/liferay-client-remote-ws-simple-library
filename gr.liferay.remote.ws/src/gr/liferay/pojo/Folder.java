package gr.liferay.pojo;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class Folder {
	
	private String name;
	
	private long folderId;
	
	private long groupId;

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
	 * @return the folderId
	 */
	public long getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	
}
