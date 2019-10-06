package com.instcar.android.sortview2;

public class SortModel {

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String pic;  //显示数据拼音的首字母
	
	public String getName() {
		return name;
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
