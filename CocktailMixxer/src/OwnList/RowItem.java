package OwnList;

import java.io.Serializable;

import projekt.helpclasses.Saft;

public class RowItem implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ns = null;
    private int imageId;
    private String title;
    private String desc;
 
    public RowItem(int imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.desc = description;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
	public int compareTo(Saft another) {
		// TODO Auto-generated method stub
		return 0;
	}

 }