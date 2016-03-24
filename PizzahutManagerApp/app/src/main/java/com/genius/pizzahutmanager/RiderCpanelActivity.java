package com.genius.pizzahutmanager;


        import android.app.Activity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class RiderCpanelActivity extends Activity{
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ridercpanel);

        toolbar= (Toolbar) findViewById(R.id.toolbarriderm);
        toolbar.setTitle("Rider Management");
        toolbar.setSubtitle("Personal details");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

        RiderDetails rider = (RiderDetails)getIntent().getSerializableExtra("rider");


        Button btn=(Button)findViewById(R.id.remove);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "button clicked", Toast.LENGTH_SHORT).show();

            }
        });

        TextView name=(TextView)findViewById(R.id.naemtext);
        TextView uname=(TextView)findViewById(R.id.unaemtext);
        TextView nic=(TextView)findViewById(R.id.nictext);
        TextView phone=(TextView)findViewById(R.id.phonetext);
        TextView status=(TextView)findViewById(R.id.statustext);
        TextView rating=(TextView)findViewById(R.id.ratingtext);
        TextView delivery=(TextView)findViewById(R.id.deliverytext);

        name.setText(rider.getRiderName());
        uname.setText(rider.getRiderUname());
        nic.setText(rider.getRiderNic());
        phone.setText(rider.getRiderPhone());
        status.setText(rider.getRiderStatus());
        rating.setText(rider.getRiderRating());
        delivery.setText(rider.getRiderDelivery());


    }
}