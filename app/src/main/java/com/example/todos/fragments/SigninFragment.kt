package com.example.todos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todos.R
import com.example.todos.databinding.FragmentSigninBinding
import com.example.todos.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SigninFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var navControl : NavController
    private lateinit var binding: FragmentSigninBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        registerEvent()
    }

    private fun registerEvent() {

        binding.tvCreate.setOnClickListener{
            navControl.navigate(R.id.action_signinFragment_to_signupFragment)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()){

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context,"Login Successfully", Toast.LENGTH_SHORT).show()
                        navControl.navigate(R.id.action_signinFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context,it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun init(view: View) {
        navControl =  Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

}