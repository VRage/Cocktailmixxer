package projekt.Activitys;

import java.util.List;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.User;
import OwnList.CustomListViewAdapter;
import OwnList.RowItem;
import actual_working.Saft;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class SelectSaftActivity extends Activity {
	CM_Status status;
	List<RowItem> safte;
	List<RowItem> safte_intern;
	ListView saftlist_all;
	CustomListViewAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_saft);
		status = (CM_Status) getApplicationContext();
		saftlist_all = (ListView) findViewById(R.id.selectsaft_saftlistall);
		safte = status.get_SaftList_all();
		safte_intern = status.get_SaftList_intern();
		adapter = new CustomListViewAdapter(this,R.layout.activity_listitem, safte);
		saftlist_all.setAdapter(adapter);
		Intent sender=getIntent();
        String extraData=sender.getExtras().getString("Bottlenumber");
        final int bottlenumber = Integer.parseInt(extraData);
        Toast.makeText(getApplicationContext(), ""+bottlenumber, Toast.LENGTH_LONG).show();
		saftlist_all.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				safte_intern.set(bottlenumber-1, (Saft) saftlist_all.getSelectedItem());
				finish();
			}
		});
	}
	


}
