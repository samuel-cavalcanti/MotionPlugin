package MotionPlugin;

public class DeviceRotationRate {

    public Double alpha;
    public Double beta;
    public Double gamma;

    public DeviceRotationRate ( double _alpha, double _beta , double _gamma ){
        alpha = _alpha;
        beta = _beta;
        gamma = _gamma;

    }

    public DeviceRotationRate ( DeviceRotationRate d){
        alpha = d.alpha;
        beta = d.beta;
        gamma = d.gamma;
    }




}
