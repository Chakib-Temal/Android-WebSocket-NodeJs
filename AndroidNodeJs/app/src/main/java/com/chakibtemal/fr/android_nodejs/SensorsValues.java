package com.chakibtemal.fr.android_nodejs;

public class SensorsValues {
    private float valueX;
    private float valueY;
    private float valueZ;
    private long time;

    public SensorsValues(float valueX, float valueY, float valueZ, long time) {
        this.valueX = valueX;
        this.valueY = valueY;
        this.valueZ = valueZ;
        this.time = time;
    }

    public float getValueX() {
        return valueX;
    }

    public SensorsValues setValueX(long valueX) {
        this.valueX = valueX;
        return this;
    }

    public float getValueY() {
        return valueY;
    }

    public SensorsValues setValueY(long valueY) {
        this.valueY = valueY;
        return this;
    }

    public float getValueZ() {
        return valueZ;
    }

    public SensorsValues setValueZ(long valueZ) {
        this.valueZ = valueZ;
        return this;
    }

    public long getTime() {
        return time;
    }

    public SensorsValues setTime(long time) {
        this.time = time;
        return this;
    }
}
