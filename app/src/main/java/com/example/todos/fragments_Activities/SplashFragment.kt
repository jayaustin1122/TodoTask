package com.example.todos.fragments_Activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todos.R
import com.example.todos.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : FragmentSplashBinding
    private lateinit var navControl : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        navControl =  Navigation.findNavController(view)

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            //if user is currently login
            if (auth.currentUser != null){
                navControl.navigate(R.id.action_splashFragment_to_homeFragment3)
            }else{
                navControl.navigate(R.id.action_splashFragment_to_signinFragment3)
            }
        },3000)

    }

}