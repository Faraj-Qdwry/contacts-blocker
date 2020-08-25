package com.example.contactsblocker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contactsblocker.db.Contact
import com.example.contactsblocker.repo.ContactsRepository
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val repository: ContactsRepository) :
    ViewModel() {

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