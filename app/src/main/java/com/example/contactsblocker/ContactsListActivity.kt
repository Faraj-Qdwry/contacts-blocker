package com.example.contactsblocker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactsblocker.databinding.ActivityContactsListBinding

class ContactsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var viewModel: ContactsViewModel
    private lateinit var contactsAdapter: BlockedContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_list)

        viewModel = ContactsViewModel(application)

        binding.addNumberAction.setOnClickListener {
            startActivity(Intent(this, AddContactsActivity::class.java))
        }

        setupAdapter()
    }

    private fun setupAdapter() {
        contactsAdapter = BlockedContactsAdapter {
            viewModel.unblock(it)
        }

        binding.recyclerView.adapter = contactsAdapter

        viewModel.getBlockedContacts().observe(this, {
            contactsAdapter.setData(it!!)
        })
    }
}