package team5427.frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Distance;
import team5427.lib.drivers.CANDeviceId;
import team5427.lib.drivers.ComplexGearRatio;
import team5427.lib.motors.MotorConfiguration;
import team5427.lib.motors.MotorConfiguration.IdleState;
import team5427.lib.motors.MotorConfiguration.MotorMode;
import team5427.lib.motors.MotorUtil;

public class ElevatorConstants {

  public static final CANDeviceId kLeaderMotorID = new CANDeviceId(1);
  public static final CANDeviceId kFollowerMotorID = new CANDeviceId(2);

  public static final MotorConfiguration kElevatorMotorConfiguration = new MotorConfiguration();

  public static final ComplexGearRatio kGearRatio =
      new ComplexGearRatio((14.0 / 72.0), (1.0 / (Inches.of(1.75).in(Meters) * Math.PI)));

  public static final Distance kElevatorSetpoint1 = Meters.of(0.1);
  public static final Distance kElevatorSetpoint2 = Meters.of(0.5);
  public static final Distance kElevatorStowPosition = Meters.of(0.0);

  static {
    kElevatorMotorConfiguration.gearRatio = kGearRatio;
    kElevatorMotorConfiguration.isArm = false;
    kElevatorMotorConfiguration.idleState = IdleState.kBrake;
    kElevatorMotorConfiguration.isInverted = false;
    kElevatorMotorConfiguration.mode = MotorMode.kLinear;
    kElevatorMotorConfiguration.withFOC = true;

    kElevatorMotorConfiguration.maxVelocity =
        kElevatorMotorConfiguration.getStandardMaxVelocity(MotorUtil.kKrakenX44_MaxRPM);
    kElevatorMotorConfiguration.maxAcceleration = kElevatorMotorConfiguration.maxVelocity / 2.0;

    kElevatorMotorConfiguration.kP = 10.0;
    kElevatorMotorConfiguration.kI = 0.0;
    kElevatorMotorConfiguration.kD = 0.0;

    kElevatorMotorConfiguration.kV = 0.0;
    kElevatorMotorConfiguration.kA = 0.0;
    kElevatorMotorConfiguration.kS = 0.0;
    kElevatorMotorConfiguration.kG = 1.0;
    kElevatorMotorConfiguration.kFF = 0.0;

    kElevatorMotorConfiguration.currentLimit = 80;
  }
}
