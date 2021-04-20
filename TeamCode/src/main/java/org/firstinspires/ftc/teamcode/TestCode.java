package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//Import Lib
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//navX
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

@Disabled
@Autonomous

public class TestCode extends LinearOpMode {
// sensors
    ColorSensor colorSensorRT;
    ColorSensor colorSensorLT;
// motors
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor midShift;
//servo
Servo pushServo;
// gyro objects
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
// drift vars
    private float currentDrift;
    private float counterDriftPower;

  
    @Override
    public void runOpMode() {
        colorSensorRT = hardwareMap.colorSensor.get("colorSensorRT");
      colorSensorLT = hardwareMap.colorSensor.get("colorSensorLT");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");
        pushServo = hardwareMap.get(Servo.class, "pushServo");
    
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        midShift.setDirection(DcMotorSimple.Direction.REVERSE);
        
        pushServo.setPosition(1);
       
        waitForStart();
    {
        
telemetry.update(); 
       while (opModeIsActive()) {
           telemetry.addData("color rt red: ", colorSensorRT.red());
           telemetry.addData("color lt red: ", colorSensorLT.red());
           telemetry.addData("color rt blue: ", colorSensorRT.blue());
           telemetry.addData("color lt blue: ", colorSensorLT.blue());
         telemetry.update(); 
           }

}}}