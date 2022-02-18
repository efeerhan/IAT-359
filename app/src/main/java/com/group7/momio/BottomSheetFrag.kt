package com.group7.momio

import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFrag : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun getTheme(): Int  = R.style.BottomSheetDialogCustom

}