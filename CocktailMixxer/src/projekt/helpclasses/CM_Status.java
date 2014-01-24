package projekt.helpclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projekt.OwnList.RowItem;
import android.app.Application;
import android.os.Environment;


//  @ Project : Cocktailmixxer
//  @ Date : 31.10.2013
//  @ Author : Matthias Wildberg, Jakob Nisin


public class CM_Status extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	User activeUser = null;
	private static int Userid = 100;
	Cocktail activeCocktail;
	private BluetoothSerialService BTservice;

	public BluetoothSerialService getBTservice() {
		return BTservice;
	}

	public void setBTservice(BluetoothSerialService bTservice) {
		BTservice = bTservice;
	}

	List<? super Object> status = new ArrayList<Object>();
	List<? super User> UserList = (List<User>) new ArrayList<User>();
	List<? super Cocktail> CocktailList = (List<Cocktail>) new ArrayList<Cocktail>();
	List<? super Cocktail> CocktailListOrderable= (List<Cocktail>) new ArrayList<Cocktail>();
	List<? super Saft> SaftList_intern = (List<Saft>) new ArrayList<Saft>();
	List<? super Saft> SaftList_all = (List<Saft>) new ArrayList<Saft>();

	public final static String APP_PATH_SD_USERPICS = "/cocktailmixxer/userpics";
	public final static String STATUS_FILENAME = "CM_Status"; // Changed new //
																// Parameter
	String fullPathPic = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_USERPICS;
	public final static String APP_PATH_SD_APPLICATION = "/cocktailmixxer/";
	static String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_APPLICATION;

	public void saveAll() {
		try {
			saveToSerFile("User", UserList);
			saveToSerFile("Cocktail", CocktailList);
			saveToSerFile("Saft", SaftList_all);
			saveToSerFile("Status", status);
			saveToSerFile("Saftintern", SaftList_intern);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadAll() {
		try {
			UserList = (List<User>) loadFromSerFile("User");
			CocktailList = (List<Cocktail>) loadFromSerFile("Cocktail");
			SaftList_all = (List<Saft>) loadFromSerFile("Saft");
			status = (List<Object>) loadFromSerFile("Status");
			SaftList_intern = (List<Saft>) loadFromSerFile("Saftintern");
			getStatus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getUserid() {

		return Userid;

	}

	public static void inkUserid() {
		Userid++;
	}

	public List<RowItem> get_CocktailList() {
		return (List<RowItem>) CocktailList;
	}

	public List<RowItem> get_UserList() {
		return (List<RowItem>) UserList;
	}
	public List <RowItem>get_CocktailListOrderable(){
		return (List<RowItem>)CocktailListOrderable;
	}

	public List<RowItem> get_SaftList_intern() {
		return (List<RowItem>) SaftList_intern;
	}
	
	public void set_SaftList_intern(List<RowItem> list){
		List<RowItem> SaftList_intern = list;
	}

	public List<RowItem> get_SaftList_all() {
		return (List<RowItem>) SaftList_all;
	}

	public User get_ActiveUser() {
		return activeUser;
	}

	public void set_ActiveUser(User activeUser) {
		this.activeUser = activeUser;
		setStatus();
	}

	public void setStatus() {
		status.clear();
		status.add(0, activeUser);
		status.add(1, Userid);
		status.add(2, activeCocktail);
	}

	public void getStatus() {
		if (status != null && !status.isEmpty()) {
			activeUser = (User) status.get(0);
			Userid = (Integer) status.get(1);
			activeCocktail = (Cocktail) status.get(2);
		}
	}

	public CM_Status() {
		// UserList.add(new User("MaxSchuster", 55.0, 155.0, true,
		// new GregorianCalendar(1987, 06, 18)));

		SaftList_all.add(new Saft("Orangen Saft", "Sehr fruchtiger Saft #leckerfruchtig",0));
		SaftList_all.add(new Saft("Red Bull", "Macht wach also nicht zuviel davon \n#wake me up up up ",0));
		SaftList_all.add(new Saft("Cola", "Lecker zeug",0));
		SaftList_all.add(new Saft("Ice Tee", "Auch lecker",0));
		SaftList_all.add(new Saft("Wodka", "Russenwasser \n#YOLO",38));
		SaftList_all.add(new Saft("Rum", "Das Zeug um sich wegzubeamen \n#Edelshit",38));
		SaftList_all.add(new Saft("Baileys", "MUhahahah",17));
		for (int i = 0; i < 8; i++) {
			SaftList_intern.add(new Saft("", "",0));
		}

		// activeUser = (User) UserList.get(0);

	}

	public void add_UserItem(User user) {
		UserList.add(user);
		try {
			saveToSerFile("User", UserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add_CocktailItem(Cocktail cocktail) {
		CocktailList.add(cocktail);
		try {
			saveToSerFile("Cocktail", CocktailList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete_UserItem(User user) {
		UserList.remove(user);
		File imgFile = new File(fullPathPic, user.getID() + ".jpg");
		if (imgFile.exists()) {
			imgFile.delete();
		}
	}

	public Cocktail get_ActiveCocktail() {
		return activeCocktail;
	}

	public void set_ActiveCocktail(Cocktail activeCocktail) { // Changed new
																// method
		this.activeCocktail = activeCocktail;
		setStatus();
	}

	public synchronized void saveToSerFile(String name,
			List<? extends Object> obj) throws IOException {
		try {

			// save to file
			File file = new File(fullPath, name + ".dat");
			if (file.exists()) {
				file.delete();
			}

			file.getParentFile().mkdirs();
			file.createNewFile();

			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public synchronized Object loadFromSerFile(String name) throws IOException,
			ClassNotFoundException {
		File file = new File(fullPath, name + ".dat");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object result = ois.readObject();
		ois.close();
		return result;
	}

}
