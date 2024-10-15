package dev.zwander.installwithoptions.data

import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.zwander.installwithoptions.R

@Composable
fun rememberInstallOptions(): List<InstallOption> {
    val context = LocalContext.current

    return remember {
        getInstallOptions().sortedBy { opt ->
            context.resources.getString(opt.labelResource)
        }
    }
}

fun getInstallOptions() = InstallOption::class.sealedSubclasses
    .mapNotNull { it.objectInstance }
    .filter { Build.VERSION.SDK_INT >= it.minSdk && Build.VERSION.SDK_INT <= it.maxSdk }

@Suppress("unused")
sealed class InstallOption(
    override val minSdk: Int = Build.VERSION_CODES.BASE,
    override val maxSdk: Int = Int.MAX_VALUE,
    override val value: Int,
    @StringRes override val labelResource: Int,
    @StringRes override val descResource: Int,
) : BaseOption<Int>() {
    @Keep
    data object ReplaceExisting : InstallOption(
        value = 0x00000002,
        labelResource = R.string.replace_existing,
        descResource = R.string.replace_existing_desc,
    )

    @Keep
    data object AllowTest : InstallOption(
        value = 0x00000004,
        labelResource = R.string.allow_test,
        descResource = R.string.allow_test_desc,
    )

    @Keep
    data object Internal : InstallOption(
        value = 0x00000010,
        labelResource = R.string.internal,
        descResource = R.string.internal_desc,
    )

    @Keep
    data object External : InstallOption(
        value = 0x00000008,
        maxSdk = Build.VERSION_CODES.P,
        labelResource = R.string.external,
        descResource = R.string.external_desc,
    )

    @Keep
    data object FromAdb : InstallOption(
        value = 0x00000020,
        labelResource = R.string.from_adb,
        descResource = R.string.from_adb_desc,
    )

    @Keep
    data object ALlUsers : InstallOption(
        value = 0x00000040,
        labelResource = R.string.all_users,
        descResource = R.string.all_users_desc,
    )

    @Keep
    data object AllowDowngrade : InstallOption(
        value = 0x00000080 or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            0x00100000
        } else {
            0
        },
        labelResource = R.string.allow_downgrade,
        descResource = R.string.allow_downgrade_desc,
    )

    @Keep
    data object GrantAllRequestedPermissions : InstallOption(
        value = 0x00000100,
        minSdk = Build.VERSION_CODES.M,
        labelResource = R.string.grant_all_permissions,
        descResource = R.string.grant_all_permissions_desc,
    )

//    @Keep
//    data object ForceVolumeUuid : InstallOption(
//        value = PackageManager.INSTALL_FORCE_VOLUME_UUID,
//        minSdk = Build.VERSION_CODES.M,
//        labelResource = R.string.force_volume_uuid,
//        descResource = R.string.force_volume_uuid_desc,
//    )

//    @Keep
//    data object ForcePermissionPrompt : InstallOption(
//        value = PackageManager.INSTALL_FORCE_PERMISSION_PROMPT,
//        minSdk = Build.VERSION_CODES.N,
//        labelResource = R.string.force_permission_prompt,
//        descResource = R.string.force_permission_prompt_desc,
//    )

    @Keep
    data object InstantApp : InstallOption(
        value = 0x00000800,
        minSdk = Build.VERSION_CODES.N,
        labelResource = R.string.instant_app,
        descResource = R.string.instant_app_desc,
    )

    @Keep
    data object DontKillApp : InstallOption(
        value = 0x00001000,
        minSdk = Build.VERSION_CODES.N,
        labelResource = R.string.dont_kill_app,
        descResource = R.string.dont_kill_app_desc,
    )

    @Keep
    data object ForceSdk : InstallOption(
        value = 0x00002000,
        minSdk = Build.VERSION_CODES.N,
        maxSdk = Build.VERSION_CODES.P,
        labelResource = R.string.force_sdk,
        descResource = R.string.force_sdk_desc,
    )

    @Keep
    data object FullApp : InstallOption(
        value = 0x00004000,
        minSdk = Build.VERSION_CODES.O,
        labelResource = R.string.full_app,
        descResource = R.string.full_app_desc,
    )

    @Keep
    data object AllocateAggressive : InstallOption(
        value = 0x00008000,
        minSdk = Build.VERSION_CODES.O,
        labelResource = R.string.allocate_aggressive,
        descResource = R.string.allocate_aggressive_desc,
    )

    @Keep
    data object VirtualPreload : InstallOption(
        value = 0x00010000,
        minSdk = Build.VERSION_CODES.O_MR1,
        labelResource = R.string.virtual_preload,
        descResource = R.string.virtual_preload_desc,
    )

    @Keep
    data object Apex : InstallOption(
        value = 0x00020000,
        minSdk = Build.VERSION_CODES.Q,
        labelResource = R.string.apex,
        descResource = R.string.apex_desc,
    )

    @Keep
    data object EnableRollback : InstallOption(
        value = 0x00040000,
        minSdk = Build.VERSION_CODES.Q,
        labelResource = R.string.enable_rollback,
        descResource = R.string.enable_rollback_desc,
    )

    @Keep
    data object DisableVerification : InstallOption(
        value = 0x00080000,
        minSdk = Build.VERSION_CODES.Q,
        labelResource = R.string.disable_verification,
        descResource = R.string.disable_verification_desc,
    )

    @Keep
    data object Staged : InstallOption(
        value = 0x00200000,
        minSdk = Build.VERSION_CODES.Q,
        labelResource = R.string.staged,
        descResource = R.string.staged_desc,
    )

    @Keep
    data object DryRun : InstallOption(
        value = 0x00800000,
        minSdk = Build.VERSION_CODES.Q,
        maxSdk = Build.VERSION_CODES.R,
        labelResource = R.string.dry_run,
        descResource = R.string.dry_run_desc,
    )

    @Keep
    data object AllWhitelistRestrictedPermissions : InstallOption(
        value = 0x00400000,
        minSdk = Build.VERSION_CODES.S,
        labelResource = R.string.all_whitelist_restricted_permissions,
        descResource = R.string.all_whitelist_restricted_permissions_desc,
    )

    @Keep
    data object DisableAllowedApexUpdateCheck : InstallOption(
        // Bug in AOSP from 12-13 where the APEX flag here shared a value with AllWhitelistRestrictedPermissions.
        value = 0x00800000,
        minSdk = Build.VERSION_CODES.UPSIDE_DOWN_CAKE,
        labelResource = R.string.disable_allowed_apex_update_check,
        descResource = R.string.disable_allowed_apex_update_check_desc,
    )

    @Keep
    data object BypassLowTargetSdkBlock : InstallOption(
        value = 0x01000000,
        minSdk = Build.VERSION_CODES.UPSIDE_DOWN_CAKE,
        labelResource = R.string.bypass_low_target_sdk_block,
        descResource = R.string.bypass_low_target_sdk_block_desc,
    )
}
