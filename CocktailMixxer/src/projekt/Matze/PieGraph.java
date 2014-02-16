package projekt.Matze;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieGraph {
	public Intent getIntent(Context context){
		int[] values = {1,2,3,4};
		CategorySeries series = new CategorySeries("Piegraph");
		int k =0;
		for(int value:values){
			series.add("Section "+ ++k,value );
		}
		
		int[]colors = new int[]{color.black,Color.BLUE,Color.YELLOW,Color.GREEN};
		DefaultRenderer renderer = new DefaultRenderer();
		for(int color:colors){
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(color);
		renderer.addSeriesRenderer(r);
		}
		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Inhalt");
		return intent;
	}
	
}
