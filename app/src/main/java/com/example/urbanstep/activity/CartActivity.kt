package com.example.urbanstep.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbanstep.Adapter.CartAdapter
import com.example.urbanstep.Helper.ChangeNumberItemsListener
import com.example.urbanstep.Helper.ManagmentCart
import com.example.urbanstep.R
import com.example.urbanstep.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagmentCart
    private var tax:Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        managementCart = ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter = CartAdapter(managementCart.getListCart(),this,object :ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }

        })

        with(binding){
            emptyText.visibility = if(managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility = if(managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart(){
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managementCart.getTotalFee()*percentTax)*100)/100.0
        val total  = Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100.0
        val itemTotal = Math.round(managementCart.getTotalFee()*100)/100.0

        with(binding){
            totalFeetxt.text = "₹$itemTotal"
            taxtxt.text = "₹$tax"
            deliverytxt.text = "₹$delivery"

            totaltxt.text = "₹$total"
        }
    }
    private fun setVariable() {
        binding.backbtn.setOnClickListener { finish() }

    }
}