package com.example.acadaptercustomproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import java.util.*

class AutoCompleteAdapter(
    context: Context,
    var allTypes: MutableList<ACAdapterCustomProjectUIModel>,
    private val listener: ACAdapterCustomProjectListener,
    private val view: Int
) : ArrayAdapter<ACAdapterCustomProjectUIModel>(context, view, allTypes),
    Filterable {

    private var currentTypes = allTypes

    override fun getCount(): Int {
        return currentTypes.size
    }

    override fun getItem(position: Int): ACAdapterCustomProjectUIModel {
        return currentTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return currentTypes[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context)
            .inflate(view, parent, false) as TextView
        view.text = currentTypes[position].name
        view.setOnClickListener { listener.onItemSelected(getItem(position)) }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                currentTypes = filterResults.values as MutableList<ACAdapterCustomProjectUIModel>

//                val size = currentTypes.size
//                val id = if (size >= 1) {
//                    currentTypes[0].id.toInt() //return id first element if list size >1
//                } else -1
//
//                listener.onTypeSelected(type = TypeListenerData(size, id))
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())
                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty()) {
                    allTypes
                } else {
                    allTypes.filter {
                        it.name.lowercase(Locale.getDefault()).contains(queryString)
                    }
                }
                return filterResults
            }
        }
    }
}

interface ACAdapterCustomProjectListener {
    fun onItemSelected(item : ACAdapterCustomProjectUIModel)
}