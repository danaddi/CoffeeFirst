package com.example.coffeefirst.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coffeefirst.R
import com.example.coffeefirst.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MapFragment.CoffeeShop>(
            "selected_coffee_shop"
        )?.observe(viewLifecycleOwner) { coffeeShop ->
            binding.tvAddress.text = coffeeShop.name
        }
        binding.addressContainer.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_map)
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_profile)
        }

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        generateQrCode(userId)

        val randomBonus = (0..100).random()
        binding.bonusTextView.text = "Бонусы: $randomBonus"

        binding.qrImageView.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val dialog = QrDialogFragment.newInstance(userId)
            dialog.show(parentFragmentManager, "QrCodeDialog")
        }

    }

    private fun generateQrCode(data: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 200, 200)
            binding.qrImageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}