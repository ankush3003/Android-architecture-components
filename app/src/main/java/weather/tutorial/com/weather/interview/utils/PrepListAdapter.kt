package weather.tutorial.com.weather.interview.utils

import android.widget.TextView


import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import weather.tutorial.com.weather.R
import weather.tutorial.com.weather.interview.data.PrepData

class PrepListAdapter (private val mContacts: List<PrepData>, private val onClickListener: RecyclerItemOnClickListener) : RecyclerView.Adapter<PrepListAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val problemNameText = itemView.findViewById<TextView>(R.id.prepItemName)
        //val problemIcon = itemView.findViewById<Button>(R.id.message_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrepListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contactView = inflater.inflate(R.layout.layout_prep_items, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: PrepListAdapter.ViewHolder, position: Int) {
        val prepData: PrepData = mContacts.get(position)
        val textView = viewHolder.problemNameText
        textView.setText(prepData.problemName)

        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(mContacts.get(position))
        }
    }

    override fun getItemCount(): Int {
        return mContacts.size
    }

    class RecyclerItemOnClickListener (val clickListener: (prepData: PrepData) -> Unit) {
        fun onClick(prepData: PrepData) = clickListener(prepData)
    }
}