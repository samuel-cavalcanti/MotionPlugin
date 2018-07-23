# MotionPlugin

## exemple code

```java
   @Override
    protected void onCreate(Bundle savedInstanceState) {

        plugin = new MotionPlugin();
        plugin.onCreate(this, savedInstanceState);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        plugin.startEvents();


         // gyroscope info:  
        if (plugin.rotationRate !=  null ){
            Double alpha = plugin.rotationRate.alpha;
            Double beta = plugin.rotationRate.alpha;
            Double gamma = plugin.rotationRate.gamma;

            Double NormalizedAlpha = rotationRateNormalized.alpha;
            Double NormalizedBeta = rotationRateNormalized.beta;
            Double NormalizedGamma = rotationRateNormalized.gamma;
         }


        if ( plugin.acceleration != null ){
            // accelerometer info:
            Double x =  plugin.acceleration.x;
            Double y =  plugin.acceleration.y;
            Double z =  plugin.acceleration.z;

            Double IncludingGravityX  = plugin.accelerationIncludingGravity.x;
            Double IncludingGravityY  = plugin.accelerationIncludingGravity.y;
            Double IncludingGravityZ  = plugin.accelerationIncludingGravity.z;


        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        plugin.stopEvents();

    }







```