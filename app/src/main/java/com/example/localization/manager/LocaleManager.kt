package com.example.localization.manager

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.LinkedHashSet

class LocaleManager(context: Context) {
    private val sharedPreferences : SharedPreferences?

    init {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    companion object{
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_UZBEK = "uz"
        const val LANGUAGE_CHINESE = "bo"
        const val LANGUAGE_KOREAN = "ko"
        const val LANGUAGE_JAPANESE = "ja"

        private const val LANGUAGE_KEY = "language_key"

        fun getLocale(res : Resources) : Locale{
            val configuration = res.configuration
            return if(isAtLeastVersion(Build.VERSION_CODES.N)) configuration.locales[0] else configuration.locale
        }

        private fun isAtLeastVersion(version: Int): Boolean {
            return Build.VERSION.SDK_INT >= version
        }
    }

    val language : String?
        get() = sharedPreferences!!.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH)

    fun setLocale(context: Context) : Context{
        return update(context, language)
    }

    private fun update(context: Context, language: String?): Context {
        updateResources(context, language)
        val applicationContext = context.applicationContext
        if(context !==applicationContext){
            updateResources(applicationContext, language)
        }
        return applicationContext
    }

    // to set new language
    fun setNewLocale(context: Context, language: String){
        persistLanguage(language)
        update(context, language)
    }

    private fun persistLanguage(language: String) {
        //use commit() instead of apply() because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        val editor = sharedPreferences!!.edit()
        editor.putString(LANGUAGE_KEY, language)
        editor.commit()
    }

    //to set Local
    private fun updateResources(context: Context, language: String?) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val configuration = Configuration(res.configuration)
        if(isAtLeastVersion(Build.VERSION_CODES.N)){
            setLocaleForApi24(configuration, locale)
        }else if(isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)){
            configuration.setLocale(locale)
        }else{
            configuration.locale = locale
        }
        res.updateConfiguration(configuration, res.displayMetrics)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setLocaleForApi24(configuration: Configuration, target: Locale) {
        val set : MutableSet<Locale> = LinkedHashSet()
        //bring the target locale to the front of the list
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()){
            //append other locales supported by the user
            set.add(all[i])
        }
        val locales = set.toTypedArray()
        configuration.setLocales(LocaleList(*locales))
    }

}