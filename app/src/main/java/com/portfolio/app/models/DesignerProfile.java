package com.portfolio.app.models;

public class DesignerProfile {
    private String name;
    private String tagline;
    private String bio;
    private String email;
    private String phone;
    private String website;
    private String instagram;
    private String behance;
    private String profileImageUri;

    public DesignerProfile() {
        // Defaults
        this.name = "Alex Morgan";
        this.tagline = "Creative Visual Designer";
        this.bio = "Passionate graphic designer with 8+ years of experience crafting visual stories. Specializing in brand identity, UI/UX, and motion graphics.";
        this.email = "alex@designstudio.com";
        this.phone = "+1 (555) 123-4567";
        this.website = "www.alexmorgan.design";
        this.instagram = "@alexmorgandesign";
        this.behance = "behance.net/alexmorgan";
        this.profileImageUri = "";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getBehance() { return behance; }
    public void setBehance(String behance) { this.behance = behance; }

    public String getProfileImageUri() { return profileImageUri; }
    public void setProfileImageUri(String profileImageUri) { this.profileImageUri = profileImageUri; }
}
