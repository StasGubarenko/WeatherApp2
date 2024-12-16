package com.bignerdranch.android.weatherapp.presentation


import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomAdapter(
    context: Context,
    resource: Int,
) : ArrayAdapter<String>(context,resource){

   private var initObjects : MutableList<String>

    init {
        initObjects = mutableListOf()
    }

    fun update(outerObject: List<String>){
        initObjects = outerObject.toMutableList()
    }

    override fun getCount(): Int {
        return initObjects.size
    }

    override fun getItem(index: Int): String? {
        return initObjects[index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
             override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filterResults = FilterResults()
                if (constraint != null) {

                    initObjects.add(constraint.toString())

                    filterResults.values = initObjects
                    filterResults.count = initObjects.size
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