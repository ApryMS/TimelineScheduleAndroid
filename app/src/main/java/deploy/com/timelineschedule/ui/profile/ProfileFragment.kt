package deploy.com.timelineschedule.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.FragmentProfileBinding
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.LoginActivity


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val pref = PrefManager(requireContext())
            pref.clearData()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }

}