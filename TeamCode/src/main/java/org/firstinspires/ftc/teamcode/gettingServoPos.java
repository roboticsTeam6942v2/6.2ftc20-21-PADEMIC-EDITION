package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class gettingServoPos extends LinearOpMode {
    private Servo servoPain;
    double servoPos = 0.0;

    @Override
    public void runOpMode() {
        servoPain = hardwareMap.get(Servo.class, "servoPain");

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Servo Previous Position, ", servoPain.getPosition());
            telemetry.update();

            if (gamepad1.a) {
               //double plus = servoPos + 0.1;
                //servoPos += 0.1;
                servoPain.setPosition(0.3);
            }

            if (gamepad1.b) {
                //double minus = servoPos - 0.1;
                //servoPos -= 0.1;
                servoPain.setPosition(0.7);
            }

            //servoPain.setPosition(servoPos);

        }

    }
}
