package org.firstinspires.ftc.teamcode.ultimateGoalSeason2021;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

/*This file will probably  be deleted, it is used to find out our maximum launcher power
Use a to increase the power a lot, and b to decrese the ower a lot
To fine tune the speed used the up and down on the d pad to change speeds in smaller increments, accordingly
Please then note the power as displayed in telemetry data for our maximum launcher output
Press start to toggle power to the launcher
*NOTE* You MUST name the launcher "diskLauncher" in the expansion hub setup in the Robot controller for this code to work
 */



public class Launcher_fine_tune_limiter_test extends LinearOpMode {
    DcMotor diskLauncher;
    double launcherPower=1;
    boolean launcherRunning=false;

    @Override
    public void runOpMode() {
        diskLauncher = hardwareMap.get(DcMotor.class, "diskLauncher");
        diskLauncher.setDirection(DcMotorSimple.Direction.FORWARD);//swap with REVERSE if the motor goes the wrong way

        waitForStart();

        while(!isStopRequested()) {
            telemetry.addData("Power: ", launcherPower);
            telemetry.addData("Time last changed power: ", gamepad1.timestamp);
            telemetry.update();

            if (gamepad1.start && launcherRunning==true) {
                diskLauncher.setPower(launcherPower);
                launcherRunning=true;
            }
            if (gamepad1.start && launcherRunning==true) {
                diskLauncher.setPower(0);
                launcherRunning=false;
            }
            if (gamepad1.dpad_down) {
                launcherPower=launcherPower-.02;
            }
            if (gamepad1.dpad_up) {
                launcherPower=launcherPower+.02;
            }
            if (gamepad1.a) {
                launcherPower=launcherPower+.1;
            }
            if (gamepad1.b) {
                launcherPower=launcherPower-.1;
            }


        }
    }
}
