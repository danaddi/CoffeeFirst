package com.example.coffeefirst.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeefirst.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val total = args.totalPrice
        val bonus = args.bonus

        binding.paymentText.text = "Сумма: $total ₽\nБонусы: +$bonus"

        binding.buttonPay.setOnClickListener {
            val success = (1..10).random() <= 8

            if (success) {
                saveBonus(bonus)
                Toast.makeText(requireContext(), "Оплата прошла успешно!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Оплата не прошла. Попробуйте снова.", Toast.LENGTH_SHORT).show()
            }

            findNavController().popBackStack()
        }
    }

    private fun saveBonus(bonus: Int) {
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val current = prefs.getInt("bonus", 0)
        prefs.edit().putInt("bonus", current + bonus).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

