# iWatt


## Synopsis 
This is a prototype data-driven android application designed to aid students at a university 


## Motivations
This is a 4th year dissertation project

##Dependencies

    
  
    compile 'com.j256.ormlite:ormlite-core:4.48'
    compile 'com.j256.ormlite:ormlite-android:4.48'
    compile 'com.tojc.ormlite.android:ormlite-content-provider-library:1.0.4'
    compile 'com.google.android.gms:play-services:8.4.0'
    compile 'com.bettervectordrawable:lib:0.8+'
    compile 'de.hdodenhof:circleimageview:1.3.0'
    compile 'com.android.support:design:23.1.1'


   You will also require the following archive files 
   - mail.jar
   - additional.jar
   - activation.jar
   - java-json.jar
   - json-lib-2.4-jdk15.jar
   - commons-collections4-4.1.jar
   - firebase-client-android-2.5.0.jar

   download mail.jar ,activation.jar and additional.jar from https://code.google.com/archive/p/javamail-android/downloads

   download java-json.jar from http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm

   download json-lib-2.4-jdk15.jar from http://www.java2s.com/Code/Jar/j/Downloadjsonlib24jdk15jar.htm

   download commons-collections4-4.1.jar
   from https://commons.apache.org/proper/commons-collections/download_collections.cgi
   download firebase-client-android-2.5.0.jar from https://www.firebase.com/docs/android/api/


## Usage

1. Create a new project in Android Studio.(Note: set the Company Domain to "uk.ac.hw.macs.nl148.iwatt") 

2. Go into the "app" directiory and delete all of its content

3. Open a terminal/command prompt in the empty "app" directory

4. Type "git clone https://github.com/neliousness/iWatt.git" in the command prompt/terminal and press return

5. In the "app" directory, create a new directory named "libs"

6. Copy and paste all of the jar files mentioned in the Dependecies section into the libs folder.

7. In Android Studio, change the file structure view from "Android" to Project. Navigate to the libs folder. Right click each jar file and click on "Add as library"

8. Revert to the Android file structure view. Double click on "build.gradle (Module: app)". Overrite all of the files contents with the folloing :


apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.2"

    defaultConfig {
        applicationId "uk.ac.hw.macs.nl148.iwatt"
        minSdkVersion 21
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
        multiDexEnabled true
        generatedDensities = []

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    packagingOptions {
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/LICENSE-FIREBASE.txt'
        exclude 'META-INF/NOTICE.txt'

    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    testCompile 'junit:junit:4.12'
    compile files('libs/firebase-client-android-2.5.0.jar')
    compile files('libs/java-json.jar')
    compile files('libs/json-lib-2.4-jdk15.jar')
    compile files('libs/commons-collections4-4.1.jar')
    // Required -- JUnit 4 framework
    testCompile 'junit:junit:4.12'
    // Optional -- Mockito framework
    testCompile 'org.mockito:mockito-core:1.10.19'
    compile files('libs/mail.jar')
    compile files('libs/additionnal.jar')
    compile files('libs/activation.jar')
    androidTestCompile 'com.android.support.test.espresso:espresso-core:2.0'
    androidTestCompile 'com.android.support.test:testing-support-lib:0.1'


    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:support-v4:23.1.1'
    compile 'com.j256.ormlite:ormlite-core:4.48'
    compile 'com.j256.ormlite:ormlite-android:4.48'
    compile 'com.google.android.gms:play-services:8.4.0'
    compile 'de.hdodenhof:circleimageview:1.3.0'
    compile 'com.android.support:design:23.1.1'
}


9. Rebuild the project

### Tools

Created with Android Studio
