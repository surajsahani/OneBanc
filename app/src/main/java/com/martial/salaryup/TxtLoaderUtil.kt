package com.martial.salaryup

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.os.Environment
import android.util.Log
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

object TxtLoaderUtil {
    private const val TAG = "TxtLoaderUtil"

    public val txtSavePath: String
        get() {
            return Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DCIM + "/" + System.currentTimeMillis() + ".png"
        }

    fun getBitmap(context: Context, resId: Int): Bitmap {
        // Decode a image
        val options = BitmapFactory.Options()
        options.inScaled = false
        return BitmapFactory.decodeResource(context.resources, resId, options)
    }

    fun getTxt(bitmap: Bitmap): Int {
        val txtNames = IntArray(2)
        GLES20.glGenTextures(1, txtNames, 0)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)

        // Bind to the texture in OpenGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, txtNames[0])

        // make alpha to transparent not to be a black color.
        // https://stackoverflow.com/questions/1003497/how-to-remove-black-background-from-textures-in-opengl
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        // Set filtering: a default must be set, or the texture will be
        // black.
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_CLAMP_TO_EDGE
        )

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        // Note: Following code may cause an error to be reported in the
        // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
        // Failed to generate texture mipmap levels (error=3)
        // No OpenGL error will be encountered (glGetError() will return
        // 0). If this happens, just squash the source image to be
        // square. It will look the same because of texture coordinates,
        // and mipmap generation will work.
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)

        Log.w(TAG, "text id : ${txtNames[0]}")
        if (txtNames[0] == 0) {
            Log.w(TAG, "Could not generate a new OpenGL texture object.")
            return 0
        }

        // Recycle the bitmap, since its data has been loaded into
        // OpenGL.
        bitmap.recycle()

        // Unbind from the texture.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        return txtNames[0]
    }

    @Throws(IOException::class)
    fun saveFrame(width: Int, height: Int) {
        // glReadPixels gives us a ByteBuffer filled with what is essentially big-endian RGBA
        // data (i.e. a byte of red, followed by a byte of green...).  To use the Bitmap
        // constructor that takes an int[] array with pixel data, we need an int[] filled
        // with little-endian ARGB data.
        //
        // If we implement this as a series of buf.get() calls, we can spend 2.5 seconds just
        // copying data around for a 720p frame.  It's better to do a bulk get() and then
        // rearrange the data in memory.  (For comparison, the PNG compress takes about 500ms
        // for a trivial frame.)
        //
        // So... we set the ByteBuffer to little-endian, which should turn the bulk IntBuffer
        // get() into a straight memcpy on most Android devices.  Our ints will hold ABGR data.
        // Swapping B and R gives us ARGB.  We need about 30ms for the bulk get(), and another
        // 270ms for the color swap.
        //
        // We can avoid the costly B/R swap here if we do it in the fragment shader (see
        // http://stackoverflow.com/questions/21634450/ ).
        //
        // Having said all that... it turns out that the Bitmap#copyPixelsFromBuffer()
        // method wants RGBA pixels, not ARGB, so if we create an empty bitmap and then
        // copy pixel data in we can avoid the swap issue entirely, and just copy straight
        // into the Bitmap from the ByteBuffer.
        //
        // Making this even more interesting is the upside-down nature of GL, which means
        // our output will look upside-down relative to what appears on screen if the
        // typical GL conventions are used.  (For ExtractMpegFrameTest, we avoid the issue
        // by inverting the frame when we render it.)
        //
        // Allocating large buffers is expensive, so we really want mPixelBuf to be
        // allocated ahead of time if possible.  We still get some allocations from the
        // Bitmap / PNG creation.

        val mPixelBuf = ByteBuffer.allocateDirect(width * height * 4)
            .order(ByteOrder.nativeOrder())

        GLES20.glReadPixels(
            0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
            mPixelBuf
        )

        Log.d(TAG, "size=" + mPixelBuf.position())

        var bos: BufferedOutputStream? = null
        val path = txtSavePath
        try {
            bos = BufferedOutputStream(FileOutputStream(path))
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mPixelBuf.rewind()
            bmp.copyPixelsFromBuffer(mPixelBuf)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, bos)
            bmp.recycle()
        } finally {
            bos?.close()
        }
        Log.d(TAG, "Saved " + width + "x" + height + " frame as '" + path + "'")
    }


    @Throws(IOException::class)
    fun saveFrame(textureId: Int, width: Int, height: Int) {
        // glReadPixels gives us a ByteBuffer filled with what is essentially big-endian RGBA
        // data (i.e. a byte of red, followed by a byte of green...).  To use the Bitmap
        // constructor that takes an int[] array with pixel data, we need an int[] filled
        // with little-endian ARGB data.
        //
        // If we implement this as a series of buf.get() calls, we can spend 2.5 seconds just
        // copying data around for a 720p frame.  It's better to do a bulk get() and then
        // rearrange the data in memory.  (For comparison, the PNG compress takes about 500ms
        // for a trivial frame.)
        //
        // So... we set the ByteBuffer to little-endian, which should turn the bulk IntBuffer
        // get() into a straight memcpy on most Android devices.  Our ints will hold ABGR data.
        // Swapping B and R gives us ARGB.  We need about 30ms for the bulk get(), and another
        // 270ms for the color swap.
        //
        // We can avoid the costly B/R swap here if we do it in the fragment shader (see
        // http://stackoverflow.com/questions/21634450/ ).
        //
        // Having said all that... it turns out that the Bitmap#copyPixelsFromBuffer()
        // method wants RGBA pixels, not ARGB, so if we create an empty bitmap and then
        // copy pixel data in we can avoid the swap issue entirely, and just copy straight
        // into the Bitmap from the ByteBuffer.
        //
        // Making this even more interesting is the upside-down nature of GL, which means
        // our output will look upside-down relative to what appears on screen if the
        // typical GL conventions are used.  (For ExtractMpegFrameTest, we avoid the issue
        // by inverting the frame when we render it.)
        //
        // Allocating large buffers is expensive, so we really want mPixelBuf to be
        // allocated ahead of time if possible.  We still get some allocations from the
        // Bitmap / PNG creation.

        val mPixelBuf = ByteBuffer.allocate(width * height * 4)

        val frame = IntArray(1)
        GLES20.glGenFramebuffers(1, frame, 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frame[0])

        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER,
            GLES20.GL_COLOR_ATTACHMENT0,
            GLES20.GL_TEXTURE_2D,
            textureId,
            0
        )

        GLES20.glReadPixels(
            0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
            mPixelBuf
        )

        Log.d(TAG, "Saved in $mPixelBuf")

        var bos: BufferedOutputStream? = null
        val path = txtSavePath
        try {
            bos = BufferedOutputStream(FileOutputStream(path))
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mPixelBuf.rewind()
            bmp.copyPixelsFromBuffer(mPixelBuf)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, bos)
            bmp.recycle()
        } finally {
            bos?.close()
        }

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        GLES20.glDeleteFramebuffers(1, frame, 0)
        Log.d(TAG, "Saved " + width + "x" + height + " frame as '" + path + "'")
    }
}
