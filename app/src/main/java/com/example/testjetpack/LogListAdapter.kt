package com.example.testjetpack

import android.graphics.Color
import android.util.Log
import android.widget.BaseAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testjetpack.BPM.LogViewModel

class LogListAdapter() : BaseAdapter() {
    private val mLogData: MutableList<String>

    init {
        mLogData = ArrayList()
    }

    fun addLog(message: String, model: LogViewModel): MutableList<String> {
        Log.e("addLog", message)
        mLogData.add(message)
        model.addData(message)
        notifyDataSetChanged()
        return mLogData
    }

    fun getDeviceMac(position: Int): String {
        return mLogData[position]
    }

    fun clear() {
        mLogData.clear()
        notifyDataSetChanged()
    }

    fun remove(location: Int) {
        if (location < mLogData.size) {
            mLogData.removeAt(location)
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return mLogData.size
    }

    override fun getItem(i: Int): Any {
        return mLogData[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null
        // General ListView optimization code.
        if (view == null) {
            //view = mInflator.inflate(R.layout.log_item, null)
            viewHolder = ViewHolder()
            //viewHolder!!.message = view.findViewById<View>(R.id.message) as TextView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val message = mLogData[i]
        if (message != null) {
            if (message.startsWith("WRITE")) {
                viewHolder.message!!.setTextColor(Color.RED)
            } else if (message.startsWith("NOTIFY")) {
                viewHolder!!.message!!.setTextColor(Color.BLUE)
            } else {
                viewHolder!!.message!!.setTextColor(Color.GREEN)
            }
            viewHolder!!.message!!.text = message
        }
        return view
    }

    fun getList(): List<String> {
        return mLogData
    }

    internal inner class ViewHolder {
        //		TextView deviceName;
        var message: TextView? = null //		TextView device_rssi;
    }
}