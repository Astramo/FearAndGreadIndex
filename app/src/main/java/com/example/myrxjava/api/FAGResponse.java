package com.example.myrxjava.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FAGResponse{

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("name")
	private String name;

	public Metadata getMetadata(){
		return metadata;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"FAGResponse{" + 
			"metadata = '" + metadata + '\'' + 
			",data = '" + data + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}