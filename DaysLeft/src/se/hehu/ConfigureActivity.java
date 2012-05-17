package se.hehu;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class ConfigureActivity extends Activity {
	int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	EditText titleEdit;
	DatePicker datePicker;
	WidgetConfiguration config;
	
	OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			saveSettings();
			refreshWidget();
			acceptSettings();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We default to canceled until user accepts the settings
        setResult(RESULT_CANCELED);

        extractWidgetIdFromIntent();
        
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
                
        setContentView(R.layout.config);
        findViewById(R.id.saveButton).setOnClickListener(onClickListener);
        
        loadSettings();
    }
    
    private void extractWidgetIdFromIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }
    
    private void loadSettings() {
    	config = new WidgetConfiguration(getBaseContext(), widgetId);
        SimpleDate date = config.getSimpleDate();
        
        titleEdit = (EditText) findViewById(R.id.configureTitle);
        datePicker = (DatePicker) findViewById(R.id.configureDate);
        
        titleEdit.setText(config.getTitle());
        if (date != null) {
        	datePicker.updateDate(date.getYear(), date.getMonth(), date.getDay());
        }
    }
    
    private SimpleDate getDatePickerDate() {
    	return new SimpleDate(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
    }
    
    private void saveSettings() {
		config.setTitle(titleEdit.getText().toString());
		config.setSimpleDate(getDatePickerDate());
    }
    
    private void refreshWidget() {
    	final Context context = this;
    	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        DaysLeftWidgetProvider.updateAppWidget(context, appWidgetManager, widgetId);
    }

	private void acceptSettings() {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		setResult(RESULT_OK, resultValue);
		finish();
	}
}