package com.example.kotlin_weather.view.contentProvider


import android.content.pm.PackageManager
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                // (Если разрешение не было предоставлено, но пользователь ранее не отозвал его,окно еще не вызывалось)
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Для работы необходимо выдать доступ")
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



    private fun getContacts(){
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}