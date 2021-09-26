package org.bedu.bedushop

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputLayout

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
            if(validateAll(regFullName.editText, regEmail.editText, regPhone.editText, regPassword.editText)) {
                regFullName.error = getString(R.string.validInput)
                regEmail.error = getString(R.string.errorEmail)
                regPhone.error = getString(R.string.validInput)
                regPassword.error = getString(R.string.errorPassword)
            } else if (regFullName.editText?.text.toString().isBlank()){
                regFullName.error = getString(R.string.validInput)
            } else if (regPhone.editText?.text.toString().isBlank()){
                regPhone.error = getString(R.string.validInput)
            } else if (regEmail.editText?.text.toString().isBlank()){
                regEmail.error = getString(R.string.errorEmail)
            } else if (regPassword.editText?.text.toString().isBlank()){
                regPassword.error = getString(R.string.errorPassword)
            } else {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLogInFragment(regEmail.editText?.text.toString())
                findNavController().navigate(action, options)
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

    private fun validateAll(username: EditText?, email: EditText?, phone: EditText?, pass: EditText?): Boolean {
        if(username?.text.toString().isBlank() && email?.text.toString().isBlank() && phone?.text.toString().isBlank() && pass?.text.toString().isBlank()) {
            return true
        }
        return false
    }
}