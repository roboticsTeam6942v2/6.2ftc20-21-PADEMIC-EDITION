package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class gettingServoPos extends LinearOpMode {
    private Servo servoPain;

    @Override
    public void runOpMode() {
        servoPain = hardwareMap.get(Servo.class, "servoPain");

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Servo Previous Position, ", servoPain.getPosition());
            telemetry.update();
            double servoPos = 0;

            if (gamepad1.a) {
                servoPos += 0.1;
                servoPain.setPosition(servoPos);
            }

            if (gamepad1.b) {
                servoPos -= 0.1;
                servoPain.setPosition(servoPos);
            }

        }

    }
}
