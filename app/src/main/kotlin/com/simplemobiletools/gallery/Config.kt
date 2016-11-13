package com.simplemobiletools.gallery

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class Config private constructor(context: Context) {
    private val mPrefs: SharedPreferences

    companion object {
        fun newInstance(context: Context): Config {
            return Config(context)
        }
    }

    init {
        mPrefs = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
    }

    var isFirstRun: Boolean
        get() = mPrefs.getBoolean(Constants.IS_FIRST_RUN, true)
        set(isFirstRun) = mPrefs.edit().putBoolean(Constants.IS_FIRST_RUN, isFirstRun).apply()

    var isDarkTheme: Boolean
        get() = mPrefs.getBoolean(Constants.IS_DARK_THEME, true)
        set(isDarkTheme) = mPrefs.edit().putBoolean(Constants.IS_DARK_THEME, isDarkTheme).apply()

    var isSameSorting: Boolean
        get() = mPrefs.getBoolean(Constants.IS_SAME_SORTING, true)
        set(isSameSorting) = mPrefs.edit().putBoolean(Constants.IS_SAME_SORTING, isSameSorting).apply()

    var sorting: Int
        get() = if (isSameSorting) directorySorting else mPrefs.getInt(Constants.SORT_ORDER, Constants.SORT_BY_DATE or Constants.SORT_DESCENDING)
        set(order) = if (isSameSorting) directorySorting = order else mPrefs.edit().putInt(Constants.SORT_ORDER, order).apply()

    var directorySorting: Int
        get() = mPrefs.getInt(Constants.DIRECTORY_SORT_ORDER, Constants.SORT_BY_DATE or Constants.SORT_DESCENDING)
        set(order) = mPrefs.edit().putInt(Constants.DIRECTORY_SORT_ORDER, order).apply()

    var showHiddenFolders: Boolean
        get() = mPrefs.getBoolean(Constants.SHOW_HIDDEN_FOLDERS, false)
        set(showHiddenFolders) = mPrefs.edit().putBoolean(Constants.SHOW_HIDDEN_FOLDERS, showHiddenFolders).apply()

    fun addHiddenDirectory(path: String) {
        val hiddenFolders = hiddenFolders
        hiddenFolders.add(path)
        mPrefs.edit().putStringSet(Constants.HIDDEN_FOLDERS, hiddenFolders).apply()
    }

    fun addHiddenDirectories(paths: Set<String>) {
        val hiddenFolders = hiddenFolders
        hiddenFolders.addAll(paths)
        mPrefs.edit().putStringSet(Constants.HIDDEN_FOLDERS, hiddenFolders).apply()
    }

    fun removeHiddenDirectory(path: String) {
        val hiddenFolders = hiddenFolders
        hiddenFolders.remove(path)
        mPrefs.edit().putStringSet(Constants.HIDDEN_FOLDERS, hiddenFolders).apply()
    }

    fun removeHiddenDirectories(paths: Set<String>) {
        val hiddenFolders = hiddenFolders
        hiddenFolders.removeAll(paths)
        mPrefs.edit().putStringSet(Constants.HIDDEN_FOLDERS, hiddenFolders).apply()
    }

    val hiddenFolders: MutableSet<String>
        get() = mPrefs.getStringSet(Constants.HIDDEN_FOLDERS, HashSet<String>())

    fun getIsFolderHidden(path: String): Boolean {
        return hiddenFolders.contains(path)
    }

    var autoplayVideos: Boolean
        get() = mPrefs.getBoolean(Constants.AUTOPLAY_VIDEOS, false)
        set(autoplay) = mPrefs.edit().putBoolean(Constants.AUTOPLAY_VIDEOS, autoplay).apply()

    var treeUri: String
        get() = mPrefs.getString(Constants.TREE_URI, "")
        set(uri) = mPrefs.edit().putString(Constants.TREE_URI, uri).apply()

    var displayFileNames: Boolean
        get() = mPrefs.getBoolean(Constants.DISPLAY_FILE_NAMES, false)
        set(display) = mPrefs.edit().putBoolean(Constants.DISPLAY_FILE_NAMES, display).apply()
}
