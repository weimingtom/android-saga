package com.androidsaga;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.util.EncodingUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.content.Context;
import com.constantvalue.ConstantUtil;

public class Data extends Object{
	public Integer status = ConstantUtil.NORMAL_SAGA;
	
	public Integer totalTime = 0;
	public Integer level = 0;
	public Integer satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
	public Integer hungry = 0;
	public Integer dirty = 0;
	public Integer sick = 0;
	public Integer otaku = 0;
	public Integer inelegant = 0;
	public Integer scientist = 0;
	public Integer strange = 0;	
	
	protected HashMap<String, Integer> materialList = new HashMap<String, Integer>();
	
	public void increaseTotalTime(){
		++totalTime;
		
		//update level
		if(level*level*3600 < totalTime)
			++level;
	}
	
	public void clearData(boolean reserveTimeAndValue){
		satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
		hungry = dirty = sick = otaku = inelegant = scientist = strange = 0;
		status = ConstantUtil.NORMAL_SAGA;
		
		if(!reserveTimeAndValue){
			totalTime = level = 0;
		}
	}
	
	public void saveData(Context ctx, String path){
		FileOutputStream fout = null;
		try{			
			fout = ctx.openFileOutput(path, Context.MODE_PRIVATE);
			String toWrite = "STATUS="+status.toString()+"\n";			
			toWrite += ("TOTAL_TIME="+totalTime.toString()+"\n");
			toWrite += ("LEVEL="+level.toString()+"\n");
			toWrite += ("SATISFACTION="+satisfaction.toString()+"\n");
			toWrite += ("HUNGRY="+hungry.toString()+"\n");
			toWrite += ("DIRTY="+dirty.toString()+"\n");
			toWrite += ("SICK="+sick.toString()+"\n");
			toWrite += ("OTAKU="+otaku.toString()+"\n");
			toWrite += ("INELEGANT="+inelegant.toString()+"\n");
			toWrite += ("SCIENTIST="+scientist.toString()+"\n");
			toWrite += ("STRANGE="+strange.toString()+"\n");			
			
			Iterator<Map.Entry<String, Integer>> it = materialList.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, Integer> entry = it.next();
				toWrite += ("PRESENT="+entry.getKey().toString()+
						",AMOUNT="+entry.getValue().toString()+"\n");
			}			
			
			fout.write(toWrite.getBytes());				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(fout != null){
					fout.close();
					fout = null;
				}				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void loadData(Context ctx, String path){
		FileInputStream fin = null;
		try{		
			fin = ctx.openFileInput(path);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			String[] dataBlock = EncodingUtils.getString(buffer, ConstantUtil.ENCODING).split("\n");
			for(String line: dataBlock){
				if(line.indexOf("STATUS=") >= 0){
					status = Integer.parseInt(line.substring(7));					
				}
				else if(line.indexOf("TOTAL_TIME=") >= 0){
					totalTime = Integer.parseInt(line.substring(11));					
				}
				else if(line.indexOf("LEVEL=") >= 0){
					level = Integer.parseInt(line.substring(6));					
				}
				else if(line.indexOf("SATISFACTION=") >= 0){
					satisfaction = Integer.parseInt(line.substring(13));					
				}
				else if(line.indexOf("HUNGRY=") >= 0){
					hungry = Integer.parseInt(line.substring(7));					
				}
				else if(line.indexOf("DIRTY=") >= 0){
					dirty = Integer.parseInt(line.substring(6));
				}
				else if(line.indexOf("SICK=") >= 0){
					sick = Integer.parseInt(line.substring(5));
				}
				else if(line.indexOf("OTAKU=") >= 0){
					otaku = Integer.parseInt(line.substring(6));
				}
				else if(line.indexOf("INELEGANT=") >= 0){
					inelegant = Integer.parseInt(line.substring(10));
				}
				else if(line.indexOf("SCIENTIST=") >= 0){
					scientist = Integer.parseInt(line.substring(10));
				}
				else if(line.indexOf("STRANGE=") >= 0){
					strange = Integer.parseInt(line.substring(9));
				}
				else if(line.indexOf("PRESENT=") >= 0){
					int amountIdx = line.indexOf("AMOUNT=");
					String name = line.substring(8, amountIdx - 1);
					int amount = Integer.parseInt(line.substring(amountIdx+7));
					materialList.put(name, amount);
				}					
			}						
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(fin != null){
					fin.close();
					fin = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public Integer getMaterialCount(String name){
		if(!materialList.containsKey(name)){
			return -1;
		}
		
		return (materialList.get(name));
	}
	
	public void addMaterial(String name){
		if(materialList.containsKey(name)){
			Integer curAmount = materialList.remove(name);
			materialList.put(name, curAmount+1);
		}
		else{
			materialList.put(name, 1);
		}
	}
	
	public Integer useMaterial(String name, Integer amount){
		if(getMaterialCount(name) < amount){
			return -1;
		}
		Integer curAmount = materialList.remove(name);
		curAmount -= amount;
		if(curAmount > 0){
			materialList.put(name, curAmount);
		}		
		return 1;
	}
}