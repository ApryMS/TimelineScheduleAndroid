package deploy.com.timelineschedule.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.FragmentProfileBinding
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.LoginActivity
import deploy.com.timelineschedule.ui.login.User


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
        init()
    }

    private fun init() {
        with(binding) {
            val pref = PrefManager(requireContext())
            val json = pref.getString("user_login")
            val user = Gson().fromJson(json, User::class.java)
            tvName.text = user.name
            tvEmail.text = user.email
            tvPosition.text = user.position
            tvStatus.text = user.status
            btnLogout.setOnClickListener {

                pref.clearData()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}