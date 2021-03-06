package com.example.pika.bargia;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pika on 2015/6/29.
 */
public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

    public static final String KEY
            =  "AIzaSyABcQYiDXlNShljKRxZymZ-XOKDbk9Ve3c";
    public YoutubeConnector(Context content){
        youtube= new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest httpRequest) throws IOException {}
        }).setApplicationName(content.getString(R.string.app_name)).build();

        try{
            query= youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            } catch (IOException e)
                {
                    Log.d("YC","Could not initialize: "+e);
                }
            }


    public List<VideoItem> search(String keywords){
        query.setQ(keywords);
        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.SetTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.SetId(result.getId().getVideoId());
                items.add(item);
            }
            return items;
        }catch (IOException e){
            Log.d("YC","Could not search: " +e);
            return null;

        }

    }


}

