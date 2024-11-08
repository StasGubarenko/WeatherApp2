package com.bignerdranch.android.weatherapp.presentation


import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomAdapter(
    context: Context,
    resource: Int,
    private var objects: MutableList<String>
) : ArrayAdapter<String>(context,resource,objects){

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(index: Int): String? {
        return objects[index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
             override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filterResults = FilterResults()
                if (constraint != null) {

                    objects.add(constraint.toString())

                    filterResults.values = objects
                    filterResults.count = objects.size
                }
                return filterResults
            }

             override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if ((results != null) && (results.count > 0)) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}