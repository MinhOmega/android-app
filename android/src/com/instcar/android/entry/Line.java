package com.instcar.android.entry;

import java.util.ArrayList;
import java.util.List;

public class Line {
public String id;
public String name;
public String description;
public String price;
public String addtime;
public String modtime;
public List<CarPoint> list;
public Line() {
	// TODO Auto-generated constructor stub
	list = new ArrayList<CarPoint>();
}
	
}
