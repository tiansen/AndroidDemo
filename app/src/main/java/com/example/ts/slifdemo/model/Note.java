package com.example.ts.slifdemo.model;

import java.util.Date;


public class Note {
	public Note(){

	}
	public Note(int _id, String title, String content, int flag, Date date, int create_date, String name) {
		this._id = _id;
		this.title = title;
		this.content = content;
		this.flag = flag;
		this.date = date;
		this.create_date = create_date;
		this.name = name;
	}

	int _id;
	String title;
	String content;
	int flag;
	Date date;
	int create_date;
	String name;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCreate_date() {
		return create_date;
	}

	public void setCreate_date(int create_date) {
		this.create_date = create_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
