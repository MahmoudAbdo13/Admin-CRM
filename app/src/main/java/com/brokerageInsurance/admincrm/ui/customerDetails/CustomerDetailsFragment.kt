package com.brokerageInsurance.admincrm.ui.customerDetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.database.CustomerDatabase
import com.brokerageInsurance.admincrm.data.Visit
import com.brokerageInsurance.admincrm.databinding.CallDialogBinding
import com.brokerageInsurance.admincrm.databinding.FragmentCustomerDetailsBinding
import com.brokerageInsurance.admincrm.ui.customer.CustomerViewModel
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls.CallViewModel
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits.VisitViewModel
import com.google.android.material.snackbar.Snackbar


class CustomerDetailsFragment : Fragment(R.layout.fragment_customer_details) {
    private val args: CustomerDetailsFragmentArgs by navArgs()
    private var permissionCall = false
    private var permissionStaueCall: Boolean = false
    private val REQUEST_CALL = 1000
    private val REQUEST_READ_PHONE_STATE = 2000
    private var callOrVisit: Boolean = true
    private var callPermission: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCustomerDetailsBinding.bind(view)

        binding.apply {
            name.text = args.customer.name
            phone.text = args.customer.phone
            emailAddress.text = args.customer.email
            address.text = args.customer.address
            timeAdded.text = "Customer added at: ${args.customer.createdDateFormatted}"
            callBtn.setOnClickListener {
                callOrVisit = true
                makePhoneCall(args.customer.phone)
                if (callPermission){
                    showAddCallDialog()
                }
            }
            visitBtn.setOnClickListener {
                callOrVisit = false
                showAddCallDialog()
            }
            moreDetailsBtn.setOnClickListener {
                val action =
                    CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToMoreDetailsFragment(
                        args.customer.id
                    )
                findNavController().navigate(action)
            }

            if(args.customer.followUp){
                followBtn.text = "Unfollow"
            }else{
                followBtn.text = "Follow"
            }
            followBtn.setOnClickListener {
                val customer = Customer(args.customer.name, args.customer.address, args.customer.email, args.customer.phone,
                !args.customer.followUp, args.customer.time, args.customer.id)
                val viewModel = ViewModelProvider(requireParentFragment())[CustomerViewModel::class.java]
                viewModel.intiViewModel(this@CustomerDetailsFragment)
                viewModel.updateCustomer(customer)
                findNavController().popBackStack()
            }
        }

    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_READ_PHONE_STATE) }
        } else {
            //callPermission = true
            val phoneListener = PhoneCallListener(phoneNumber)
            val telephonyManager =
                requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.listen(
                phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE
            )
            val dial = "tel:$phoneNumber"
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            showAddCallDialog()
        }
    }

    private class PhoneCallListener(var phoneNumber: String) : PhoneStateListener() {
        private var isPhoneCalling = false

        @Deprecated("Deprecated in Java")
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                isPhoneCalling = true
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                if (isPhoneCalling) {
                    //showAddCallDialog(phoneNumber)
                    isPhoneCalling = false
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showAddCallDialog() {
        val dialog = Dialog(requireContext())
        val initialize = InitializeDialog(requireContext(), dialog, R.layout.call_dialog)
        val mView = initialize.initializeDialog()
        val callBinding = CallDialogBinding.bind(mView)
        callBinding.apply {
            if (!callOrVisit) {
                title.text = "Add Visit Result"
                callLength.hint = "Enter visit duration"
                callResult.hint = "Enter visit result"
            }
            addCallBtn.setOnClickListener {
                if (callOrVisit) {
                    if (callLength.text.toString().isEmpty()) {
                        callLength.error = "Please enter call duration"
                        callLength.isFocusable = true
                    } else if (callResult.text.toString().isEmpty()) {
                        callResult.error = "Please enter call result"
                        callResult.isFocusable = true
                    } else {
                        val call =
                            Call(
                                callLength.text.toString(),
                                args.customer.id,
                                callResult.text.toString().trim()
                            )
                        val viewModel = ViewModelProvider(this@CustomerDetailsFragment)[CallViewModel::class.java]
                        viewModel.intiViewModel(this@CustomerDetailsFragment)
                        viewModel.insertCall(call)
                        dialog.dismiss()
                    }
                } else {
                    if (callLength.text.toString().isEmpty()) {
                        callLength.error = "Please enter visit duration"
                        callLength.isFocusable = true
                    } else if (callResult.text.toString().isEmpty()) {
                        callResult.error = "Please enter visit result"
                        callResult.isFocusable = true
                    } else {
                        val visit =
                            Visit(
                                callLength.text.toString(),
                                args.customer.phone,
                                callResult.text.toString(), args.customer.id
                            )
                        val viewModel = ViewModelProvider(this@CustomerDetailsFragment)[VisitViewModel::class.java]
                        viewModel.intiViewModel(this@CustomerDetailsFragment)
                        viewModel.insertVisit(visit)
                        dialog.dismiss()
                    }
                }
            }
            cancelBtn.setOnClickListener{
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CALL) {
            permissionCall = true
        }
        if (requestCode == REQUEST_READ_PHONE_STATE) {
            permissionStaueCall = true
        }
        if (permissionCall && permissionStaueCall) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(args.customer.phone)
            } else {
                Toast.makeText(requireContext(), "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

}