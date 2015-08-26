package com.example.pika.bargia;

/**
 * Created by Pika on 2015/6/29.
 */
public class VideoItem {
    private String title;
    private String description;
    private String thumbnailURL;
    private String id;


    public String getId()
    {        return id;    }
    public void SetId(String id)
    {        this.id=id;   }

    public String getTitle()
    {       return title;   }
    public void SetTitle(String title)
    {       this.title=title;}

    public String getDescription()
    {       return description;}
    public void  setDescription(String description)
    {       this.description= this.description;}

    public String getThumbnailURL()
    {       return thumbnailURL;       }
    public void setThumbnailURL(String url)
    {       this.thumbnailURL=thumbnailURL;}
}
