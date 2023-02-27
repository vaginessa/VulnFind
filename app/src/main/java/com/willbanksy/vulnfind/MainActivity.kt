package com.willbanksy.vulnfind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.willbanksy.vulnfind.data.source.VulnRepository
import com.willbanksy.vulnfind.data.source.local.VulnDB
import com.willbanksy.vulnfind.data.source.local.VulnLocalDataSource
import com.willbanksy.vulnfind.data.source.remote.VulnRemoteDataSource
import com.willbanksy.vulnfind.model.VulnListModel
import com.willbanksy.vulnfind.ui.MainActivityView
import com.willbanksy.vulnfind.ui.theme.VulnFindTheme

class MainActivity : ComponentActivity() {
	// TODO: find some way to scope these to the activity or applicaiton or something so that they don't get recreated when the device is rotated etc
	private lateinit var repository: VulnRepository
	private lateinit var vulnDB: VulnDB
	private lateinit var model: VulnListModel
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			VulnFindTheme {
				vulnDB = Room.databaseBuilder(this, VulnDB::class.java, "VulnDB").build()
				repository = VulnRepository(VulnRemoteDataSource(), VulnLocalDataSource(vulnDB.dao()))
				model = VulnListModel(repository)
				model.refreshId("CVE-2019-1010218")
				MainActivityView(model)
			}
		}
	}
}
