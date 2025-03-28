package com.open.piGation.service;

import org.springframework.stereotype.Service;
import com.diozero.api.DigitalOutputDevice;

@Service
public class GPIOService {
    private boolean isPi = false;
    private DigitalOutputDevice pumpRelay;

    public GPIOService() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux") && !os.contains("windows")) {
            isPi = true;
        }

        if (isPi) {
            try {
                // Set the Diozero provider (you can also do this via -Ddiozero.provider)
                System.setProperty("diozero.provider", "linuxfs");

                // Use BCM pin 12 (physical pin 32)
                pumpRelay = new DigitalOutputDevice(17, true, false); // initialState=true, shutdownState=false
                System.out.println("Diozero initialized on GPIO12 (BCM)");

            } catch (Exception e) {
                System.err.println("Diozero init failed: " + e.getMessage());
                pumpRelay = null;
                isPi = false;
            }
        }
        setPumpState(true);
    }

    public void setPumpState(boolean state) {
        if (isPi && pumpRelay != null) {
            if (state) {
                pumpRelay.on();
                System.out.println("Pump turned ON");
            } else {
                pumpRelay.off();
                System.out.println("Pump turned OFF");
            }
        } else {
            System.out.println("Setting pump state to " + state + " (mock)");
        }
    }

    public void setPumpState(int pin, boolean state) {
        if (isPi) {
            try (DigitalOutputDevice dynamicRelay = new DigitalOutputDevice(pin, false, false)) {
                if (state) {
                    dynamicRelay.on();
                    System.out.println("Pump on pin " + pin + " turned ON");
                } else {
                    dynamicRelay.off();
                    System.out.println("Pump on pin " + pin + " turned OFF");
                }
            }
        } else {
            System.out.println("Setting pump state on pin " + pin + " to " + state + " (mock)");
        }
    }

    public boolean getPumpState() {
        if (isPi && pumpRelay != null) {
            return pumpRelay.isOn();
        } else {
            return false;
        }
    }
}
