package se.hehu;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

public class ConfigureActivity extends Activity {
	
	int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	EditText titleEdit;
	DatePicker datePicker;
	WidgetConfiguration config;
	SimpleDate date;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // We default to canceled until user confirms settings
        setResult(RESULT_CANCELED);
        
        setContentView(R.layout.config);

        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        
        config = new WidgetConfiguration(getBaseContext(), widgetId);
        date = config.getSimpleDate();
        
        titleEdit = (EditText) findViewById(R.id.configureTitle);
        datePicker = (DatePicker) findViewById(R.id.configureDate);
        
        titleEdit.setText(config.getTitle());
        if (date != null) {
        	datePicker.updateDate(date.getYear(), date.getMonth(), date.getDay());
        }
    }
}