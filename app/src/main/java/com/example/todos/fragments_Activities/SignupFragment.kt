package com.example.todos.fragments_Activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todos.R
import com.example.todos.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var navControl : NavController
    private lateinit var binding : FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        registerEvent()
    }

    private fun registerEvent() {

        binding.tvSingIn.setOnClickListener{
            navControl.navigate(R.id.action_signupFragment_to_signinFragment)
        }
        binding.btnSignUp.setOnClickListener {
            val email = binding.etSignUpEmail.text.toString().trim()
            val pass = binding.etSignUpPassword.text.toString().trim()
            val verifyPass = binding.etSignUpPassword2.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()&& verifyPass.isNotEmpty()){
                if (pass == verifyPass){
                     binding.progressBar.visibility = View.VISIBLE
                     auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                         if (it.isSuccessful) {
                             Toast.makeText(context,"Registered Successfully", Toast.LENGTH_SHORT).show()
                             navControl.navigate(R.id.action_signupFragment_to_signinFragment)
                         } else {
                             Toast.makeText(context,it.exception?.message, Toast.LENGTH_SHORT).show()
                         }
                         binding.progressBar.visibility = View.GONE
                     }
                }else{
                    Toast.makeText(context,"Password Doesn't match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Dont Leave a Blank Text Input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(view: View) {
        navControl =  Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }
}