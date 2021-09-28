package org.bedu.bedushop

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


class LogInFragment : Fragment() {

    private lateinit var metEmail : TextInputLayout
    private lateinit var metPassword : TextInputLayout
    private lateinit var btnLogin : Button
    private lateinit var tvRegister : TextView

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
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        metEmail = view.findViewById(R.id.metEmail)
        metPassword = view.findViewById(R.id.metPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        tvRegister = view.findViewById(R.id.tvRegister)

        val email = args.email
        val password = args.password
        metEmail.editText?.setText(email)
        metPassword.editText?.setText(password)

        btnLogin.setOnClickListener {
            if (!isValidEmail(metEmail.editText?.text.toString()) && !isValidPassword(metPassword.editText?.text.toString())){
                metEmail.error = getString(R.string.errorEmail)
                metPassword.error = getString(R.string.errorPassword)
            } else if (!isValidEmail(metEmail.editText?.text.toString())){
                metEmail.error = getString(R.string.errorEmail)
            } else if (!isValidPassword(metPassword.editText?.text.toString())){
                metPassword.error = getString(R.string.errorPassword)
            } else {
                findNavController().navigate(R.id.action_logInFragment_to_homeActivity)
            }
        }

        metEmail.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(metEmail.error?.isNotBlank() == true) metEmail.error = ""
            }
        })

        metPassword.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(metPassword.error?.isNotBlank() == true) metPassword.error = ""
            }
        })

        tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment, null, options)
        }

        return view
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return target.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches() && password.isNotBlank()
    }

}