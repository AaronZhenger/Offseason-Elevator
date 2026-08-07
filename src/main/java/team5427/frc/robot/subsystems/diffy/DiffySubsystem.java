package team5427.frc.robot.subsystems.diffy;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.diffy.io.DiffyIO;
import team5427.frc.robot.subsystems.diffy.io.DiffyIOTalonFXBadFriction;
import team5427.frc.robot.subsystems.diffy.io.DiffyInputsAutoLogged;

public class DiffySubsystem extends SubsystemBase {

  private DiffyIO io;
  private DiffyInputsAutoLogged inputs;

  private static DiffySubsystem m_instance;

  @Getter @Setter private Rotation2d averageSetpoint;
  @Getter @Setter private Rotation2d differenceSetpoint;
  @Getter @Setter private LinearVelocity flywheelSetpoint;

  public static DiffySubsystem getInstance() {
    if (m_instance == null) {
      m_instance = new DiffySubsystem();
    }
    return m_instance;
  }

  private DiffySubsystem() {
    inputs = new DiffyInputsAutoLogged();
    switch (Constants.currentMode) {
      case REAL:
        io = new DiffyIOTalonFXBadFriction();
        break;
      case SIM:
        io = new DiffyIOTalonFXBadFriction();
        break;
      default:
        break;
    }
    averageSetpoint = DiffyConstants.kAverageStowPosition;
    differenceSetpoint = DiffyConstants.kDifferenceStandardPosition;
    flywheelSetpoint = DiffyConstants.kFlywheelStowVelocity;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    io.setLeaderPivotPosition(averageSetpoint.plus(differenceSetpoint));
    io.setFollowerPivotPosition(averageSetpoint.minus(differenceSetpoint));
    io.setFlywheelVelocity(flywheelSetpoint);

    logDiffy();
  }

  public boolean pivotAtGoal() {
    return Math.abs(
                inputs.pivotLeaderAngle.getDegrees()
                    - (averageSetpoint.plus(differenceSetpoint).getDegrees()))
            <= 4.0
        && Math.abs(
                inputs.pivotFollowerAngle.getDegrees()
                    - (averageSetpoint.minus(differenceSetpoint).getDegrees()))
            <= 4.0;
  }

  private void logDiffy() {
    Logger.recordOutput("Diffy/Leader/TargetPosition", averageSetpoint.plus(differenceSetpoint).getRadians());
    Logger.recordOutput("Diffy/Leader/Position", inputs.pivotLeaderAngle.getRadians());
    Logger.recordOutput(
        "Diffy/Leader/Velocity", inputs.pivotLeaderAngularVelocity.in(RadiansPerSecond));
    Logger.recordOutput(
        "Diffy/Leader/Acceleration",
        inputs.pivotLeaderAngularAcceleration.in(RadiansPerSecondPerSecond));
    Logger.recordOutput("Diffy/Leader/Current", inputs.pivotLeaderCurrent.in(Amps));
    Logger.recordOutput("Diffy/Leader/Voltage", inputs.pivotLeaderVoltage.in(Volts));

    Logger.recordOutput("Diffy/Follower/TargetPosition", averageSetpoint.minus(differenceSetpoint).getRadians());
    Logger.recordOutput("Diffy/Follower/Position", inputs.pivotFollowerAngle.getRadians());
    Logger.recordOutput(
        "Diffy/Follower/Velocity", inputs.pivotFollowerAngularVelocity.in(RadiansPerSecond));
    Logger.recordOutput(
        "Diffy/Follower/Acceleration",
        inputs.pivotFollowerAngularAcceleration.in(RadiansPerSecondPerSecond));
    Logger.recordOutput("Diffy/Follower/Current", inputs.pivotFollowerCurrent.in(Amps));
    Logger.recordOutput("Diffy/Follower/Voltage", inputs.pivotFollowerVoltage.in(Volts));
  }

  private void logFlywheel() {
    Logger.recordOutput(
        "Diffy/Flywheel/LinearVelocity", inputs.flywheelLinearVelocity.in(MetersPerSecond));
    Logger.recordOutput(
        "Diffy/Flywheel/LinearAcceleration",
        inputs.flywheelLinearAcceleration.in(MetersPerSecondPerSecond));
    Logger.recordOutput("Diffy/Flywheel/Current", inputs.flywheelCurrent.in(Amps));
    Logger.recordOutput("Diffy/Flywheel/Voltage", inputs.flywheelVoltage.in(Volts));
    Logger.recordOutput(
        "Diffy/Flywheel/Temperature", inputs.flywheelTemperature.in(Celsius));
  }
}
