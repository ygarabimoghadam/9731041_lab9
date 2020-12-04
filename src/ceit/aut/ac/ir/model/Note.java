package ceit.aut.ac.ir.model;

import java.io.Serializable;

// TODO: Phase2: uncomment this code
//done
public class Note implements Serializable {

    private String title;
    private String content;
    private String date;

    public Note(String title, String content, String date) {
        this.title = title;
       this.content = content;
        this.date = date;
    }
     public Note (){}

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
   public String toString() {
   return "Note{" +
               "title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", date='" + date + '\'' +
               '}';
    }

}

