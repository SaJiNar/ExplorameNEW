package com.example.explora_me.View

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.explora_me.MainActivity
import com.example.explora_me.R
import com.example.explora_me.data.SitioEntity
import com.example.explora_me.databinding.FragmentEditSitioBinding
import com.example.explora_me.model.SitioApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue


class EditSitioFragment : Fragment() {

    private lateinit var mBinding: FragmentEditSitioBinding
    private var mActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEditSitioBinding
            .inflate(
                inflater,
                container,
                false
            )
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_sitio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edit_sitio_title_add)

        setHasOptionsMenu(true)

        mBinding.etPhotoUrl.addTextChangedListener{
            Glide.with(this)
                .load(mBinding.etPhotoUrl.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imageFoto)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                hideKeyborad()
               // mActivity?.onBackPressed()
                mActivity?.onBackPressedDispatcher?.onBackPressed()
                true
            }

            R.id.action_save -> {
                var sitio = SitioEntity(
                    name = mBinding.etName.text.toString().trim(),
                    phone = mBinding.etPhone.text.toString().trim(),
                    website = mBinding.etWebsite.text.toString().trim(),
                    photoUrl = mBinding.etPhotoUrl.text.toString().trim()


                )


                // el video de ejemplo lo tiene muy diferente !!!
                val queue = LinkedBlockingQueue<Long?>()
                Thread{
                 val id = SitioApplication.database.sitioDao().addSitio(sitio)
                    queue.add(id)


                   // mActivity?.addSitio(sitio)
                }.start()
                mActivity?.addSitio(sitio)
                hideKeyborad()
                queue.take()?.let{
                    // agregar una funcion print que funcione
                    Toast.makeText(mActivity, R.string.edit_sitio_message_save_succes, Toast.LENGTH_SHORT).show()
                    mActivity?.onBackPressedDispatcher?.onBackPressed()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
        // return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView() {
        hideKeyborad()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hidePrincipalFab(true)

        setHasOptionsMenu(false)
        super.onDestroy()
    }

    private fun hideKeyborad(){
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}