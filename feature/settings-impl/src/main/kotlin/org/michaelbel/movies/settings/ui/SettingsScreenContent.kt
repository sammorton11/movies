package org.michaelbel.movies.settings.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.review.rememberReviewManager
import org.michaelbel.movies.common.review.rememberReviewTask
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.denied
import org.michaelbel.movies.settings_impl.BuildConfig
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.language.model.AppLanguage
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.version.AppVersionData
import org.michaelbel.movies.ui.R as UiR

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val currentLanguage: AppLanguage = AppLanguage.transform(stringResource(UiR.string.language_code))
    val currentTheme: AppTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val dynamicColors: Boolean by viewModel.dynamicColors.collectAsStateWithLifecycle()
    val layoutDirection: LayoutDirection by viewModel.layoutDirection.collectAsStateWithLifecycle()
    val isPlayServicesAvailable: Boolean by viewModel.isPlayServicesAvailable.collectAsStateWithLifecycle()
    val isAppFromGooglePlay: Boolean by viewModel.isAppFromGooglePlay.collectAsStateWithLifecycle()
    val areNotificationsEnabled: Boolean by viewModel.areNotificationsEnabled.collectAsStateWithLifecycle()
    val networkRequestDelay: Int by viewModel.networkRequestDelay.collectAsStateWithLifecycle()
    val appVersionData: AppVersionData by viewModel.appVersionData.collectAsStateWithLifecycle()

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    lifecycleOwner.lifecycle.addObserver(viewModel)

    SettingsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier,
        languages = viewModel.languages,
        currentLanguage = currentLanguage,
        onLanguageSelect = viewModel::selectLanguage,
        themes = viewModel.themes,
        currentTheme = currentTheme,
        onThemeSelect = viewModel::selectTheme,
        isDynamicColorsFeatureEnabled = viewModel.isDynamicColorsFeatureEnabled,
        dynamicColors = dynamicColors,
        onSetDynamicColors = viewModel::setDynamicColors,
        isRtlEnabled = layoutDirection == LayoutDirection.Rtl,
        onEnableRtlChanged = viewModel::setRtlEnabled,
        isPostNotificationsFeatureEnabled = viewModel.isPostNotificationsFeatureEnabled,
        areNotificationsEnabled = areNotificationsEnabled,
        onNotificationsStatusChanged = viewModel::checkNotificationsEnabled,
        isPlayServicesAvailable = isPlayServicesAvailable,
        isAppFromGooglePlay = isAppFromGooglePlay,
        networkRequestDelay = networkRequestDelay,
        onDelayChangeFinished = viewModel::setNetworkRequestDelay,
        appVersionData = appVersionData
    )
}

@Composable
internal fun SettingsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    languages: List<AppLanguage>,
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    themes: List<AppTheme>,
    currentTheme: AppTheme,
    onThemeSelect: (AppTheme) -> Unit,
    isDynamicColorsFeatureEnabled: Boolean,
    dynamicColors: Boolean,
    onSetDynamicColors: (Boolean) -> Unit,
    isRtlEnabled: Boolean,
    onEnableRtlChanged: (Boolean) -> Unit,
    isPostNotificationsFeatureEnabled: Boolean,
    areNotificationsEnabled: Boolean,
    onNotificationsStatusChanged: () -> Unit,
    isPlayServicesAvailable: Boolean,
    isAppFromGooglePlay: Boolean,
    networkRequestDelay: Int,
    onDelayChangeFinished: (Int) -> Unit,
    appVersionData: AppVersionData
) {
    val context: Context = LocalContext.current
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val reviewManager: ReviewManager = rememberReviewManager()
    val reviewInfo: ReviewInfo? = rememberReviewTask(reviewManager)

    var languageDialog: Boolean by remember { mutableStateOf(false) }
    var themeDialog: Boolean by remember { mutableStateOf(false) }

    if (languageDialog) {
        SettingLanguageDialog(
            languages = languages,
            currentLanguage = currentLanguage,
            onLanguageSelect = onLanguageSelect,
            onDismissRequest = {
                languageDialog = false
            }
        )
    }

    if (themeDialog) {
        SettingThemeDialog(
            themes = themes,
            currentTheme = currentTheme,
            onThemeSelect = onThemeSelect,
            onDismissRequest = {
                themeDialog = false
            }
        )
    }

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    val onStartAppSettingsIntent: () -> Unit = {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            "package:${context.packageName}".toUri()
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent: Intent ->
            resultContract.launch(intent)
        }
    }

    val onShowPermissionSnackbar: () -> Unit = {
        scope.launch {
            val result: SnackbarResult = snackbarHostState.showSnackbar(
                message = context.getString(R.string.settings_post_notifications_should_request),
                actionLabel = context.getString(R.string.settings_action_go),
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                onStartAppSettingsIntent()
            }
        }
    }

    val postNotificationsPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onNotificationsStatusChanged()
        } else {
            val shouldRequest: Boolean = (context as Activity).shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (!shouldRequest) {
                onShowPermissionSnackbar()
            }
        }
    }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    fun onLaunchReviewFlow() {
        when {
            !isPlayServicesAvailable -> {
                onShowSnackbar(context.getString(R.string.settings_error_play_services_not_available))
            }
            !isAppFromGooglePlay -> {
                onShowSnackbar(context.getString(R.string.settings_error_app_from_google_play))
            }
            else -> {
                reviewInfo?.let {
                    reviewManager.launchReviewFlow(context as Activity, reviewInfo)
                }
            }
        }
    }

    fun onLaunchPostNotificationsPermission() {
        if (Manifest.permission.POST_NOTIFICATIONS.denied(context)) {
            postNotificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                onNavigationIconClick = onBackClick
            )
        },
        bottomBar = {
            SettingsLanguageBox(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth(),
                appVersionData = appVersionData
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            SettingsLanguageBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        languageDialog = true
                    },
                currentLanguage = currentLanguage
            )

            SettingsThemeBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        themeDialog = true
                    },
                currentTheme = currentTheme
            )

            if (isDynamicColorsFeatureEnabled) {
                SettingsDynamicColorsBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            onSetDynamicColors(!dynamicColors)
                        },
                    isDynamicColorsEnabled = dynamicColors
                )
            }

            SettingsRtlBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onEnableRtlChanged(!isRtlEnabled)
                    },
                isRtlEnabled = isRtlEnabled
            )

            if (isPostNotificationsFeatureEnabled) {
                SettingsPostNotificationsBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            if (areNotificationsEnabled) {
                                onStartAppSettingsIntent()
                            } else {
                                onLaunchPostNotificationsPermission()
                            }
                        },
                    areNotificationsEnabled = areNotificationsEnabled
                )
            }

            SettingsReviewBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onLaunchReviewFlow()
                    }
            )

            if (BuildConfig.DEBUG) {
                SettingsNetworkRequestDelayBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    delay = networkRequestDelay,
                    onDelayChangeFinished = onDelayChangeFinished
                )
            }
        }
    }
}