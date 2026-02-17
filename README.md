# 🎨 Graphic Designer Portfolio App — Android

A complete, professional Android portfolio application for graphic designers. Features a stunning dark-themed portfolio viewer and a full admin panel for content management.

---

## ✨ Features

### Portfolio (Public View)
- **Splash screen** with animated logo
- **Designer profile header** with photo, name, and tagline
- **Category filter chips** (Branding, UI/UX, Print, Motion, etc.)
- **Grid portfolio view** with thumbnails
- **Project detail screen** with:
  - Horizontal scrollable media gallery
  - Images (full resolution)
  - Videos (inline playback with ExoPlayer)
  - Project info: title, category, client, year, description
- **Fullscreen media viewer** for both images and videos

### Admin Panel (Password Protected)
- **Password login** (default: `admin123`)
- **Dashboard** with all projects list
- **Add new project** with:
  - Title, description, category, client, year
  - Pick multiple images from gallery
  - Pick multiple videos from gallery
  - Create new custom categories
- **Edit existing projects**
- **Delete projects** (with confirmation)
- **Edit designer profile**: name, tagline, bio, email, phone, website, Instagram, Behance, profile photo
- **Change admin password**

---

## 🛠 Tech Stack

| Component | Library |
|-----------|---------|
| Image loading | Glide 4.16 |
| Video playback | ExoPlayer (Media3) |
| Data storage | SharedPreferences + Gson |
| UI components | Material Design 3 |
| Profile image | CircleImageView |
| Min SDK | Android 7.0 (API 24) |
| Target SDK | Android 14 (API 34) |

---

## 📁 Project Structure

```
app/src/main/java/com/portfolio/app/
├── activities/
│   ├── SplashActivity.java          # Animated launch screen
│   ├── MainActivity.java            # Portfolio grid + filter
│   ├── ProjectDetailActivity.java   # Project details + media
│   ├── FullScreenMediaActivity.java # Image/video fullscreen
│   ├── AdminLoginActivity.java      # Password protected entry
│   ├── AdminDashboardActivity.java  # Project management list
│   ├── AddProjectActivity.java      # Add/edit project form
│   └── EditProfileActivity.java     # Designer profile editor
├── adapters/
│   ├── PortfolioAdapter.java        # Grid items
│   ├── MediaAdapter.java            # Horizontal media scroll
│   ├── AddedMediaAdapter.java       # Media preview in form
│   └── AdminPortfolioAdapter.java   # Admin list
├── models/
│   ├── PortfolioItem.java           # Project + MediaItem model
│   └── DesignerProfile.java         # Designer info model
└── utils/
    └── DataManager.java             # All data persistence
```

---

## 🚀 How to Build

### Requirements
- Android Studio Hedgehog or newer
- JDK 11+
- Android SDK with API 34

### Steps

1. **Clone/copy** the project folder into Android Studio
2. **Sync Gradle** (File → Sync Project with Gradle Files)
3. **Run** on a device or emulator (API 24+)

### First Launch
- Default admin password: **`admin123`**
- Access admin panel via the person icon (⊙) in the top-right of the main screen
- Change the password from Admin Dashboard → overflow menu → "Change Password"

---

## 🎨 Design Theme

The app uses a dark, professional color scheme:

| Color | Hex | Usage |
|-------|-----|-------|
| Background | `#1A1A2E` | App background |
| Surface | `#16213E` | Cards |
| Accent | `#E94560` | Highlights, buttons |
| Text Primary | `#FFFFFF` | Main text |
| Text Secondary | `#B0B0C0` | Subtitles |

---

## 🔒 Security Notes

- Admin password is stored in SharedPreferences (local device only)
- Media URIs use persistent content resolver permissions
- For production use, consider adding biometric authentication

---

## 📱 Screenshots Guide

| Screen | Description |
|--------|-------------|
| Splash | Logo fade-in animation |
| Main | Grid of projects with category filter chips |
| Detail | Horizontal scrollable media + project info |
| Fullscreen | Immersive image/video viewer |
| Admin Login | Password entry |
| Dashboard | List of all projects with edit/delete |
| Add Project | Full form with media picker |
| Edit Profile | Designer info + photo picker |

---

## 🔧 Customization

### Adding Default Categories
Edit `DataManager.getCategories()` to change default categories.

### Changing Default Password
Edit `DataManager.getAdminPassword()` to change the default password.

### Changing Colors
Edit `res/values/colors.xml` — the primary accent color is `#E94560`.
