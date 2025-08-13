package com.nedhuo.libimage

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.nedhuo.libutils.utilcode.util.ConvertUtils
import com.nedhuo.libutils.utilcode.util.Utils
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File

/**
 *
 *  图片加载工具类
 *
 * @name ImageUtils
 */
object ImageLoader {
    val ANIM: Int = -1

    fun ossResize(path: String?, imageView: ImageView): String? {
        if (TextUtils.isEmpty(path) || !path!!.contains("oss")) {
            return path
        }
        val layoutParams = imageView.getLayoutParams()
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT || layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return path
        }
        val measuredWidth = imageView.getMeasuredWidth()
        val measuredHeight = imageView.getMeasuredHeight()
        if (path.contains("webp") || path.contains("gif") || path.contains("x-oss-process") || measuredHeight < 1 || measuredWidth < 1) {
            return path
        }
        return String.format("%s?x-oss-process=image/resize,h_%s,w_%s", path, measuredWidth, measuredHeight)
    }

    /**
     * 默认加载
     */
    fun loadImageView(fragment: Fragment, path: String?, view: ImageView?) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(fragment).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    /**
     * 默认加载
     */
    fun loadImageView(path: String?, view: ImageView?) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(view).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    /**
     * 不压缩
     */
    fun loadOrigin(path: String?, view: ImageView?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    /**
     * 加载爵位动图
     */
    fun loadVipWebp(path: String?, view: ImageView?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        loadSample(path, view, ConvertUtils.dp2px(200f), ConvertUtils.dp2px(200f))
    }

    /**
     * 采样
     */
    fun loadSample(path: String?, view: ImageView?, width: Int, height: Int) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        val options = RequestOptions().override(width, height).diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(view).load(path).apply(options).into(view)
    }

    fun loadHeadCC(path: String?, view: ImageView?) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(view).load(path).error(R.mipmap.default_avatar).placeholder(R.mipmap.default_avatar).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadCompressImg(path: String?, view: ImageView?, width: Int, height: Int) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(view)
            .load(path)
            .centerCrop()
            .override(width, height).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    fun loadCenterCrop(path: String?, view: ImageView?) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(view).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadRes(path: Int, view: ImageView?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }


    fun loadFile(fileUrl: File?, view: ImageView?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(fileUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }


    /**
     * @param radius   1-25范围 随着数字越大,模糊度越高
     * @param sampling 模糊度越高
     */
    fun <T> loadImageBlurMeBg(url: T?, view: ImageView?, radius: Int, sampling: Int) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(url)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
            .into(view)
    }

    /**
     * 设置加载中以及加载失败图片
     */
    fun loadImageWithLoading(path: String?, view: ImageView?, lodingImage: Int, errorRes: Int) {
        var path = path
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        path = ossResize(path, view)
        Glide.with(view).load(path).placeholder(lodingImage).error(errorRes).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }


    /**
     * 加载为bitmap
     *
     * @param path     图片地址
     * @param listener 回调
     */
    fun loadBitmap(path: String?, listener: onLoadBitmap) {
        Glide.with(Utils.getApp()).asBitmap().load(path).into(object : SimpleTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                listener.onReady(resource)
            }
        })
    }

    fun loadGift(view: ImageView?, res: Int) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        if (res == ANIM) {
            try {
                val background = view.getBackground() as AnimationDrawable?
                if (background != null) {
                    background.start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Glide.with(view.getContext()).asGif().load(res).into(view)
        }
    }

    fun loadImageWithWidth(url: String?, view: ImageView?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).asDrawable().load(url).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable?>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                val oldParams = view.getLayoutParams()
                oldParams.height = (resource.getIntrinsicHeight() * 1f / resource.getIntrinsicWidth() * view.getMeasuredWidth()).toInt()
                view.setLayoutParams(oldParams)
                return false
            }
        }).into(view)
    }

    fun loadImageWithWidth2(url: String?, view: ImageView?, cornerRadius: Int) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).asDrawable().load(url).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable?>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                val oldParams = view.getLayoutParams()
                oldParams.height = (resource.getIntrinsicHeight() * 1f / resource.getIntrinsicWidth() * view.getMeasuredWidth()).toInt()
                view.setLayoutParams(oldParams)
                return false
            }
        }).apply(RequestOptions.bitmapTransform(MultiTransformation(RoundedCorners(ConvertUtils.dp2px(cornerRadius.toFloat())))))
            .into(view)
    }

    /**
     * 加载圆角
     *
     * @param view
     * @param imageUrl
     */
    fun loadCornerRadius(view: ImageView?, imageUrl: String?, cornerRadius: Int) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(imageUrl)
            .apply(RequestOptions.bitmapTransform(MultiTransformation<Bitmap?>(CenterCrop(), RoundedCorners(ConvertUtils.dp2px(cornerRadius.toFloat()))))).into(view)
    }

    /**
     * 加载圆角
     */
    fun loadCornerRadius(view: ImageView?, drawable: Drawable?, cornerRadius: Int) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(drawable)
            .apply(RequestOptions.bitmapTransform(MultiTransformation<Bitmap?>(CenterCrop(), RoundedCorners(ConvertUtils.dp2px(cornerRadius.toFloat()))))).into(view)
    }

    /**
     * 加载圆型
     *
     * @param view
     * @param imageUrl
     */
    fun loadCircleCropRadius(view: ImageView?, imageUrl: String?) {
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        Glide.with(view).load(imageUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(view)
    }

    fun loadHead(context: Context, view: ImageView?, url: String?) {
        var url = url
        if (view == null) return
        if (context is Activity) {
            if (context.isDestroyed() || context.isFinishing()) {
                return
            }
        }
        url = ossResize(url, view)
        val options = RequestOptions.circleCropTransform()
        Glide.with(context).load(url).apply(options).error(R.mipmap.default_avatar).placeholder(R.mipmap.default_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadHead(view: ImageView?, url: String?) {
        var url = url
        if (view == null) return
        if (view.getContext() is Activity) {
            if ((view.getContext() as Activity).isFinishing() || (view.getContext() as Activity).isDestroyed()) {
                return
            }
        }
        url = ossResize(url, view)
        val options = RequestOptions.circleCropTransform()
        Glide.with(view.getContext()).load(url).apply(options).error(R.mipmap.default_avatar).placeholder(R.mipmap.default_avatar).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    fun loadImage(context: Context, view: ImageView?, url: String?) {
        var url = url
        if (view == null) return
        if (context is Activity) {
            if (context.isDestroyed() || context.isFinishing()) {
                return
            }
        }
        url = ossResize(url, view)
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImage(context: Context, imageView: ImageView?, resId: Int) {
        if (imageView == null) return
        if (context is Activity) {
            if (context.isDestroyed() || context.isFinishing()) {
                return
            }
        }
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
    }

    fun loadImageWithWrap(context: Context, imageView: ImageView?, url: String?) {
        if (imageView == null) return
        if (context is Activity) {
            if (context.isDestroyed() || context.isFinishing()) {
                return
            }
        }
        Glide.with(context).asBitmap().load(url).into(object : SimpleTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                val oldParams = imageView.getLayoutParams()
                oldParams.width = oldParams.height * 3
                imageView.setLayoutParams(oldParams)
                Glide.with(context).load(resource).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
            }
        })
    }

    fun loadIcon(context: Context, view: ImageView?, url: String?) {
        var url = url
        if (view == null) return
        if (context is Activity) {
            if (context.isDestroyed() || context.isFinishing()) {
                return
            }
        }
        url = ossResize(url, view)
        if (TextUtils.isEmpty(url)) {
            view.setVisibility(View.GONE)
        } else {
            view.setVisibility(View.VISIBLE)
        }
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    /**
     * 加载bitmap回调
     */
    interface onLoadBitmap {
        fun onReady(resource: Bitmap?)
    }
}