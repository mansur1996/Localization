package com.example.localization.activity

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localization.MyApplication
import com.example.localization.R
import com.example.localization.databinding.ActivityLanguageBinding
import com.example.localization.manager.LocaleManager
import java.util.*

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        initViews()
    }

    private fun initViews() {
        binding.btnEn.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_ENGLISH)
            finish()
        }
        binding.btnRu.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_RUSSIAN)
            finish()
        }
        binding.btnUz.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_UZBEK)
            finish()
        }
        binding.btnBoChina.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_CHINESE)
            finish()
        }
        binding.btnKo.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_KOREAN)
            finish()
        }
        binding.btnJa.setOnClickListener {
            MyApplication.localeManager!!.setNewLocale(context, LocaleManager.LANGUAGE_JAPANESE)
            finish()
        }

        // one = 1
        val one = resources.getQuantityString(R.plurals.my_car, 1,1)
        // few = 2-4
        val few = resources.getQuantityString(R.plurals.my_car, 2,3)
        // many = 5~
        val many = resources.getQuantityString(R.plurals.my_car, 5,10)

        binding.tv.text = "$one \n $few \n$many"


    }
}