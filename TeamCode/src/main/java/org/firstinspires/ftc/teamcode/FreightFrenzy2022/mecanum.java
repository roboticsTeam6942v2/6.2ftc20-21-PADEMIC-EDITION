package org.firstinspires.ftc.teamcode.FreightFrenzy2022;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class mecanum extends LinearOpMode {
    double wheelSpeedAdapter = 0;

    //Declaring the motors
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {

        // Name strings must match up with the config on the Robot Controller app.
        // initialize four DcMotors for the drive train, and add the speed variable
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // looping code during the game
        while(!isStopRequested()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            // declare the values of the joysticks
            double drive  = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x * 1.5; //Makes angles more accurate because the wheels have to turn more to go sideways, so it allows the bot to adapt better when it comes to going at obscure angles on the plane
            double twist  = -gamepad1.left_stick_y;

            // calculates speed on each of the 4 motors
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)};

            // loop through all values in the speeds[] array and find the greatest *magnitude*.  Not the greatest velocity
            // essentially normalizing them
            double max = Math.abs(speeds[0]);
            for (double speed : speeds) {
                if (max < Math.abs(speed)) max = Math.abs(speed);
            }

            // If and only if the maximum is outside of the range we want it to be,
            // normalize all the other speeds based on the given speed value.
            if (max > 1) {
                for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
            }

            // apply the calculated values to the motors, and modify it for direction
            double originalWheelSpeedAdadpter= wheelSpeedAdapter;
            String speed="ERROR";
            if (originalWheelSpeedAdadpter==.5) {
                speed="Half Speed";
            } else if (originalWheelSpeedAdadpter==1) {
                speed="Half Speed";
            }
            telemetry.addData("Speed: ", speed );
            telemetry.update();

            if (speeds[0] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            frontLeft.setPower(speeds[0]- wheelSpeedAdapter);

            if (speeds[1] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            frontRight.setPower(speeds[1]- wheelSpeedAdapter);

            if (speeds[2] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            backLeft.setPower(speeds[2]- wheelSpeedAdapter);

            if (speeds[3] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            backRight.setPower(speeds[3]- wheelSpeedAdapter);
            
        }
    }
}
