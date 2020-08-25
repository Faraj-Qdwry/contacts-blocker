package com.example.contactsblocker.di

import com.example.contactsblocker.AddContactsActivity
import com.example.contactsblocker.ContactsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeContactsListActivity(): ContactsListActivity

    @ContributesAndroidInjector()
    abstract fun contributeAddContactsActivity(): AddContactsActivity

}