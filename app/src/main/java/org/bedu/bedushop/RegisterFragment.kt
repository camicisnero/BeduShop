package org.bedu.bedushop

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var regFullName : TextInputLayout
    private lateinit var regEmail : TextInputLayout
    private lateinit var regPhone : TextInputLayout
    private lateinit var regPassword : TextInputLayout
    private lateinit var btnRegister : Button

    val options = navOptions {
        anim {
            enter = R.anim.slide_in_left
            exit = R.anim.slide_out_right
            popEnter = R.anim.slide_in_right
            popExit = R.anim.slide_out_left
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        regFullName = view.findViewById(R.id.regFullName)
        regEmail = view.findViewById(R.id.regEmail)
        regPhone = view.findViewById(R.id.regPhone)
        regPassword = view.findViewById(R.id.regPassword)
        btnRegister = view.findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            if(validateAllFalse(regFullName.editText, regEmail.editText, regPhone.editText, regPassword.editText)) {
                regFullName.error = getString(R.string.validInput)
                regEmail.error = getString(R.string.errorEmail)
                regPhone.error = getString(R.string.validInput)
                regPassword.error = getString(R.string.errorPassword)
            } else if (validateAll(regFullName.editText, regEmail.editText, regPhone.editText, regPassword.editText)){
                val action = RegisterFragmentDirections.actionRegisterFragmentToLogInFragment(regEmail.editText?.text.toString(), regPassword.editText?.text.toString())
                findNavController().navigate(action, options)
            }
            else {
                if (regFullName.editText?.text.toString().isBlank()) {
                    regFullName.error = getString(R.string.validInput)
                }
                if (!isValidPhone(regPhone.editText?.text.toString())) {
                    regPhone.error = getString(R.string.validInput)
                }
                if (!isValidEmail(regEmail.editText?.text.toString())) {
                    regEmail.error = getString(R.string.errorEmail)
                }
                if (!isValidPassword(regPassword.editText?.text.toString())) {
                    regPassword.error = getString(R.string.errorPassword)
                }
            }
        }

        regFullName.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(regFullName.error?.isNotBlank() == true) regFullName.error = ""
            }
        })
        regEmail.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(regEmail.error?.isNotBlank() == true) regEmail.error = ""
            }
        })
        regPassword.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(regPassword.error?.isNotBlank() == true) regPassword.error = ""
            }
        })
        regPhone.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(regPhone.error?.isNotBlank() == true) regPhone.error = ""
            }
        })

        return view
    }

    //Devuelve verdadero si ningún campo es válido y falso si alguno lo es
    private fun validateAllFalse(username: EditText?, email: EditText?, phone: EditText?, pass: EditText?): Boolean {
        if(username?.text.toString().isBlank() && !isValidEmail(email?.text.toString()) && !isValidPhone(phone?.text.toString()) && !isValidPassword(pass?.text.toString())) {
            return true
        }
        return false
    }

    private fun validateAll(username: EditText?, email: EditText?, phone: EditText?, pass: EditText?): Boolean {
        if(username?.text.toString().isNotBlank() && isValidEmail(email?.text.toString()) && isValidPhone(phone?.text.toString()) && isValidPassword(pass?.text.toString())) {
            return true
        }
        return false
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return target.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches() && password.isNotBlank()
    }

    private fun isValidPhone(target: CharSequence): Boolean{
        val pattern: Pattern
        val PHONE_PATTERN = "^[+]?[0-9]{10,13}$"
        pattern = Pattern.compile(PHONE_PATTERN)
        val matcher: Matcher = pattern.matcher(target)
        return target.isNotBlank() && matcher.matches()
    }
}