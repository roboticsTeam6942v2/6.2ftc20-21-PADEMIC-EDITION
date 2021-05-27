package org.firstinspires.ftc.teamcode.ultimateGoalSeason2021;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class GetGyroPositionTest extends LinearOpMode {
    BNO055IMU imu;
    Orientation angles;

    @Override
    public void runOpMode()  {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            double heading=angles.firstAngle*-1;
            double roll=angles.secondAngle;
            double pitch=angles.thirdAngle;
            telemetry.addData("Heading", heading);
            telemetry.addData("Roll", roll);
            telemetry.addData("Pitch", pitch);
            telemetry.update();
        }

    }
}
