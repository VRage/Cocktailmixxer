package OwnList;

import java.io.IOException;
import java.io.Serializable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.DropBoxManager.Entry;

public class RowItem implements Serializable{
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

 }