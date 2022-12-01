package com.brokerageInsurance.admincrm.ui.addCustomer

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.databinding.FragmentAddCustomerBinding
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls.CallViewModel


class AddFragment : Fragment(R.layout.fragment_add_customer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddCustomerBinding.bind(view)

        binding.addBtn.setOnClickListener {
            addCustomer(binding)
        }
    }

    private fun addCustomer(binding: FragmentAddCustomerBinding) {
        binding.apply {
            if (name.text.toString().isEmpty()) {
                name.error = "Please enter customer name"
                name.isFocusable = true
            } else if (emailAddress.text.toString().isEmpty()) {
                emailAddress.error = "Please enter customer name"
                emailAddress.isFocusable = true
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.text.toString()).matches()) {
                emailAddress.error =
                    "Invalid Email address, email MUST be like this 'example@exp.com"
                emailAddress.isFocusable = true
            } else if (address.text.toString().isEmpty()) {
                address.error = "Please enter address"
                address.isFocusable = true
            } else if (phone.text.toString().isEmpty()) {
                phone.error = "Please enter phone"
                phone.isFocusable = true
            } else if (phone.text.toString()
                    .startsWith("01") && phone.text.toString().length != 11
            ) {
                phone.error = "phone number Must be start with '01' and it's length be 11 number"
                phone.isFocusable = true
            } else {
                val customer =
                    Customer(
                        name.text.toString(), address.text.toString(),
                        emailAddress.text.toString(), phone.text.toString(), true)

                val viewModel = ViewModelProvider(requireParentFragment())[AddViewModel::class.java]
                viewModel.intiViewModel(requireParentFragment())
                viewModel.insertCustomer(customer)

                createdMessage.isVisible = true
                binding.root.setOnClickListener {
                    createdMessage.isVisible = false
                }
            }
        }

    }
}