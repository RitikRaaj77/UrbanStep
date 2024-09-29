package com.example.urbanstep.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbanstep.Adapter.CartAdapter
import com.example.urbanstep.Helper.ChangeNumberItemsListener
import com.example.urbanstep.Helper.ManagmentCart
import com.example.urbanstep.R
import com.example.urbanstep.databinding.ActivityCartBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class CartActivity : BaseActivity(), PaymentResultWithDataListener {

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

        //razorPay
        Checkout.preload(applicationContext)
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_Nqy8gmPWtyPySL")

//        binding.button2.setOnClickListener{
//            intiPayment()
//        }

//        binding.button2.setOnClickListener {
//            val uri = Uri.parse("upi://pay?pa=YOUR_UPI_ID@bank&pn=YOUR_NAME&tn=TransactionNote&am=1&cu=INR")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.setPackage("com.google.android.apps.nbu.paisa.user") // You can change this to any UPI app's package name
//            val chooser = Intent.createChooser(intent, "Pay with")
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(chooser)
//            } else {
//                Toast.makeText(this, "No UPI service available",Toast.LENGTH_SHORT).show()
//            }
//        }

    }

//    private fun intiPayment() {
//
//        val activity: Activity = this
//        val co = Checkout()
//
//        try {
//            val options = JSONObject()
//            options.put("name","Razorpay Corp")
//            options.put("description","Demoing Charges")
//            //You can omit the image option to fetch the image from the dashboard
//            options.put("image","http://example.com/image/rzp.jpg")
//            options.put("theme.color", "#3399cc");
//            options.put("currency","INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
//            options.put("amount","50000")//pass amount in currency subunits
//
//            val retryObj = JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);
//
//            val prefill = JSONObject()
//            prefill.put("email","ritik.raj@example.com")
//            prefill.put("contact","212173314")
//
//            options.put("prefill",prefill)
//            co.open(activity,options)
//        }catch (e: Exception){
//            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
//            e.printStackTrace()
//        }
//
//    }

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

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "error $p1", Toast.LENGTH_SHORT).show()
    }
}
