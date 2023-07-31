package com.ram.elctricbillscalculator.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ram.elctricbillscalculator.R
import com.ram.elctricbillscalculator.databinding.ActivityAddSlabBinding
import com.ram.elctricbillscalculator.viewModel.SlabViewModel
import com.ram.electricbillcalculator.adapter.SlabAdapter
import com.ram.electricbillcalculator.model.SlabModel

class AddSlabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSlabBinding
    private lateinit var slabAdapter: SlabAdapter
    private lateinit var slabViewModel: SlabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSlabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Initializing the view model */
        slabViewModel = ViewModelProvider(this)[SlabViewModel::class.java]

        initView()
        initListener()
        initRecyclerView()
        setUpObserver()

    }

    /*initializing activity view*/
    private fun initView() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        binding.toolbarLayout.toolbar.title = " "
        binding.toolbarLayout.toolbarTitle.text = getString(R.string.consumption_slab)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    /*add click listener on button and logic and validation on click */
    private fun initListener() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        /*Adding  the new slab and rate in database*/
        binding.btnAdd.setOnClickListener {
            if (binding.etStartUnit.text.toString()
                    .isEmpty() || binding.etEndUnit.text.toString()
                    .isEmpty() || binding.etRateOfUnit.text.toString()
                    .isEmpty()
            ) {
                showToast(getString(R.string.all_details))
                return@setOnClickListener
            }
            if (binding.etStartUnit.text.toString().toInt() > binding.etEndUnit.text.toString()
                    .toInt()
            ) {
                showToast(getString(R.string.unit_error))
                return@setOnClickListener
            }


            slabViewModel.addSlabDetails(
                SlabModel(
                    binding.etStartUnit.text.toString().toInt(),
                    binding.etEndUnit.text.toString().toInt(),
                    binding.etRateOfUnit.text.toString().toInt()
                )
            )
            clearEditText()

        }
    }

    /*initializing recyclerview*/
    private fun initRecyclerView() {
        binding.consumptionlayout.rvSlab.apply {
            layoutManager =
                LinearLayoutManager(this@AddSlabActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            slabAdapter = SlabAdapter()
            adapter = slabAdapter
        }
    }

    /*observing value from the observer and showing in the recycler view by adapter*/
    private fun setUpObserver() {
        slabViewModel.getSlabList.observe(this) {
            if (it.isNotEmpty()) {
                slabAdapter.submitList(it)
                binding.consumptionlayout.rvSlab.visibility = View.VISIBLE
                binding.consumptionlayout.tlSlapTitle.visibility = View.VISIBLE

            } else {
                binding.consumptionlayout.rvSlab.visibility = View.GONE
                binding.consumptionlayout.tlSlapTitle.visibility = View.GONE
            }
        }
    }

    /*Clearing the edittext view after adding the value in database*/
    private fun clearEditText() {
        binding.etStartUnit.text.clear()
        binding.etEndUnit.text.clear()
        binding.etRateOfUnit.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

}