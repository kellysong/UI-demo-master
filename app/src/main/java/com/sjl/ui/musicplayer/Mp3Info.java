package com.sjl.ui.musicplayer;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename Mp3Info.java
 * @time 2018/10/5 18:56
 * @copyright(C) 2018 xxx有限公司
 */
public class Mp3Info {

    private String url;//路径
    private String title;//歌曲名
    private String artist;//艺术家/作者
    private long duration;//歌曲时长
    private long id;
    private long albumId;//以上两种Id用以获取专辑图片

    public Mp3Info() {

    }

    public Mp3Info(String url, String title, String artist, long duration, long id, long albumId) {
        this.url = url;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.id = id;
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return "Mp3Info{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", id=" + id +
                ", albumId=" + albumId +
                '}';
    }
}
