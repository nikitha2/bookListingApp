bookListingApp lists a list of books it recieved from from the googleBooks API.

Topics covered: http networking, adaptors, searchView and other basic concepts.

Documentation:
https://medium.com/@gullapalli.nikitha/book-listing-app-35cf3b46a202

## Installation
1. Unzip the github project to a folder
2. Open Android Studio. Go to File -> New -> Import Project
3. choose bookListingApp to import and then click Next->Finish.
4. Run the project in android studio with emulator or on android phone to see the App running.
 
## Dependencies
make sure you have the below dependencies in build.gradle.app file
* implementation fileTree(dir: 'libs', include: ['*.jar'])
* implementation 'androidx.appcompat:appcompat:1.0.2'
* implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
* testImplementation 'junit:junit:4.12'
* androidTestImplementation 'androidx.test.ext:junit:1.1.0'
* androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

## Final App
https://youtu.be/ReSVwxLiS58

## License
bookListingApp is distributed under the MIT license.
