package projekt.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projekt.OwnList.RowItem;

import android.widget.Toast;

import com.example.cocktailmixxer.R;

//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg, Jakob Nisin


public class Cocktail extends RowItem implements Serializable{
	static String Name;
	static String desc;
	public String ret;
	final public int cocktailsize = 250;
	int mlLeft=cocktailsize;
	int mlFull=0;
	List<? super Saft> SaftList_cocktail;
	Saft activeSaft;
	//Gibt bytes zurück die per bluetooth gesendet werden
	public byte[] getBluetoothSignal(List<? super Saft>  SaftListIntern) {
		String returnStr = "\r\nAAAA";
		List<? super Saft> Saftlist=SaftListIntern;
		
		for (int i = 0; i < SaftList_cocktail.size(); i++) {
			if(SaftList_cocktail.get(i)!=null){
				if(((Saft) SaftList_cocktail.get(i)).getMl()<100)
					
					returnStr+=SaftListIntern.indexOf(((Saft) SaftList_cocktail.get(i)))+1+"0"+((Saft) SaftList_cocktail.get(i)).getMl();
				else 
					returnStr+=SaftListIntern.indexOf(((Saft) SaftList_cocktail.get(i)))+1+""+((Saft) SaftList_cocktail.get(i)).getMl();
			}
		}
		returnStr+="BBBB\r\n";
		ret = returnStr;
		return returnStr.getBytes();
	}
	public Saft getActiveSaft() {
		return activeSaft;
	}

	public void setActiveSaft(Saft activeSaft) {
		this.activeSaft = activeSaft;
	}

	static int imageId = R.drawable.icon_cocktail;

	
	public Cocktail(int imageId, String title, String desc) {
		super(imageId, title, desc);
		Name = title;
		this.desc = desc;
		SaftList_cocktail = (List<Saft>) new ArrayList<Saft>();

	}

	public void setSaftList_cocktail(List<? super Saft> saftList_cocktail) {
		SaftList_cocktail = saftList_cocktail;
	}

	public void setName(String name) {
		super.setTitle(name);
	}

	public void setDesc(String info) {
		super.setDesc(info);
	}



	public int getMlLeft() {
		return mlLeft;
	}

	public void setMlLeft(int mlLeft) {
		this.mlLeft = mlLeft;
	}

	
	public void getC_ID() {

	}

	public List<RowItem> get_SaftList_cocktail() {
		return (List<RowItem>) SaftList_cocktail;
	}
 public double getAlkgehalt(){
	 double alk =0;
	 for (int i = 0; i < SaftList_cocktail.size(); i++) {
			if(SaftList_cocktail.get(i)!=null){
				alk +=((Saft) SaftList_cocktail.get(i)).getAlkMl(); 
			}
		}
	 return alk;
 }
	public void addSaft(Saft saft) {
		for (int i = 0; i < SaftList_cocktail.size(); i++) {
			if(((Saft)SaftList_cocktail.get(i)).getTitle()==saft.getTitle()){
				
				SaftList_cocktail.remove(i);
				
			}
		}
		SaftList_cocktail.add(saft);
	}

	public String toString() {
		return "";
	}
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
