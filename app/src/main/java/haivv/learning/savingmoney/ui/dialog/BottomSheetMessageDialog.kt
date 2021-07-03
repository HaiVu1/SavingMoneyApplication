package haivv.learning.savingmoney.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.databinding.TitleMessageDialogBinding

class BottomSheetMessageDialog : BottomSheetDialogFragment() {
    private lateinit var titleMessageDialogBinding: TitleMessageDialogBinding
    private var title: String = ""
    private var message: String = ""
    private lateinit var listener: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        titleMessageDialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.title_message_dialog, container, false)
        return titleMessageDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupAction()
    }

    private fun setupData() {
        titleMessageDialogBinding.title = title
        titleMessageDialogBinding.message = message
    }

    private fun setupAction() {
        titleMessageDialogBinding.btnOkDialog.setOnClickListener {
            dismissAllowingStateLoss()
            listener.invoke()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            tile: String,
            message: String,
            listener: () -> Unit
        ): BottomSheetMessageDialog {
            val dialog = BottomSheetMessageDialog()
            dialog.title = tile
            dialog.message = message
            dialog.listener = listener
            return dialog
        }
    }
}