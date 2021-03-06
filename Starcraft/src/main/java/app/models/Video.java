package app.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "videos")
public class Video {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Basic
    private String name;

    @Basic
    private int likes;

    @Basic
    private int dislikes;

    @Basic
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "video", targetEntity = VideoComment.class, fetch = FetchType.EAGER,
            orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<VideoComment> videoComments;

    public Video() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Set<VideoComment> getVideoComments() {
        return videoComments;
    }

    public void setVideoComments(Set<VideoComment> videoComments) {
        this.videoComments = videoComments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
