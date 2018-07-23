package MotionPlugin;

 public class DeviceAcceleration {
    public Double x;
    public Double y;
    public Double z;

    public DeviceAcceleration (double _x , double _y, double _z){
        x = _x;
        y = _y;
        z = _z;
    }

    public DeviceAcceleration(DeviceAcceleration d){
        x = d.x;
        y = d.y;
        z = d.z;
    }



}
