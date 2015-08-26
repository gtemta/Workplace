package com.example.pika.bargia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchActivity extends ActionBarActivity {

    private EditText searchInput;
    private ListView videosFound;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput =(EditText)findViewById(R.id.SearchText);
        videosFound=(ListView)findViewById(R.id.videos_found);

        handler =new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId!= EditorInfo.IME_ACTION_DONE)
                {
                    searchOnYoutube(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    private List<VideoItem> searchResults;

    private  void searchOnYoutube(final String keywords){
        new Thread(){
            public void run(){
                YoutubeConnector yc=new YoutubeConnector(SearchActivity.this);
                searchResults = yc.search(keywords);
                handler.post(new Runnable(){
                    public void run()
                    {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }


    private void updateVideosFound()
    {
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(),R.layout.video_item,searchResults){
            @Override
            public View getView (int position,View convertView ,ViewGroup parent ){
                if(convertView==null){
                    convertView=getLayoutInflater().inflate(R.layout.video_item,parent,false);
                }
                ImageView thumbnail =(ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title =(TextView)convertView.findViewById(R.id.video_title);
                TextView description =(TextView)convertView.findViewById(R.id.video_description);

                VideoItem searchResult= searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };
        videosFound.setAdapter(adapter);
    }

    private void addClickListener(){
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                //startActivitiy(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
