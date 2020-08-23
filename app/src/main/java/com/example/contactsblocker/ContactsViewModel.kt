package com.example.contactsblocker

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.contactsblocker.db.Contact
import com.example.contactsblocker.repo.ContactsRepository

class ContactsViewModel(applicationContext: Application) {
    private val repository = ContactsRepository(applicationContext)

    fun getBlockedContacts(): LiveData<List<Contact>> {
        return repository.getBlockedContacts()
    }

    fun blockContact(contact: Contact) {
        repository.blockContact(contact)
    }

    fun unblock(contact: Contact) {
        repository.unblock(contact)
    }
}