package com.application.backend.payload.request;

public class setTagRequest {

	long imageId;

	String[] insertedTagNames;
	String[] deletedTagNames;

	public setTagRequest() {
	}

	/**
	 * @param imageId
	 * @param insertedTagNames
	 * @param deletedTagNames
	 */
	public setTagRequest(long imageId, String[] insertedTagNames, String[] deletedTagNames) {
		super();
		this.imageId = imageId;
		this.insertedTagNames = insertedTagNames;
		this.deletedTagNames = deletedTagNames;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String[] getInsertedTagNames() {
		return insertedTagNames;
	}

	public void setInsertedTagNames(String[] insertedTagNames) {
		this.insertedTagNames = insertedTagNames;
	}

	public String[] getDeletedTagNames() {
		return deletedTagNames;
	}

	public void setDeletedTagNames(String[] deletedTagNames) {
		this.deletedTagNames = deletedTagNames;
	}

}
