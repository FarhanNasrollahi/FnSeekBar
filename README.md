<h1>FnSeekBar</h1>

Add this in your app's build.gradle file(Using Android Studio and Gradle):

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  

    dependencies {
	         implementation 'com.github.FarhanNasrollahi:FnSeekBar:0.1.0'
	}

For use Code :

```
FnSeekBar fnSeekBar = findViewById(R.id.fnseekbarview);
        fnSeekBar.setSeekbarChangeListener(new FnSeekBar.OnSeekbarChangeListener() {
            @Override
            public void values(int values) {

            }

            @Override
            public void stopTouch() {

            }

            @Override
            public void startTouch() {

            }
        });

```
