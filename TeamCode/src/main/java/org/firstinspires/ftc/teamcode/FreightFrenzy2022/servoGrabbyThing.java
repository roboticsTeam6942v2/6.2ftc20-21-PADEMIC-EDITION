package org.firstinspires.ftc.teamcode.FreightFrenzy2022;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class servoGrabbyThing extends LinearOpMode {


    Servo roll;
    Servo pitch;
    Servo grab;
    int rollPos;
    int pitchPos;
    boolean grabbing;

    @Override
    public void runOpMode() {

        roll = hardwareMap.get(Servo.class, "roll");
        pitch = hardwareMap.get(Servo.class, "pitch");
        grab = hardwareMap.get(Servo.class, "grab");

        roll.setPosition(0.47);//.02, .93
        rollPos = 0;
        pitch.setPosition(0.50);//.07, 1
        pitchPos = 0;
        grab.setPosition(0.43);//0.66 is closed
        grabbing = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // looping code during the game
        while (!isStopRequested()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            if (gamepad1.x) {
                roll.setPosition(0.47);
                }
            if (gamepad1.y) {
                roll.setPosition(0.47);
            }

            }
        }
    }
