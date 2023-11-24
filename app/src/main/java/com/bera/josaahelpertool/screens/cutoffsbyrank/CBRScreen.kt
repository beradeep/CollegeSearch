package com.bera.josaahelpertool.screens.cutoffsbyrank

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bera.josaahelpertool.components.cbr.CBRCutoffs
import com.bera.josaahelpertool.components.cbr.CBRFilters
import com.bera.josaahelpertool.openAppSettings
import com.bera.josaahelpertool.utils.PermissionDialog
import com.bera.josaahelpertool.utils.StoragePermissionTextProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CBRScreen(viewModel: CBRViewModel) {

    val state = viewModel.state
    val activity = LocalContext.current as Activity
    val permissionState = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (!state.loading && state.exam == "Main" && state.cutoffs.isNotEmpty()) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            viewModel.onAction(CBRAction.GeneratePdf)
                        }
                        else if(permissionState.hasPermission) {
                            viewModel.onAction(CBRAction.GeneratePdf)
                        }
                        else {
                            permissionState.launchPermissionRequest()
                        }
                    }) {
                    Icon(imageVector = Icons.Rounded.Download, contentDescription = "Download")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 2.dp, start = 4.dp, end = 4.dp)
        ) {
            CBRFilters(
                state = state,
                onAction = viewModel::onAction
            )
            Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp))
            CBRCutoffs(cutoffs = state.cutoffs, isLoading = state.loading)
        }
        if (permissionState.permissionRequested && !permissionState.hasPermission) {
            PermissionComposable(permissionState = permissionState, activity = activity)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionComposable(
    permissionState: PermissionState,
    activity: Activity
) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                PermissionDialog(
                    permissionTextProvider = StoragePermissionTextProvider(),
                    isPermanentlyDeclined = false,
                    onDismiss = permissionState::launchPermissionRequest,
                    onOkClick = permissionState::launchPermissionRequest,
                    onGoToAppSettingsClick = activity::openAppSettings
                )
            },
            permissionNotAvailableContent = {
                PermissionDialog(
                    permissionTextProvider = StoragePermissionTextProvider(),
                    isPermanentlyDeclined = true,
                    onDismiss = { showDialog = false },
                    onOkClick = {},
                    onGoToAppSettingsClick = activity::openAppSettings
                )
            }
        ) { }
    }
}