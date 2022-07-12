package deploy.com.timelineschedule.preference

import android.content.Context
import android.content.SharedPreferences

class PrefManager (content: Context) {
    private val prefName = "TS2022.pref"
    private var sharedPref : SharedPreferences
    private val editor : SharedPreferences.Editor

    init {
        sharedPref = content.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put (key: String, value: String) {
        editor.putString(key,value).apply()
    }

    fun putToken (key: String, value: String) {
        editor.putString(key,value).apply()
    }

    fun put (key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getString (key: String) : String?{
        return sharedPref.getString(key, "")
    }
    fun getToken (key: String) : String?{
        return sharedPref.getString(key, "")
    }
    fun getInt (key : String?) : Int{
        return sharedPref.getInt(key, 0)
    }

    fun clearData(){
        editor.clear().apply()
    }
}