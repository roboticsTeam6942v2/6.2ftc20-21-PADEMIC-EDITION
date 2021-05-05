package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class encoderAJ extends LinearOpMode {
    private DcMotor testMotor;
    @Override
    public void runOpMode() {
        testMotor = hardwareMap.get(DcMotor.class, "testTest");
            
    }
}
