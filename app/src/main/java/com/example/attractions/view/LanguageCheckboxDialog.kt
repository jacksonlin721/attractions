package com.example.attractions.view

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class LanguageCheckboxDialog(context: Context?, langValue: ArrayList<String>, selectIndex: Int = 0) {
    private var mSelectedItem: Int = 0
    private var mItemList: Array<String>? = null
    private var mContext: Context? = context
    var valuesArray: Array<String>? = arrayOf()
    var mFragmentCallback: AttractionListFragment? = null

    interface OnItemSelectListener {
        fun selectLanguage(position: Int)
    }

    init {
        getItemListFromHashmap(langValue)
        this.mItemList = valuesArray
    }

    private fun getItemListFromHashmap(langValue: ArrayList<String>) {
        valuesArray = langValue.toArray(arrayOfNulls<String>(langValue.size))
    }

    fun setSelectedItem(selectedItem: Int) {
        mSelectedItem = selectedItem
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(mContext!!)
        builder.setSingleChoiceItems(mItemList, mSelectedItem,
            DialogInterface.OnClickListener { dialog, which -> mSelectedItem = which })
        builder.setPositiveButton(mContext!!.getString(android.R.string.ok)) { dialog, which ->
            if (mSelectedItem !== -1) {
                Toast.makeText(mContext, "已選擇：" + mItemList?.get(mSelectedItem), Toast.LENGTH_SHORT)
                    .show()
                mFragmentCallback?.selectLanguage(mSelectedItem)
            } else {
                Toast.makeText(mContext, "請選擇項目", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton(
            mContext!!.getString(android.R.string.cancel)
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    fun setLangCallback(fragmentCallback: AttractionListFragment) {
        mFragmentCallback = fragmentCallback
    }

}