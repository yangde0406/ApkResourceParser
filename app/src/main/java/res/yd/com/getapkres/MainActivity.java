package res.yd.com.getapkres;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import res.yd.com.getapkres.manager.ParserManager;
import res.yd.com.getapkres.services.ParserService;


public class MainActivity extends Activity {
    private EditText packageNameEditView;
    private Button parserLayoutButton;
    private Button parseStringButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageNameEditView = (EditText) findViewById(R.id.main_parser_packagename_editview);

        parserLayoutButton = (Button) findViewById(R.id.main_parserlayout_button);
        parserLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParserService.Start(MainActivity.this, packageNameEditView.getText().toString().trim(), ParserManager.ResourceType.LAYOUT);
            }
        });

        parseStringButton = (Button) findViewById(R.id.main_parserstring_button);
        parseStringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParserService.Start(MainActivity.this, packageNameEditView.getText().toString().trim(), ParserManager.ResourceType.STRINGS);
            }
        });
    }

}
