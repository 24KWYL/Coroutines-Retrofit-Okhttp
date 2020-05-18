package com.common.coroutines_retrofit_okhttp

import MainAdapter
import MainViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        mModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mModel.data?.observe(this, Observer {
            val mainAdapter = MainAdapter(this, it)
            val linearLayoutManager = LinearLayoutManager(this)
            rv.layoutManager = linearLayoutManager
            rv.adapter = mainAdapter
        })
        mModel.getDataFromServer()
    }

}
