# Gradle Workshop

Repository for the Workshop on GDG Dublin 24/02/2015


https://speakerdeck.com/cdsap/in-gradle-we-trust

# Exercise 1
Basic configuration of gradle android project

```groovy
android {
    compileSdkVersion 21
    buildToolsVersion "21.1.2"

    defaultConfig {
        minSdkVersion 15
        targetSdkVersion 21
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile project(':lib')
}
```
### Exercise:
Define and use dependencies in your project and lib.

# Exercise 2
SignOptions and Build Types

##Sign application with stored credentials##
**Using the releaseStoredCredentials build type, you will be able to build and sign the application using the credentials stored directly in build.gradle**
### Exercise:
 Read the code and build the application using the android studio terminal
 > ./gradlew assembleReleaseStoredCredentials

##Sign application using command prompt##
**Using the releaseConsolePrompt build type, you will be able to build and sign the application using the credentials read from a standard console prompt**
### Exercise:
 Using taskGraph to execute the prompt only for the right build type, ask the user to input their credentials and use the values to sign the apk

```groovy
gradle.taskGraph.whenReady { taskGraph ->
         // Only execute when we are trying to assemble a release build with command prompt
         if (taskGraph.hasTask(':app:assembleReleaseConsolePrompt')) {
             def password = ''
             def alias = ''
             def aliasPassword = ''

             // gradle will ask us for credentials
             // System.console() can be null
             def console = System.console()
             if (console != null) {
                 // readPassword returns a char[] so we need to wrap it into a string, because that's
                 // most likely what you need
                 password = new String(console.readPassword("\n\$ Enter keystore password: "))
                 alias = new String(console.readLine("\n\$ Enter key alias: "))
                 aliasPassword = new String(console.readPassword("\n\$ Enter key password: "))
             }

             // set the captured credentials to the signingConfig
             signingConfigs.releaseConsolePrompt.storePassword = password
             signingConfigs.releaseConsolePrompt.keyAlias = alias
             signingConfigs.releaseConsolePrompt.keyPassword = aliasPassword
         }
}
```
##Sign application using credentials stored in an external file##
**Using the releaseExternalCredentials build type, you will be able to build and sign the application using the credentials read from an external file**
### Exercise:
 Using taskGraph to read the file only for the right build type, extract the credentials from an external file and use them to sign the apk
 
 ```groovy
 gradle.taskGraph.whenReady { taskGraph ->
        // Only execute when we are trying to assemble a release build with external credentials file
        if (taskGraph.hasTask(':app:releaseExternalCredentials')) {
            // extract credentials from a file outside the project
            // file structure:
            //    keystore=/path/to/keystore
            //    keystore.password=demo1234
            //    keystore.alias=demo
            //    keystore.aliasPassword=demo1234
            def credentialsFile = '/path/to/file'
            if (new File(credentialsFile).exists()) {

                Properties props = new Properties()
                props.load(new FileInputStream(file(credentialsFile)))

                signingConfigs.releaseExternalCredentialsFile.storeFile = file(props['keystore'])
                signingConfigs.releaseExternalCredentialsFile.storePassword = props['password']
                signingConfigs.releaseExternalCredentialsFile.keyAlias = file(props['alias'])
                signingConfigs.releaseExternalCredentialsFile.keyPassword = file(props['aliasPassword'])

            }
        }
 }
 ```

# Exercise 3
Flavors

```groovy

    productFlavors {

        flavor1 {
            applicationId "com.gdgdublin.exercise2flavor1"
            manifestPlaceholders = [label: "flavor1"]
        }
        flavor2 {
            applicationId "com.gdgdublin.exercise2flavor2"
            manifestPlaceholders = [label: "flavor2"]
        }
        flavor3 {
            applicationId "com.gdgdublin.exercise2flavor3"
            manifestPlaceholders = [label: "flavor3"]
        }
    }
```

### Exercise:
 Define BuildConfig values in your flavors.

# Exercise 4
Build a custom task to rename apk name.

```groovy

    android.applicationVariants.all { variant ->
        variant.outputs.each { output ->
            def outputFile = output.outputFile
            if (outputFile != null && outputFile.name.endsWith('.apk')) {
                def fileName = outputFile.name.replace(".apk", "-" + getDate() + ".apk")
                output.outputFile = new File(outputFile.parent, fileName)
            }
        }
    }

```groovy

# Exercise 5
Build a custom plugin to rename apk name.

```groovy

    renamingOptions {
        outputOptions {
            nameFormat getDate() + '-$appName-$buildType-$versionName'
        }
    }
```groovy

### Exercise:
Deploy and import the plugin in your module




Developed By
------------
* Vlado Atanasov - <vlado.atanasov@gmail.com>
* Iñaki Villar - <inaki.seri@gmail.com>

<!--<a href="https://plus.google.com/100633195306202271076/posts">
  <img alt="GDG Dublin" src="https://lh5.googleusercontent.com/-Ac8OIZTqtk8/VKqWmpTJVpI/AAAAAAAAAwY/dXw4rK-kVIQ/s1248-no/GDG_Dublin_Logo_Doors_square_small.png" />
</a>
<a href="https://plus.google.com/101769406777537927870/posts">
  <img alt="GDG Mallorca" src="https://lh6.googleusercontent.com/-8gNYtqcZjPE/URIMcJMwD_I/AAAAAAAAAFc/A2oa3q-gcB0/s500-no/gdg2.png" />
</a>-->
