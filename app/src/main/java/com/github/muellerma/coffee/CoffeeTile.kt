package com.github.muellerma.coffee

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.service.quicksettings.Tile
import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import android.util.Log

class CoffeeTile : TileService() {
    override fun onClick() {
        Log.d(TAG, "onClick()")
        ForegroundService.startOrStop(this, !ForegroundService.isRunning(this))
    }

    override fun onStartListening() {
        Log.d(TAG, "onStartListening()")
        super.onStartListening()
        setTileState(qsTile, ForegroundService.isRunning(this))
    }

    override fun onStopListening() {
        Log.d(TAG, "onStopListening()")
        super.onStopListening()
    }

    companion object {
        private val TAG = CoffeeTile::class.java.simpleName

        fun requestTileStateUpdate(context: Context) {
            Log.d(TAG, "requestTileStateUpdate()")
            requestListeningState(context, ComponentName(context, CoffeeTile::class.java))
        }

        private fun setTileState(tile: Tile?, running: Boolean) {
            Log.d(TAG, "setTileState: running = $running")
            tile ?: return
            tile.apply {
                state = if (running) STATE_ACTIVE else STATE_INACTIVE
                updateTile()
            }
        }
    }
}