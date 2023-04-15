package com.example.kotlin_weather.view.contentProvider


import android.content.pm.PackageManager
import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kotlin_weather.databinding.FragmentContentProviderBinding

class ContentProviderFragment : Fragment() {
    private var _binding : FragmentContentProviderBinding? = null
    private val binding get() = _binding!!
    
    
    companion object{
        fun newInstance()  = ContentProviderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        (PackageManager.PERMISSION_GRANTED) -> {
                    getContacts()
                }
                //если нужно пояснение перед запросом
                // (Если пользователь отклюнил разрешение разрешение)
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Для работы необходимо выдать доступ, иначе здесь будет пустой экран")
                        .setPositiveButton("Предоставить доступ") { dialog, which ->
                            myRequestPermission()
                        }
                        .setNegativeButton("Не предоставлять") { dialog, which ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
                else -> {
                    // Запросить разрешение(Если разрешение на чтение контактов было отозвано пользователем)
                    myRequestPermission()
                }
            }
        }
    }

    private val REQUEST_CODE = 50

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage("Для работы необходимо выдать доступ")
                            .setPositiveButton("Предоставить доступ") { dialog, which ->
                                myRequestPermission()
                            }
                            .setNegativeButton("Не предоставлять") { dialog, which ->
                                dialog.dismiss()
                               // closeThisFragment()
                            }
                            .create()
                            .show()
                    }
                }
            }
        }

    }

    private fun closeThisFragment(){
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    private fun getContacts(){
        context?.let {
            val contentResolver : ContentResolver = it.contentResolver
            val cursorWithContacts : Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME+" ASC")

            cursorWithContacts?.let {cursor ->
                for (i in 0..cursor.count){
                    if( cursor.moveToPosition(i)){
                        val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (nameIndex >= 0){
                            val name = cursor.getString(nameIndex)
                            binding.containerForContacts.addView(TextView(it).apply {
                                text = name
                                textSize = 30f
                            })
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}