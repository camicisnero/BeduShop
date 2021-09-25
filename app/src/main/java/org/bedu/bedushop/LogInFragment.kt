package org.bedu.bedushop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputLayout

class LogInFragment : Fragment() {

    private lateinit var met_email : TextInputLayout
    private lateinit var met_password : TextInputLayout
    private lateinit var btn_login : Button
    private lateinit var tv_register : TextView

    private val args: LogInFragmentArgs by navArgs()

    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        met_email = view.findViewById(R.id.met_email)
        met_password = view.findViewById(R.id.met_password)
        btn_login = view.findViewById(R.id.btn_login)
        tv_register = view.findViewById(R.id.tv_register)

        val email = args.email
        met_email.editText?.setText(email)

        btn_login.setOnClickListener {
            if (met_email.editText?.text.toString().isBlank()){
                met_email.error = getString(R.string.errorEmail)
            } else if (met_password.editText?.text.toString().isBlank()){
                met_password.error = getString(R.string.errorPassword)
            } else {
                findNavController().navigate(R.id.action_logInFragment_to_homeActivity)
            }
        }

        tv_register.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment, null, options)
        }

        return view
    }

}