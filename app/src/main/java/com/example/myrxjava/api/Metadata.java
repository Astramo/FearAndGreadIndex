package com.example.myrxjava.api;

import com.google.gson.annotations.SerializedName;

public class Metadata{

	@SerializedName("error")
	private Object error;

	public Object getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"Metadata{" + 
			"error = '" + error + '\'' + 
			"}";
		}
}