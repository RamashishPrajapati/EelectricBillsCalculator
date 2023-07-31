package com.ram.elctricbillscalculator.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ram.elctricbillscalculator.R
import com.ram.elctricbillscalculator.adapter.ConsumptionAdapter
import com.ram.elctricbillscalculator.databinding.ActivityCalculateElectricBillBinding
import com.ram.elctricbillscalculator.viewModel.SlabViewModel
import com.ram.electricbillcalculator.model.ConsumptionModel
import com.ram.electricbillcalculator.viewModel.ConsumptionViewModel

class CalculateElectricBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateElectricBillBinding
    private lateinit var consumptionAdapter: ConsumptionAdapter
    private lateinit var consumptionViewModel: ConsumptionViewModel
    private lateinit var slabViewModel: SlabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_electric_bill)
        binding = ActivityCalculateElectricBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Initializing the view model in activity*/
        consumptionViewModel = ViewModelProvider(this)[ConsumptionViewModel::class.java]
        slabViewModel = ViewModelProvider(this)[SlabViewModel::class.java]

        initView()
        hideView()
        initListener()
        initRecyclerView()
        setUpObserver()

    }

    /*initializing activity view*/
    private fun initView() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        binding.toolbarLayout.toolbar.title = ""
        binding.toolbarLayout.toolbarTitle.text = getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    /*add click listener on button and logic and validation on click */
    private fun initListener() {

        binding.btnSubmit.setOnClickListener {
            if (!isValidateServiceNumber(binding.etServiceNumber.text.toString())) {
                showToast(getString(R.string.valid_service_no_message))
                return@setOnClickListener
            }

            val inputText = binding.etMeterRedaing.text.toString()
            if (inputText.toIntOrNull() == null) {
                showToast(getString(R.string.positive_reading_message))
                return@setOnClickListener
            }

            slabViewModel.currentConsumptionRate = 0;
            slabViewModel.currentUnit = inputText.toInt()
            consumptionViewModel.getCustomerConsumptionDetails(binding.etServiceNumber.text.toString())
        }

        binding.btnSave.setOnClickListener {
            try {
                val inputText = binding.etMeterRedaing.text.toString()
                if (inputText.toIntOrNull() == null) {
                    return@setOnClickListener
                }

                if (slabViewModel.currentConsumptionRate > 0) {
                    consumptionViewModel.addSlabDetails(
                        ConsumptionModel(
                            binding.etServiceNumber.text.toString(),
                            inputText.toInt(),
                            slabViewModel.currentConsumptionRate
                        )
                    )
                    showToast(getString(R.string.save_message))
                    refreshActivity()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*initializing recyclerview*/
    private fun initRecyclerView() {
        binding.consumptionlayout.rvSlab.apply {
            layoutManager = LinearLayoutManager(
                this@CalculateElectricBillActivity, LinearLayoutManager.VERTICAL, false
            )
            setHasFixedSize(true)
            consumptionAdapter = ConsumptionAdapter()
            adapter = consumptionAdapter
        }
    }

    /*observing value from the observer*/
    private fun setUpObserver() {
        consumptionViewModel.showCustomerConsumptionDetails().observe(this) {
            if (it.isNotEmpty()) {
                /*Checking the current entered unit should be grater the last unit */
                if (binding.etMeterRedaing.text.toString().toInt() > it.first().consumptionUnit) {

                    slabViewModel.currentUnit =
                        binding.etMeterRedaing.text.toString().toInt() - it.first().consumptionUnit

                    slabViewModel.getSlabDetailsList()
                } else {
                    showToast(getString(R.string.meter_reading_error))
                }

                /*Adding value to adapter to show in recycler view*/
                consumptionAdapter.submitList(it)
                /*Showing hided view as per*/
                binding.consumptionlayout.tlSlapTitle.visibility = View.VISIBLE
                binding.consumptionlayout.rvSlab.visibility = View.VISIBLE
                binding.tvHistoricalTitle.visibility = View.VISIBLE
            } else {
                slabViewModel.getSlabDetailsList()
            }
        }

        slabViewModel.showSlabDetailsList().observe(this) {
            if (it.isEmpty()) {
                showToast(getString(R.string.add_consumption_slab))
                return@observe
            }
            slabViewModel.updateCurrentConsumptionRate(it)
            binding.cvConsumptionRate.visibility = View.VISIBLE
            binding.tvConsumptionRate.text =
                getString(R.string.consumption_cost) + "${slabViewModel.currentConsumptionRate}"
        }
    }

    /*Recreating activity again*/
    private fun refreshActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    /*Checking string is alpha numeric  and length = 10 or not*/
    private fun isValidateServiceNumber(chars: String): Boolean {
        return if (chars.isEmpty())
            false
        else
            chars.matches("^[a-zA-Z0-9]*$".toRegex()) && chars.length == 10
    }

    /*Hiding the view as per the UI logic,
     get visible after submit button condition is true*/
    private fun hideView() {
        binding.cvConsumptionRate.visibility = View.GONE
        binding.consumptionlayout.tlSlapTitle.visibility = View.GONE
        binding.consumptionlayout.rvSlab.visibility = View.GONE
        binding.tvHistoricalTitle.visibility = View.GONE
    }

    /*Common toast function*/
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.slab -> {
                startActivity(
                    Intent(
                        this@CalculateElectricBillActivity, AddSlabActivity::class.java
                    )
                )
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}