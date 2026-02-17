package com.portfolio.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.portfolio.app.models.DesignerProfile;
import com.portfolio.app.models.PortfolioItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {

    private static final String PREFS_NAME = "PortfolioAppPrefs";
    private static final String KEY_PORTFOLIO_ITEMS = "portfolio_items";
    private static final String KEY_DESIGNER_PROFILE = "designer_profile";
    private static final String KEY_ADMIN_PASSWORD = "admin_password";
    private static final String KEY_CATEGORIES = "categories";

    private static DataManager instance;
    private final SharedPreferences prefs;
    private final Gson gson;

    private DataManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    // ---- Portfolio Items ----

    public List<PortfolioItem> getPortfolioItems() {
        String json = prefs.getString(KEY_PORTFOLIO_ITEMS, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<PortfolioItem>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void savePortfolioItems(List<PortfolioItem> items) {
        prefs.edit().putString(KEY_PORTFOLIO_ITEMS, gson.toJson(items)).apply();
    }

    public void addPortfolioItem(PortfolioItem item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(UUID.randomUUID().toString());
        }
        List<PortfolioItem> items = getPortfolioItems();
        items.add(0, item);
        savePortfolioItems(items);
    }

    public void updatePortfolioItem(PortfolioItem updatedItem) {
        List<PortfolioItem> items = getPortfolioItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                break;
            }
        }
        savePortfolioItems(items);
    }

    public void deletePortfolioItem(String itemId) {
        List<PortfolioItem> items = getPortfolioItems();
        items.removeIf(item -> item.getId().equals(itemId));
        savePortfolioItems(items);
    }

    public PortfolioItem getPortfolioItemById(String id) {
        List<PortfolioItem> items = getPortfolioItems();
        for (PortfolioItem item : items) {
            if (item.getId().equals(id)) return item;
        }
        return null;
    }

    public List<String> getCategories() {
        String json = prefs.getString(KEY_CATEGORIES, null);
        if (json == null) {
            List<String> defaults = new ArrayList<>();
            defaults.add("All");
            defaults.add("Branding");
            defaults.add("UI/UX");
            defaults.add("Print");
            defaults.add("Motion");
            defaults.add("Illustration");
            defaults.add("Photography");
            return defaults;
        }
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addCategory(String category) {
        List<String> categories = getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            prefs.edit().putString(KEY_CATEGORIES, gson.toJson(categories)).apply();
        }
    }

    // ---- Designer Profile ----

    public DesignerProfile getDesignerProfile() {
        String json = prefs.getString(KEY_DESIGNER_PROFILE, null);
        if (json == null) return new DesignerProfile();
        return gson.fromJson(json, DesignerProfile.class);
    }

    public void saveDesignerProfile(DesignerProfile profile) {
        prefs.edit().putString(KEY_DESIGNER_PROFILE, gson.toJson(profile)).apply();
    }

    // ---- Admin Auth ----

    public String getAdminPassword() {
        return prefs.getString(KEY_ADMIN_PASSWORD, "admin123");
    }

    public void setAdminPassword(String password) {
        prefs.edit().putString(KEY_ADMIN_PASSWORD, password).apply();
    }

    public boolean verifyAdminPassword(String password) {
        return getAdminPassword().equals(password);
    }
}
