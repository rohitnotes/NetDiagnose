package com.safframework.netdiagnose.log

import android.os.Environment
import com.safframework.log.configL
import com.safframework.log.converter.gson.GsonConverter
import com.safframework.log.printer.FilePrinter
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.printer.file.clean.FileLastModifiedCleanStrategy

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.log.LogManager
 * @author: Tony Shen
 * @date: 2020-01-18 21:19
 * @version: V1.0 <描述当前版本功能>
 */
object LogManager {

    const val PREFIX_APP = "recycle_machine-app-"
    const val PREFIX_AIYANJI = "recycle_machine-aiyanji-"
    const val PREFIX_AP = "recycle_machine-ap-"
    const val PREFIX_REMOTE = "recycle_machine-remote-"
    const val PREFIX_HARDWARE = "recycle_machine-hardware-"

    const val SEVEN_DAYS:Long = 24*3600*1000*7

    private val cleanStrategy = FileLastModifiedCleanStrategy(SEVEN_DAYS)

    val filePrinter: FilePrinter by lazy {

        var sdcardPath = "/sdcard/netdiagnose"
        val f = Environment.getExternalStorageDirectory()
        if (f != null) {
            sdcardPath = f.absolutePath + "/netdiagnose/app"
        }

        FileBuilder().folderPath(sdcardPath).fileNameGenerator(NetdiagnoseFileNameGenerator(
            PREFIX_APP
        )).cleanStrategy(cleanStrategy).build()
    }

    @JvmStatic
    fun initLog() {

        configL {
            converter = GsonConverter()
        }.apply {
            addPrinter(filePrinter)
        }
    }
}