package com.example.contactsblocker

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactsblocker.databinding.ActivityAddNumberBinding
import com.example.contactsblocker.db.Contact


class AddContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_number)
        binding.viewModel = ContactsViewModel(application)
        binding.addNumberToBlock.setOnClickListener {
            selectContact()
        }
        binding.blockButton.setOnClickListener {
            if (binding.numberInput.text.isNotEmpty()) {
                blockContactNumber(binding.numberInput.text.toString())
            } else {
                binding.numberInput.error = "please insert a number to block!"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                selectContact()
            }
        }
    }

    private fun selectContact() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        when (reqCode) {
            PICK_CONTACT -> if (resultCode == Activity.RESULT_OK) {
                val contactData: Uri? = data?.data
                handleContactData(contactData)
            }
        }
    }

    private fun handleContactData(contactData: Uri?) {
        val cursor: Cursor = managedQuery(contactData, null, null, null, null)
        if (cursor.moveToFirst()) {
            val id: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val hasPhone: String =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            if (hasPhone.equals("1", ignoreCase = true)) {
                val phones: Cursor? = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null
                )
                phones?.moveToFirst()
                val selectedNumber = phones?.getString(phones.getColumnIndex("data1"))
                if (!selectedNumber.isNullOrEmpty()) {
                    blockContactNumber(selectedNumber)
                } else {
                    Toast.makeText(this, "no number found", Toast.LENGTH_SHORT).show()
                }
            }
            val name: String =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
        }
    }

    private fun blockContactNumber(selectedNumber: String) {
        binding.viewModel?.blockContact(Contact(0, selectedNumber))
        Toast.makeText(this, "$selectedNumber blocked", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PICK_CONTACT = 1
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 2
    }
}