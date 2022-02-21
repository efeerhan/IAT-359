package com.group7.momio

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragmentActivity : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun getTheme(): Int  = R.style.CustomBottomSheetDialog
}