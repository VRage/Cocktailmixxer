package projekt.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import projekt.OwnList.RowItem;


public class User extends RowItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public int icon;

	private static String B_Name;
	private int ID;
	private double Weight =0.0;
	private double Length=0.0;
	private double AlkGehalt;
	private double Promille;
	private Date FahrtuechtigIn;
	private List<? super Cocktail> cocktails = (List<Cocktail>) new ArrayList<Cocktail>();
	private Boolean Geschlecht;
	public int Alter =0;
	public GregorianCalendar Birthdate;
	
	public User(String name, double weight, double length, boolean gesch,GregorianCalendar birth)
	{
		super(CM_Status.getUserid(), name, "");
		B_Name = name;
		Weight = weight;
		Length = length;
		Geschlecht = gesch;
		Birthdate = birth;
		setAlter();
		ID = CM_Status.getUserid();
		AlkGehalt = 0.0; 
		Promille= 0.0;
		setDescription();
		CM_Status.inkUserid();
	}
	
	public void addCocktail(Cocktail cock)
	{
		cocktails.add(cock);
	}
	public void removeCocktails()
	{
		cocktails.clear();
	}

	public double getPromille() {
		return Promille;
	}
	public void setDescription(){
		super.setDesc("Alter: "+Alter+"\nGröße: "+Length+"\nGeburstag: "+Birthdate.get(GregorianCalendar.DAY_OF_MONTH)+"."+Birthdate.get(GregorianCalendar.MONTH)+"."+Birthdate.get(GregorianCalendar.YEAR));
		
	}

	
	public int getID() {
		return ID;
	}

	public void setB_Name(String name) {
		
		this.B_Name = name;
	}
	public String getB_Name() {
		return B_Name;
	}

	
	public void setWeight(double gewicht) {
		this.Weight = gewicht;
	}
	
	public void setLength(double lang) {
		this.Length = lang;
	}
	
	public void setGeschlaecht(boolean w){
		if(w)
			this.Geschlecht = true;
		else
			this.Geschlecht = false;
	}
	public String getGeschlaecht()
	{
		if(Geschlecht ==true)
			return "Männlich";
		else
			return "Weiblich";
	}
	
	public void setBirthdate(GregorianCalendar date){
		
		this.Birthdate = date;
	}
	
	public void setFahrtuechtigIn() {
		
	}
	public void setAlter()
    {    

        GregorianCalendar heute = new GregorianCalendar();
 
        int alter = heute.get(Calendar.YEAR) - (Birthdate.get(Calendar.YEAR));
 
        if (heute.get(Calendar.MONTH) < Birthdate.get(Calendar.MONTH))
        {
        	
            alter = alter - 1;
        }
        else if (heute.get(Calendar.MONTH) == Birthdate.get(Calendar.MONTH))
        {
            if (heute.get(Calendar.DATE) <= Birthdate.get(Calendar.DATE))
            {
                alter = alter - 1;
            }
        }
        this.Alter =alter;
    }
	
	public void setAlkgehalt(double alk)
	{
		AlkGehalt = alk;
	}
	
	public void calcAlkAnteil() {
		
		if(!Geschlecht){ 		//weibliche Berechnungsformel
			
			 Promille = (0.8* AlkGehalt)/(-2.097 + 0.1069 * Length + 0.2466 * Weight);
		}else{					//männliche Berechnungsformel
			 Promille = (0.8* AlkGehalt)/(2.447 - 0.09516 * Alter + 0.1074 * Length + 0.3362 * Weight);
		}
		
	
	}
	
	public String toString() {
		return "Benutzername: "+B_Name+" Alter: "+Alter+ " Größe: "+Length+" Gewicht: "+Weight
				+" Aktuller Promillewert: "+Promille + " Geschlecht: "+ getGeschlaecht() ;
	}
}
