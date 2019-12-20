package com.zs.test.recycleview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2019-11-19 15:36
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class RecycleViewFragment : BaseFragment() {

    var recycleView: RecyclerView? = null

    override fun createViewId(): Int {
        return R.layout.recycle_view_layout
    }

    override fun initView(view: View) {
        recycleView = view.findViewById(R.id.test_recycle_view)

        val layoutManager = LinearLayoutManager(activity)
        val adapter = TestAdapter()
        recycleView?.layoutManager = layoutManager
        recycleView?.adapter = adapter
    }


    inner class TestAdapter : RecyclerView.Adapter<TestViewHolder>() {

        var dataList  = ArrayList<String>()

        init {
            var index = 0
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
            dataList.add(index++.toString())
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)

            return TestViewHolder(view)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            holder.testText?.text = dataList.get(position)
        }

    }


    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var testText: TextView? = null

        init {
            testText = itemView.findViewById(R.id.test_tv)
        }

    }

}