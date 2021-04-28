package com.example.filemanager.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filemanager.R

class HomeFragment: Fragment() {

    private lateinit var view: View
    override fun getView(): View? {
        return view
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.home_fragment,container,false)

        return view
    }
}