package com.example.contactsblocker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsblocker.db.Contact

class BlockedContactsAdapter(val removeContact: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactViewHolder>() {

    private var data = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.numberText.text = data[position].number
        holder.removeBtn.setOnClickListener {
            removeContact(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Contact>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val numberText: TextView = view.findViewById(R.id.numberText)
    val removeBtn: Button = view.findViewById(R.id.removeBtn)
}