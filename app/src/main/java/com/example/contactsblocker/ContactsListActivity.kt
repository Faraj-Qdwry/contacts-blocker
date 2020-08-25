package com.example.contactsblocker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.databinding.ActivityContactsListBinding
import com.example.contactsblocker.di.ViewModelFactory

import javax.inject.Inject

class ContactsListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ContactsViewModel

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsAdapter: BlockedContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)

        //viewModel = ContactsViewModel(application)

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