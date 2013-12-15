package projekt.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import actual_working.Saft;

import com.example.cocktailmixxer.R;

import OwnList.RowItem;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Cocktail.java
//  @ Date : 31.10.2013
//  @ Author : 
//
//




public class Cocktail extends RowItem implements Serializable{
	static String Name;
	int C_ID;
	static String desc;
	//List<Saft> Saefte;
	int MixIDList;
	int mlLeft;
	final public int cocktailsize = 500;
	List<? super Saft> SaftList_cocktail;
	Saft activeSaft;
	public Saft getActiveSaft() {
		return activeSaft;
	}

	public void setActiveSaft(Saft activeSaft) {
		this.activeSaft = activeSaft;
	}

	static int imageId = R.drawable.icon_cocktail;

	
	public Cocktail(int imageId, String title) {
		super(imageId, desc, "-");
		desc = title;
		SaftList_cocktail = (List<Saft>) new ArrayList<Saft>();

	}

	public List<RowItem> getSaftList_cocktail() {
		return (List<RowItem>) SaftList_cocktail;
	}

	public void setSaftList_cocktail(List<? super Saft> saftList_cocktail) {
		SaftList_cocktail = saftList_cocktail;
	}

	public void setName(String name) {
		super.setTitle(name);
	}

	public void setInfo(String info) {
		this.desc = info;
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

	public void addSaft(Saft saft) {
		for (int i = 0; i < SaftList_cocktail.size(); i++) {
			if(((Saft)SaftList_cocktail.get(i)).getTitle()==saft.getTitle())
				SaftList_cocktail.remove(i);
		}
		SaftList_cocktail.add(saft);
	}

	public String toString() {
		return "";
	}
}