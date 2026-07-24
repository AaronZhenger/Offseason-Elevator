package team5427.frc.robot.subsystems.diffy;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import team5427.lib.drivers.CANDeviceId;
import team5427.lib.drivers.ComplexGearRatio;
import team5427.lib.motors.MotorConfiguration;
import team5427.lib.motors.MotorConfiguration.IdleState;
import team5427.lib.motors.MotorConfiguration.MotorMode;
import team5427.lib.motors.MotorUtil;

public class DiffyConstants {
  public static final CANDeviceId kPivotLeaderMotorID = new CANDeviceId(18);
  public static final CANDeviceId kPivotFollowerMotorID = new CANDeviceId(19);
  public static final CANDeviceId kFlywheelMotorID = new CANDeviceId(20);

  public static final MotorConfiguration kPivotMotorConfiguration = new MotorConfiguration();
  public static final MotorConfiguration kPivotFollowerMotorConfiguration =
      new MotorConfiguration();
  public static final MotorConfiguration kFlywheelMotorConfiguration = new MotorConfiguration();

  public static final ComplexGearRatio kPivotGearRatio = new ComplexGearRatio();
  public static final ComplexGearRatio kFlywheelGearRatio = new ComplexGearRatio();

  public static final Rotation2d kPivotStowPosition = Rotation2d.kZero;
  public static final Rotation2d kPivotFlippedPosition = Rotation2d.k180deg;

  public static final LinearVelocity kFlywheelStowVelocity = MetersPerSecond.of(-0.5);
  public static final LinearVelocity kFlywheelIntakeVelocity = MetersPerSecond.of(-1.5);
  public static final LinearVelocity kFlywheelOuttakeVelocity = MetersPerSecond.of(1.5);

  static {
    kPivotMotorConfiguration.gearRatio = kPivotGearRatio;
    kPivotMotorConfiguration.isArm = false;
    kPivotMotorConfiguration.idleState = IdleState.kBrake;
    kPivotMotorConfiguration.isInverted = false;
    kPivotMotorConfiguration.mode = MotorMode.kServo;
    kPivotMotorConfiguration.withFOC = true;

    kPivotMotorConfiguration.maxVelocity =
        kPivotMotorConfiguration.getStandardMaxVelocity(MotorUtil.kKrakenX44FOC_MaxRPM);
    kPivotMotorConfiguration.maxAcceleration = kPivotMotorConfiguration.maxVelocity / 3.0;

    kPivotMotorConfiguration.kP = 0.0;
    kPivotMotorConfiguration.kI = 0.0;
    kPivotMotorConfiguration.kD = 0.0;

    kPivotMotorConfiguration.kV = 0.0;
    kPivotMotorConfiguration.kA = 0.0;
    kPivotMotorConfiguration.kS = 0.0;
    kPivotMotorConfiguration.kG = 0.0;
    kPivotMotorConfiguration.kFF = 0.0;

    kPivotMotorConfiguration.currentLimit = 60;
  }

  static {
    kPivotFollowerMotorConfiguration.gearRatio = kPivotGearRatio;
    kPivotFollowerMotorConfiguration.isArm = false;
    kPivotFollowerMotorConfiguration.idleState = IdleState.kBrake;
    kPivotFollowerMotorConfiguration.isInverted = true;
    kPivotFollowerMotorConfiguration.mode = MotorMode.kServo;
    kPivotFollowerMotorConfiguration.withFOC = true;

    kPivotFollowerMotorConfiguration.maxVelocity =
        kPivotFollowerMotorConfiguration.getStandardMaxVelocity(MotorUtil.kKrakenX44FOC_MaxRPM);
    kPivotFollowerMotorConfiguration.maxAcceleration =
        kPivotFollowerMotorConfiguration.maxVelocity / 3.0;

    kPivotFollowerMotorConfiguration.kP = 0.0;
    kPivotFollowerMotorConfiguration.kI = 0.0;
    kPivotFollowerMotorConfiguration.kD = 0.0;

    kPivotFollowerMotorConfiguration.kV = 0.0;
    kPivotFollowerMotorConfiguration.kA = 0.0;
    kPivotFollowerMotorConfiguration.kS = 0.0;
    kPivotFollowerMotorConfiguration.kG = 0.0;
    kPivotFollowerMotorConfiguration.kFF = 0.0;

    kPivotFollowerMotorConfiguration.currentLimit = 60;
  }

  static {
    kFlywheelMotorConfiguration.gearRatio = kFlywheelGearRatio;
    kFlywheelMotorConfiguration.isArm = false;
    kFlywheelMotorConfiguration.idleState = IdleState.kCoast;
    kFlywheelMotorConfiguration.isInverted = false;
    kFlywheelMotorConfiguration.mode = MotorMode.kFlywheel;
    kFlywheelMotorConfiguration.withFOC = false;

    kFlywheelMotorConfiguration.maxVelocity =
        kFlywheelMotorConfiguration.getStandardMaxVelocity(MotorUtil.kKrakenX60_MaxRPM);
    kFlywheelMotorConfiguration.maxAcceleration = kFlywheelMotorConfiguration.maxVelocity / 2.0;

    kFlywheelMotorConfiguration.kP = 10.0;
    kFlywheelMotorConfiguration.kI = 0.0;
    kFlywheelMotorConfiguration.kD = 0.0;

    kFlywheelMotorConfiguration.kV = 0.0;
    kFlywheelMotorConfiguration.kA = 0.0;
    kFlywheelMotorConfiguration.kS = 0.0;
    kFlywheelMotorConfiguration.kG = 1.0;
    kFlywheelMotorConfiguration.kFF = 0.0;

    kFlywheelMotorConfiguration.currentLimit = 40;
  }
}
