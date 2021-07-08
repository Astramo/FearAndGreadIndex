package com.example.myrxjava.api;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class DataItem{

	@SerializedName("time_until_update")
	private String timeUntilUpdate;

	@SerializedName("value_classification")
	private String valueClassification;

	@SerializedName("value")
	private String value;

	@SerializedName("timestamp")
	private String timestamp;

	public String getTimeUntilUpdate(){
		return timeUntilUpdate;
	}

	public String getValueClassification(){
		return valueClassification;
	}

	public String getValue(){
		return value;
	}

	public String getTimestamp(){
		return timestamp;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"time_until_update = '" + timeUntilUpdate + '\'' + 
			",value_classification = '" + valueClassification + '\'' + 
			",value = '" + value + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}



//		@BindingAdapter("android:setDate")
//		public void setDate(TextView textView){
//			List<String> strings = Arrays.asList("Yesterday", "day before yesterday");
//		}
}