package com.example.contactsblocker.di

import android.app.Application
import android.content.Context
import com.example.contactsblocker.ContactsViewModel
import com.example.contactsblocker.repo.ContactsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContactsModule {

    @Provides
    @Singleton
    fun repo(application: Application) : ContactsRepository{
        return ContactsRepository(application)
    }

}