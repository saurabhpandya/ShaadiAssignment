package com.fidato.shaadiassignment.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fidato.shaadiassignment.R
import com.fidato.shaadiassignment.base.BaseFragment
import com.fidato.shaadiassignment.databinding.FragmentSetupBinding
import com.fidato.shaadiassignment.main.viewmodel.MainViewModel
import com.fidato.shaadiassignment.utility.AcceptanceGroup
import com.fidato.shaadiassignment.utility.AgeGroup
import com.fidato.shaadiassignment.utility.Constants.Companion.ACCEPTANCE_GROUP
import com.fidato.shaadiassignment.utility.Constants.Companion.GENDER_GROUP
import com.fidato.shaadiassignment.utility.GenderGroup

class SetupFragment : BaseFragment(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private val TAG = SetupFragment::class.java.canonicalName

    private lateinit var binding: FragmentSetupBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var selectedGenderGroup: GenderGroup
    private lateinit var selectedAgeGroup: AgeGroup
    private lateinit var selectedAcceptanceGroup: AcceptanceGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = setupViewModel(this.activity!!, MainViewModel::class.java) as MainViewModel
        binding.vm = viewModel
        initListner()
    }

    private fun initListner() {
        binding.rdoGrpGroupByGender.setOnCheckedChangeListener(this)
        binding.rdoGrpGroupByAcceptance.setOnCheckedChangeListener(this)
        binding.rdoGrpGroupByAge.setOnCheckedChangeListener(this)
        binding.btnProceed.setOnClickListener(this)
        binding.rdobtnAcptncNew.isChecked = true
        binding.rdobtnAll.isChecked = true
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            binding.rdobtnMale.id -> selectedGenderGroup = GenderGroup.MALE
            binding.rdobtnFemale.id -> selectedGenderGroup = GenderGroup.FEMALE
            binding.rdobtnAll.id -> selectedGenderGroup = GenderGroup.ALL
            binding.rdobtnAgeGroup1.id -> {
                AgeGroup.GROUP1
            }
            binding.rdobtnAgeGroup2.id -> {
                AgeGroup.GROUP2
            }
            binding.rdobtnAgeGroup3.id -> {
                AgeGroup.GROUP3
            }
            binding.rdobtnAgeGroup4.id -> {
                AgeGroup.GROUP4
            }
            binding.rdobtnAgeGroup5.id -> {
                AgeGroup.GROUP5
            }
            binding.rdobtnAgeGroup6.id -> {
                AgeGroup.GROUP6
            }
            binding.rdobtnAgeGroup7.id -> {
                AgeGroup.GROUP7
            }
            binding.rdobtnAgeGroup8.id -> {
                AgeGroup.GROUP8
            }
            binding.rdobtnAgeGroup9.id -> {
                AgeGroup.GROUP9
            }
            binding.rdobtnAcptncAccepted.id -> selectedAcceptanceGroup = AcceptanceGroup.ACCEPTED
            binding.rdobtnAcptncDeclined.id -> selectedAcceptanceGroup = AcceptanceGroup.DECLINED
            binding.rdobtnAcptncNew.id -> selectedAcceptanceGroup = AcceptanceGroup.NEW
            binding.rdobtnAcptncAll.id -> selectedAcceptanceGroup = AcceptanceGroup.ALL
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnProceed.id -> navigateToShow()
        }
    }

    private fun navigateToShow() {
        val bundle = bundleOf(
            ACCEPTANCE_GROUP to selectedAcceptanceGroup,
            GENDER_GROUP to selectedGenderGroup
        )
        findNavController().navigate(R.id.action_setupFragment_to_mainFragment, bundle)
    }

}