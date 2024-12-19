/*package com.example.finalproject;

public class VideoItem {
    private String title;
    private String videoUri;
    private String thumbnailUrl;

    public VideoItem(String title, String videoUri, String thumbnailUrl) {
        this.title = title;
        this.videoUri = videoUri;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
*/

package com.example.finalproject;

public class VideoItem {
    private String title;
    private String videoUri; // The direct video URL
    private String thumbnailUrl; // URL for the video thumbnail
    private String googleDriveFileId; // Optional: Google Drive file ID

    // Constructor for non-Google Drive videos
    public VideoItem(String title, String videoUri, String thumbnailUrl) {
        this.title = title;
        this.videoUri = videoUri;
        this.thumbnailUrl = thumbnailUrl;
        this.googleDriveFileId = null; // Default to null
    }

    // Constructor for Google Drive videos
    public VideoItem(String title, String googleDriveFileId, String thumbnailUrl, boolean isGoogleDrive) {
        this.title = title;
        this.googleDriveFileId = googleDriveFileId;
        this.thumbnailUrl = thumbnailUrl;

        // If it's a Google Drive file, construct the direct video URL
        if (isGoogleDrive) {
            this.videoUri = "https://drive.google.com/uc?id=" + googleDriveFileId + "&export=download";
        }
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getGoogleDriveFileId() {
        return googleDriveFileId;
    }

    // Setters (optional, if needed)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setGoogleDriveFileId(String googleDriveFileId) {
        this.googleDriveFileId = googleDriveFileId;
        this.videoUri = "https://drive.google.com/uc?id=" + googleDriveFileId + "&export=download";
    }
}