package com.open.piGation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

@Service
public class GPIOService {
    private boolean isPi = false; //change to true when running on pi
    private Context pi4j;
    private DigitalOutput pumpRelay;

    @Autowired
    public GPIOService(){
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("linux") && !os.contains("windows")){
            isPi = true;
        }
        if(isPi){
            try{
            //Initialize Pi4j context
            this.pi4j = Pi4J.newAutoContext();

            //Configure GPIO pin for the pump relay BCM 17
            // Create and initialize the pump relay (GPIO 17)
            this.pumpRelay = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                    .id("pumpRelay")
                    .name("Water Pump Relay")
                    .address(16) // BCM GPIO 17 (Physical Pin 11)
                    .shutdown(DigitalState.LOW)
                    .initial(DigitalState.HIGH)
                    .provider("pigpio-digital-output")
                    .build());


        } catch (Exception e){
                System.err.println("Pi4J initialization failed: " + e.getMessage());
                isPi = false;
                pi4j = null;
                pumpRelay = null;
            }
        }
    }

    public void setPumpState(boolean state) {
        if(isPi && pumpRelay != null){
            //Pi4j code to set pump state
            if(state){
                pumpRelay.high(); //turn pump on
                System.out.println("Pump turned ON");
            } else {
                pumpRelay.low(); //turn pump off
                System.out.println("Pump turned OFF");
            }

        } else {
            //Mock code to set pump state
            System.out.println("Setting pump state to " + state);
        }
    }

    public void setPumpState(int pin, boolean state) {
        if(isPi && pi4j != null){
            DigitalOutput pumpRelay = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                    .id("pumpRelay-" + pin)
                    .name("Water Pump Relay " + pin)
                    .address(pin)
                    .shutdown(DigitalState.LOW)
                    .initial(DigitalState.LOW)
                    .provider("pigpio-digital-output")
                    .build());

            if(state){
                pumpRelay.high(); //turn pump on
                System.out.println("Pump on pin " + pin + " turned ON");
            } else {
                pumpRelay.low(); //turn pump off
                System.out.println("Pump on pin " + pin + " turned OFF");
            }
        } else {
            //Mock code to set pump state
            System.out.println("Setting pump state on pin " + pin + " to " + state);
        }
    }


    public boolean getPumpState() {
        if(isPi && pumpRelay != null){
            //Pi4j code to get pump state
            return pumpRelay.isHigh();
        } else {
            //Mock code to get pump state
            return false;
        }
    }
}
