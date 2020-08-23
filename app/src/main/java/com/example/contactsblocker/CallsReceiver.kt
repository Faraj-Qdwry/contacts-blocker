package com.example.contactsblocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.android.internal.telephony.ITelephony
import com.example.contactsblocker.repo.ContactsRepository


class CallsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // If, the received action is not a type of "Phone_State", ignore it
        if (intent.action != "android.intent.action.PHONE_STATE") return else {
            // Fetch the number of incoming call
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (ContactsRepository(context).getBlockedContactsList().filter { it.number == number }.isNotEmpty()){
                disconnectPhoneItelephony(context)
            }
        }
    }

    // Method to disconnect phone automatically and programmatically
    // Keep this method as it is
    private fun disconnectPhoneItelephony(context: Context) {
        val telephonyService: ITelephony
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val c = Class.forName(telephony.javaClass.name)
            val m = c.getDeclaredMethod("getITelephony")
            m.isAccessible = true
            telephonyService = m.invoke(telephony) as ITelephony
            telephonyService.endCall()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}