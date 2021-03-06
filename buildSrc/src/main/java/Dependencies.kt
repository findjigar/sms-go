object Versions {
    const val easyPermissions = "2.0.0"
    const val androidMaterial = "1.0.0"
    const val appCompat = "1.0.2"
    const val coreKtx = "1.1.0-alpha03"
    const val constraintlayout = "1.1.3"
    const val kotlin = "1.3.11"
    const val okhttp = "3.12.1"
    const val firebaseCore = "16.0.6"
}

object Libs {
    val easyPermission = "pub.devrel:easypermissions:${Versions.easyPermissions}"
    val googleMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"

}

object TestLibs {
    val junit = "junit:junit:4.12"
    val espresso = "androidx.test.espresso:espresso-core:3.1.1"
}