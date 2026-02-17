package com.portfolio.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PortfolioItem implements Serializable {

    private String id;
    private String title;
    private String description;
    private String category;
    private String client;
    private String year;
    private List<MediaItem> mediaItems;
    private long createdAt;

    public PortfolioItem() {
        this.mediaItems = new ArrayList<>();
        this.createdAt = System.currentTimeMillis();
    }

    public PortfolioItem(String id, String title, String description, String category,
                         String client, String year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.client = client;
        this.year = year;
        this.mediaItems = new ArrayList<>();
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public List<MediaItem> getMediaItems() { return mediaItems; }
    public void setMediaItems(List<MediaItem> mediaItems) { this.mediaItems = mediaItems; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getThumbnailUri() {
        if (mediaItems != null && !mediaItems.isEmpty()) {
            for (MediaItem item : mediaItems) {
                if (item.getType() == MediaItem.TYPE_IMAGE) {
                    return item.getUri();
                }
            }
            return mediaItems.get(0).getUri();
        }
        return null;
    }

    public static class MediaItem implements Serializable {
        public static final int TYPE_IMAGE = 0;
        public static final int TYPE_VIDEO = 1;

        private String uri;
        private int type;
        private String caption;

        public MediaItem() {}

        public MediaItem(String uri, int type, String caption) {
            this.uri = uri;
            this.type = type;
            this.caption = caption;
        }

        public String getUri() { return uri; }
        public void setUri(String uri) { this.uri = uri; }

        public int getType() { return type; }
        public void setType(int type) { this.type = type; }

        public String getCaption() { return caption; }
        public void setCaption(String caption) { this.caption = caption; }
    }
}
