package com.example.coffeefirst.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.example.coffeefirst.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.lifecycleScope
import com.example.coffeefirst.R
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
        }

        binding.registerText.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collectLatest { state ->
                when (state) {
                    is AuthState.Loading -> showProgressBar()
                    is AuthState.Success -> navigateToHome()
                    is AuthState.Error -> showError(state.message)
                    else -> hideProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        // Если ProgressBar есть в layout, например с id progressBar:
        // binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        // binding.progressBar.visibility = View.GONE
    }

    private fun showError(message: String) {
        // Например, Toast:
        // Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        // TODO: переход после успешного входа
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
