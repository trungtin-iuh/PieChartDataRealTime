package com.example.user.piechartdatarealtime.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 *  @author uy.daonguyen@live.com
 *  
 *  Reference link:
 */
public class GsonUtil {
	private static Gson instance;
	
	private GsonUtil(){
		
	}
	
	public static Gson getInstance(){
		if(instance == null){
			instance = new Gson();
		}
		return instance;
	}

	public static final <T> List<T> getList(String json){

		List<T> listTConvert = null;
		try{
			Type typeOfList = new TypeToken<List<T>>(){}.getType();
			listTConvert = getInstance().fromJson(json, typeOfList);
		} catch (Exception e){

		}

		return listTConvert;
	}

	public static final <T> T get(String json){

		T result = null;
		try{
			Type typeOfList = new TypeToken<T>(){}.getType();
			result = getInstance().fromJson(json, typeOfList);
		} catch (Exception e){

		}

		return result;
	}
}
