package team5427.frc.robot.subsystems.endeffector;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.endeffector.io.EndEffectorIO;
import team5427.frc.robot.subsystems.endeffector.io.EndEffectorIOTalonFX;
import team5427.frc.robot.subsystems.endeffector.io.EndEffectorInputsAutoLogged;

public class EndEffectorSubsystem extends SubsystemBase {

  private EndEffectorIO io;
  private EndEffectorInputsAutoLogged inputs;

  private static EndEffectorSubsystem m_instance;

  @Setter private Rotation2d pivotSetpoint;
  @Setter private LinearVelocity flywheelSetpoint;

  public static EndEffectorSubsystem getInstance() {
    return (m_instance == null) ? new EndEffectorSubsystem() : m_instance;
  }

  private EndEffectorSubsystem() {
    inputs = new EndEffectorInputsAutoLogged();
    switch (Constants.currentMode) {
      case REAL:
        io = new EndEffectorIOTalonFX();
        break;
      case SIM:
        break;
      default:
        break;
    }
    pivotSetpoint = EndEffectorConstants.kPivotStowPosition;
    flywheelSetpoint = EndEffectorConstants.kFlywheelStowVelocity;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    io.setPivotPosition(pivotSetpoint);
    io.setFlywheelVelocity(flywheelSetpoint);

    Logger.processInputs("EndEffector/Inputs", inputs);
    log();
  }

  private void log() {
    Logger.recordOutput("EndEffector/Pivot/PivotPosition", inputs.pivotLeaderAngle.getRadians());
    Logger.recordOutput(
        "EndEffector/Pivot/PivotVelocity", inputs.pivotLeaderAngularVelocity.in(RadiansPerSecond));
    Logger.recordOutput(
        "EndEffector/Pivot/PivotAcceleration",
        inputs.pivotLeaderAngularAcceleration.in(RadiansPerSecondPerSecond));
    Logger.recordOutput("EndEffector/Pivot/PivotCurrent", inputs.pivotLeaderCurrent.in(Amps));
    Logger.recordOutput("EndEffector/Pivot/PivotVoltage", inputs.pivotLeaderVoltage.in(Volts));

    Logger.recordOutput(
        "EndEffector/Flywheel/FlywheelLinearVelocity",
        inputs.flywheelLinearVelocity.in(MetersPerSecond));
    Logger.recordOutput(
        "EndEffector/Flywheel/FlywheelLinearAcceleration",
        inputs.flywheelLinearAcceleration.in(MetersPerSecondPerSecond));
    Logger.recordOutput("EndEffector/Flywheel/FlywheelCurrent", inputs.flywheelCurrent.in(Amps));
    Logger.recordOutput("EndEffector/Flywheel/FlywheelVoltage", inputs.flywheelVoltage.in(Volts));
    Logger.recordOutput(
        "EndEffector/Flywheel/FlywheelTemperature", inputs.flywheelTemperature.in(Celsius));
  }
}
