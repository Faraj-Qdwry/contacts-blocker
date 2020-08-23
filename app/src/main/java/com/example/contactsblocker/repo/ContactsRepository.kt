package com.example.contactsblocker.repo

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.contactsblocker.db.AppDatabase
import com.example.contactsblocker.db.Contact

class ContactsRepository(context: Context) {
    private val db = AppDatabase.getAppDb(context)

    fun getBlockedContacts(): LiveData<List<Contact>> {
        return db.contactDao().getAll()
    }

    fun getBlockedContactsList(): List<Contact> {
        return db.contactDao().getAllList()
    }

    fun blockContact(contact: Contact) {
        db.contactDao().insert(contact)
    }

    fun unblock(contact: Contact) {
        db.contactDao().delete(contact)
    }

}