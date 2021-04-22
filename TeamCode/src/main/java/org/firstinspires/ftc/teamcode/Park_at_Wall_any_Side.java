//Park_at_Wall_any_Side 1/29/2020

package org.firstinspires.ftc.teamcode;

//Import Lib
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
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

//Note: When active @Disabled will remove to code from the RC's Auto menu. 
@Disabled
@Autonomous

public class Park_at_Wall_any_Side extends LinearOpMode {
// Servo    
    Servo pushServo;        
// sensors
    ColorSensor colorSensorRT;
    ColorSensor colorSensorLT;
// motors
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor midShift;
// gyro objects
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
// drift vars
    private float currentDrift;
    private float counterDriftPower;
     
@Override
  public void runOpMode() {
        
  initNavx();
// get hardware objects
      colorSensorRT = hardwareMap.colorSensor.get("colorSensorRT");
      colorSensorLT = hardwareMap.colorSensor.get("colorSensorLT");
      backLeft = hardwareMap.get(DcMotor.class, "backLeft");
      backRight = hardwareMap.get(DcMotor.class, "backRight");
      midShift = hardwareMap.get(DcMotor.class, "midShift");
       pushServo = hardwareMap.get(Servo.class, "pushServo"); 
// set motor modes and directions
      backRight.setDirection(DcMotorSimple.Direction.REVERSE);
      midShift.setDirection(DcMotorSimple.Direction.REVERSE);
     
      pushServo.setPosition(1);

waitForStart();

  while (opModeIsActive()) {
       
// midShift Right to red line
      if(opModeIsActive() && colorSensorLT.red() < 30 && colorSensorLT.blue() < 35 && colorSensorRT.red() < 30 && colorSensorRT.blue() < 35){
        backLeft.setPower(.30); // - is used for reverse
        backRight.setPower(.30);
        
        while(opModeIsActive() && colorSensorLT.red() < 30 && colorSensorLT.blue() < 35 && colorSensorRT.red() < 30 && colorSensorRT.blue() < 35) {
          currentDrift = getYaw();
          counterDriftPower = (currentDrift / 90 *2);
          backLeft.setPower(counterDriftPower -.30);
          backRight.setPower(-counterDriftPower - .30);
          
         
        }
        }
        midShift.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        
        
        sleep(30000);
  }
    
}
  
// navX
private void initNavx() {
    navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
    gyro = (IntegratingGyroscope)navxMicro;
    telemetry.log().add("do not move robot navx is calibrating");
      while (navxMicro.isCalibrating() && opModeIsActive());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
}
private float getYaw() {
    Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    return angles.firstAngle;

}  
}
