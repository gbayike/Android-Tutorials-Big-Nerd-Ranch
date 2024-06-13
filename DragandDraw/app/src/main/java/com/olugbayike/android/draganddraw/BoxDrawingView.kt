package com.olugbayike.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment.SavedState
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.ArrayList


private const val TAG = "BoxDrawingView"
class BoxDrawingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    companion object{
        private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        private const val SUPER_ARRAY_KEY = "SUPER_ARRAY_KEY"
    }


    var loading: Boolean = false
    private var currentBox: Box? = null
    private var boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }

    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val current = event?.let { PointF(it.x, it.y) }
        var action = ""
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                currentBox = current?.let {
                    Box(it).also {
                        boxes.add(it)
                    }
                }
                Log.i(TAG, "Boxes in onTouch: $boxes")
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }

        Log.i(TAG, "$action at x=${current?.x}, y=${current?.y}")

        return true
    }
    private fun updateCurrentBox(current: PointF?){
        currentBox?.let {
            if (current != null) {
                it.end = current
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        // Fill the background
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box: Box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

//    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
//        dispatchFreezeSelfOnly(container)
//    }
//
//    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
//        dispatchThawSelfOnly(container)
//    }
    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelableArrayList("SPARSE_STATE_KEY", ArrayList<Parcelable>(boxes) )
        bundle.putParcelable("SUPER_STATE_KEY" ,super.onSaveInstanceState())
        return bundle
    }

//    private fun saveChildViewStates(): SparseArray<Parcelable> {
//        val childViewStates = SparseArray<Parcelable>()
//    }
//}

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewState = state
        if (viewState is Bundle){
//            boxes.addAll((viewState.getParcelableArrayList("SPARSE_STATE_KEY")).toList())
            var parcelableArray = viewState.getParcelableArrayList<Box>("SPARSE_STATE_KEY")?.toList()
            var parcelableMutableList = parcelableArray as MutableList<Box>
            boxes.addAll(parcelableMutableList)
//            Log.i(TAG, "$parcelableArray")
            Log.i(TAG, "$parcelableMutableList")
            viewState = viewState.getParcelable("SUPER_STATE_KEY")
        }

        super.onRestoreInstanceState(viewState)


    }
}

