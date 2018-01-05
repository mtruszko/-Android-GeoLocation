package com.example.maro.prj4and.Map

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.maro.prj4and.Map.PlaceListFragment.OnListFragmentInteractionListener
import com.example.maro.prj4and.Map.dummy.DummyContent.DummyItem
import com.example.maro.prj4and.Place.Place
import com.example.maro.prj4and.R

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPlaceRecyclerViewAdapter(private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyPlaceRecyclerViewAdapter.ViewHolder>() {

    var pois = listOf<Place>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = pois.get(position)
        holder.mIdView.setText("Name: " + pois.get(position).name + " \nDesc: " + pois.get(position).desc + " \nRadius: " + pois.get(position).radius)
        holder.mContentView.setText("La: " + pois.get(position).la + " \nLo:" + pois.get(position).lo)

        holder.mView.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener!!.onListFragmentInteraction(holder.mItem)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return pois.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: Place? = null

        init {
            mIdView = mView.findViewById(R.id.id) as TextView
            mContentView = mView.findViewById(R.id.content) as TextView
        }

        public override fun toString(): String {
            return super.toString() + " '" + mContentView.getText() + "'"
        }
    }
}
