package com.imt.rbac.model;

public class UploadFileModel {
	
	private String fileName;
	private String fileUrl;
	private long fileSize;
	
	public UploadFileModel(String fileName,String fileUrl,long fileSize){
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
}
