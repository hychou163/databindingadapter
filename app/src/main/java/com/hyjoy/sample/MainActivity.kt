package com.hyjoy.sample

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hyjoy.databinding.adapter.DataBindRecycleViewAdapter
import com.hyjoy.sample.databinding.ActivityMainBinding

/**
 *测试
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private val adapter: DataBindRecycleViewAdapter<String> by lazy {
        DataBindRecycleViewAdapter<String>(mContext, R.layout.item_main_layout, BR.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mContext = this
        binding.adapter = adapter
        var datas = ArrayList<String>()

        for (i in 1..100) {
            datas.add("" + i)
        }
        (binding.adapter as DataBindRecycleViewAdapter<String>).setData(datas)
    }
}
